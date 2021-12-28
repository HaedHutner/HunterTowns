package dev.haedhutner.towns.persistence.converter;

import dev.haedhutner.towns.api.permission.world.WorldPermission;
import org.spongepowered.api.Sponge;

import javax.persistence.AttributeConverter;

public class PermissionConverter implements AttributeConverter<WorldPermission, String> {

    @Override
    public String convertToDatabaseColumn(WorldPermission attribute) {
        return attribute.getId();
    }

    @Override
    public WorldPermission convertToEntityAttribute(String dbData) {
        return Sponge.getRegistry().getType(WorldPermission.class, dbData).get();
    }
}
