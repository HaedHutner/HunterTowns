package dev.haedhutner.towns;

import dev.haedhutner.chat.ChatModule;
import dev.haedhutner.core.HunterCore;
import dev.haedhutner.core.command.CommandService;
import dev.haedhutner.core.economy.Economy;
import dev.haedhutner.core.event.HunterDatabaseMigrationEvent;
import dev.haedhutner.core.event.HunterHibernateConfigurationEvent;
import dev.haedhutner.core.event.HunterHibernateInitializedEvent;
import dev.haedhutner.towns.api.permission.*;
import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.command.nation.NationCommand;
import dev.haedhutner.towns.command.plot.PlotCommand;
import dev.haedhutner.towns.command.rent.RentCommand;
import dev.haedhutner.towns.command.resident.ResidentCommand;
import dev.haedhutner.towns.command.town.TownCommand;
import dev.haedhutner.towns.facade.*;
import dev.haedhutner.towns.integration.AtherysChatIntegration;
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

        if (HunterCore.getInstance(ChatModule.class).isEnabled()) {
            AtherysChatIntegration.registerChannels();
        }

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

//    public TownRepository getTownRepository() {
//        return components.townRepository;
//    }
//
//    public NationRepository getNationRepository() {
//        return components.nationRepository;
//    }
//
//    public TownPlotRepository getTownPlotRepository() {
//        return components.townPlotRepository;
//    }
//
//    public NationPlotRepository getNationPlotRepository() {
//        return components.nationPlotRepository;
//    }
//
//    public ResidentRepository getResidentRepository() {
//        return components.residentRepository;
//    }
//
//    public TaxService getTaxService() {
//        return components.taxService;
//    }
//
//    public PollService getPollService() {
//        return components.pollService;
//    }
//
//    public NationService getNationService() {
//        return components.nationService;
//    }
//
//    public TownService getTownService() {
//        return components.townService;
//    }
//
//    public PlotService getPlotService() {
//        return components.plotService;
//    }
//
//    public ResidentService getResidentService() {
//        return components.residentService;
//    }
//
//    public RoleService getRoleService() {
//        return components.roleService;
//    }
//
//    public RentService getRentService() {
//        return components.rentService;
//    }
//
//    public TownsPermissionService getPermissionService() {
//        return components.townsPermissionService;
//    }
//
//    public TownsMessagingFacade getTownsMessagingService() {
//        return components.townsMessagingFacade;
//    }
//
//    public TownRaidService getTownRaidService() {
//        return components.townRaidService;
//    }
//
//    public NationFacade getNationFacade() {
//        return components.nationFacade;
//    }
//
//    public TownFacade getTownFacade() {
//        return components.townFacade;
//    }
//
//    public TownSpawnFacade getTownSpawnCommand() {
//        return components.townSpawnFacade;
//    }
//
//    public TownAdminFacade getTownAdminFacade() {
//        return components.townAdminFacade;
//    }
//
//    public PlotFacade getPlotFacade() {
//        return components.plotFacade;
//    }
//
//    public ResidentFacade getResidentFacade() {
//        return components.residentFacade;
//    }
//
//    public PermissionFacade getPermissionFacade() {
//        return components.permissionFacade;
//    }
//
//    public PlotSelectionFacade getPlotSelectionFacade() {
//        return components.plotSelectionFacade;
//    }
//
//    public PollFacade getPollFacade() {
//        return components.pollFacade;
//    }
//
//    public PlotBorderFacade getPlotBorderFacade() {
//        return components.plotBorderFacade;
//    }
//
//    public TownRaidFacade getTownRaidFacade() {
//        return components.townRaidFacade;
//    }
//
//    public TaxFacade getTaxFacade() {
//        return components.taxFacade;
//    }
//
//    public RentFacade getRentFacade() {
//        return components.rentFacade;
//    }
//
//    public TownsCache getTownsCache() {
//        return components.townsCache;
//    }

}
