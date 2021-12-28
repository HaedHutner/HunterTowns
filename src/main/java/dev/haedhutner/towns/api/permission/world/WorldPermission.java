package dev.haedhutner.towns.api.permission.world;

import dev.haedhutner.towns.api.permission.Permission;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(WorldPermissions.class)
public class WorldPermission extends Permission {

    private String commandElementName;
    
    WorldPermission(String id, String commandElementName, String name) {
        super(id, name);
        this.commandElementName = commandElementName;
    }

    public String getCommandElementName() {
        return commandElementName;
    }
}
