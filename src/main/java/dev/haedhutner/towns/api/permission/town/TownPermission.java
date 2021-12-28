package dev.haedhutner.towns.api.permission.town;

import dev.haedhutner.towns.api.permission.Permission;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(TownPermissions.class)
public class TownPermission extends Permission {
    TownPermission(String id, String name) {
        super(id, name);
    }
}
