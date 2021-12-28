package dev.haedhutner.towns.facade;

import dev.haedhutner.towns.TownsConfig;
import dev.haedhutner.towns.api.command.TownsCommandException;
import dev.haedhutner.towns.api.permission.Permission;
import dev.haedhutner.towns.api.permission.nation.NationPermission;
import dev.haedhutner.towns.api.permission.town.TownPermission;
import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.service.PlotService;
import dev.haedhutner.towns.service.ResidentService;
import dev.haedhutner.towns.service.TownsPermissionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class PermissionFacade {

    static final String NOT_PERMITTED = "You are not permitted to ";
    public final Map<String, TownPermission> TOWN_PERMISSIONS = getTownPermissions();
    public final Map<String, NationPermission> NATION_PERMISSIONS = getNationPermissions();
    public final Map<String, WorldPermission> WORLD_PERMISSIONS = getWorldPermissions();

    @Inject
    TownsConfig config;

    @Inject
    ResidentService residentService;

    @Inject
    TownsPermissionService townsPermissionService;

    @Inject
    PlotService plotService;

    PermissionFacade() {
    }

    public boolean isPermitted(Player source, Permission permission) throws TownsCommandException {
        if (permission == null) {
            throw new TownsCommandException("Failed to establish command permission. Will not proceed.");
        }

        return townsPermissionService.isPermitted(source, permission);
    }

    public void checkPermitted(Player source, Permission permission, String message) throws TownsCommandException {
        if (!isPermitted(source, permission)) {
            throw new TownsCommandException(NOT_PERMITTED + message);
        }
    }

    private Map<String, TownPermission> getTownPermissions() {
        Collection<TownPermission> perms = Sponge.getGame().getRegistry().getAllOf(Permission.class).stream()
                .filter(permission -> permission instanceof TownPermission)
                .map(permission -> (TownPermission) permission)
                .collect(Collectors.toList());

        Map<String, TownPermission> townPerms = new HashMap<>();
        perms.forEach(permission -> townPerms.put(permission.getId(), permission));

        return townPerms;
    }

    private Map<String, WorldPermission> getWorldPermissions() {
        Collection<WorldPermission> perms = new ArrayList<>(Sponge.getGame().getRegistry().getAllOf(WorldPermission.class));

        Map<String, WorldPermission> worldPerms = new HashMap<>();
        perms.forEach(permission -> worldPerms.put(permission.getCommandElementName(), permission));

        return worldPerms;
    }

    private Map<String, NationPermission> getNationPermissions() {
        Collection<NationPermission> perms = Sponge.getGame().getRegistry().getAllOf(Permission.class).stream()
                .filter(permission -> permission instanceof NationPermission)
                .map(permission -> (NationPermission) permission)
                .collect(Collectors.toList());

        Map<String, NationPermission> nationPerms = new HashMap<>();
        perms.forEach(permission -> nationPerms.put(permission.getId(), permission));

        return nationPerms;
    }
}
