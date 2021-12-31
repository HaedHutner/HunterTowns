package dev.haedhutner.towns;

import dev.haedhutner.chat.event.ChatChannelRegistrationEvent;
import dev.haedhutner.core.command.CommandService;
import dev.haedhutner.core.economy.Economy;
import dev.haedhutner.core.event.HunterDatabaseMigrationEvent;
import dev.haedhutner.core.event.HunterHibernateConfigurationEvent;
import dev.haedhutner.core.event.HunterHibernateInitializedEvent;
import dev.haedhutner.towns.api.permission.*;
import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.chat.NationChannel;
import dev.haedhutner.towns.chat.TownChannel;
import dev.haedhutner.towns.command.nation.NationCommand;
import dev.haedhutner.towns.command.plot.PlotCommand;
import dev.haedhutner.towns.command.rent.RentCommand;
import dev.haedhutner.towns.command.resident.ResidentCommand;
import dev.haedhutner.towns.command.town.TownCommand;
import dev.haedhutner.towns.facade.*;
import dev.haedhutner.towns.listener.PlayerListener;
import dev.haedhutner.towns.listener.ProtectionListener;
import dev.haedhutner.towns.listener.RaidListener;
import dev.haedhutner.towns.model.entity.*;
import dev.haedhutner.towns.permission.TownsContextCalculator;
import dev.haedhutner.towns.persistence.cache.TownsCache;
import com.google.inject.Inject;
import com.google.inject.Injector;
import dev.haedhutner.towns.service.*;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import static dev.haedhutner.towns.HunterTowns.*;

@Plugin(
        id = ID,
        name = NAME,
        description = DESCRIPTION,
        version = VERSION,
        dependencies = {
                @Dependency(id = "huntercore")
        }
)
public class HunterTowns {

    final static String ID = "huntertowns";
    final static String NAME = "Hunter Towns";
    final static String DESCRIPTION = "A land-management plugin";
    final static String VERSION = "%PROJECT_VERSION%";

    private static HunterTowns instance;
    private static boolean init = false;
    private boolean economyEnabled;
    @Inject
    private Logger logger;

    @Inject
    private Injector spongeInjector;

    @Inject
    private CommandService commandService;

    @Inject
    private TownsConfig config;

    @Inject
    private TownsCache cache;

    @Inject
    private RoleService roleService;

    @Inject
    private TownRaidService raidService;

    @Inject
    private PlotBorderFacade plotBorderFacade;

    @Inject
    private TaxFacade taxFacade;

    @Inject
    private RentService rentService;

    @Inject
    private PlayerListener playerListener;

    @Inject
    private ProtectionListener protectionListener;

    @Inject
    private RaidListener raidListener;

    public static HunterTowns getInstance() {
        return instance;
    }

    public static boolean economyIsEnabled() {
        return getInstance().economyEnabled;
    }

    private void init() {
        instance = this;

        // Register Permission Catalogue registry module
        Sponge.getRegistry().registerModule(Permission.class, new PermissionRegistryModule());
        Sponge.getRegistry().registerModule(WorldPermission.class, new WorldPermissionRegistryModule());
        Sponge.getRegistry().registerModule(TownsPermissionContext.class, new TownsPermissionContextRegistryModule());

        config.init();

        init = true;
    }

    private void start() {
        economyEnabled = Economy.isPresent() && config.ECONOMY;

        if (config.TOWN.TOWN_WARMUP < 0) {
            logger.warn("Town spawn warmup is negative. Will default to zero.");
            config.TOWN.TOWN_WARMUP = 0;
        }

        if (config.TOWN.TOWN_COOLDOWN < 0) {
            config.TOWN.TOWN_COOLDOWN = 0;
            logger.warn("Town spawn cooldown is negative. Will default to zero.");
        }

        roleService.init();
        cache.initCache();
        raidService.initRaidTimer();
        plotBorderFacade.initBorderTask();

        if (economyIsEnabled()) {
            taxFacade.init();
            rentService.init();
        }

        Sponge.getEventManager().registerListeners(this, playerListener);
        Sponge.getEventManager().registerListeners(this, protectionListener);
        Sponge.getEventManager().registerListeners(this, raidListener);

        Sponge.getServiceManager()
                .provideUnchecked(org.spongepowered.api.service.permission.PermissionService.class)
                .registerContextCalculator(new TownsContextCalculator());

        try {
            commandService.register(new ResidentCommand(), this);
            commandService.register(new PlotCommand(), this);
            commandService.register(new TownCommand(), this);
            commandService.register(new NationCommand(), this);

            if (economyIsEnabled()) {
                commandService.register(new RentCommand(), this);
            }
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        cache.flushCache();
    }

    @Listener
    public void onHibernateInit(HunterHibernateInitializedEvent event) {
        init();
    }

    @Listener
    public void onHibernateConfiguration(HunterHibernateConfigurationEvent event) {
        event.registerEntity(Nation.class);
        event.registerEntity(Town.class);
        event.registerEntity(NationPlot.class);
        event.registerEntity(RentInfo.class);
        event.registerEntity(TownPlot.class);
        event.registerEntity(Resident.class);
        event.registerEntity(TownPlotPermission.class);
    }

    @Listener
    public void onDatabaseMigration(HunterDatabaseMigrationEvent event) {
        event.registerForMigration(ID);
    }

    @Listener
    public void onChatChannelRegistration(ChatChannelRegistrationEvent event) {
        event.registerChatChannel(new TownChannel());
        event.registerChatChannel(new NationChannel());
    }

    @Listener(order = Order.LAST)
    public void onStart(GameStartingServerEvent event) {
        if (init) start();
    }

    @Listener
    public void onStop(GameStoppingServerEvent event) {
        if (init) stop();
    }

    @Listener
    public void onReload(GameReloadEvent event) {
        config.reload();
    }

    public Logger getLogger() {
        return logger;
    }

    public Injector getInjector() {
        return spongeInjector;
    }

    public static <T> T getInstance(Class<T> clazz) {
        return getInstance().getInjector().getInstance(clazz);
    }

}
