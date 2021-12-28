package dev.haedhutner.towns.api.permission;

import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.api.permission.world.WorldPermissions;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldPermissionRegistryModule implements CatalogRegistryModule<WorldPermission> {

    private Map<String, WorldPermission> permissions = new HashMap<>();

    public WorldPermissionRegistryModule() {
        put(
                WorldPermissions.BUILD,
                WorldPermissions.DESTROY,
                WorldPermissions.DAMAGE_NONPLAYERS,
                WorldPermissions.DAMAGE_PLAYERS,
                WorldPermissions.INTERACT_TILE_ENTITIES,
                WorldPermissions.INTERACT_DOORS,
                WorldPermissions.INTERACT_REDSTONE,
                WorldPermissions.INTERACT_ENTITIES,
                WorldPermissions.SPAWN_ENTITIES
        );
    }

    private void put(WorldPermission permission) {
        permissions.put(permission.getId(), permission);
    }

    private void put(WorldPermission... permissions) {
        for (WorldPermission permission : permissions) {
            put(permission);
        }
    }

    @Override
    public Optional<WorldPermission> getById(String id) {
        return Optional.ofNullable(permissions.get(id));
    }

    @Override
    public Collection<WorldPermission> getAll() {
        return permissions.values();
    }
}
