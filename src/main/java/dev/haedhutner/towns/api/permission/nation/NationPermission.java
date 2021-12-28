package dev.haedhutner.towns.api.permission.nation;

import dev.haedhutner.towns.api.permission.Permission;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(NationPermissions.class)
public class NationPermission extends Permission {
    NationPermission(String id, String name) {
        super(id, name);
    }
}
