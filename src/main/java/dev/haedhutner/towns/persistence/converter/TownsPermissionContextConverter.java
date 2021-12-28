package dev.haedhutner.towns.persistence.converter;

import dev.haedhutner.towns.api.permission.TownsPermissionContext;
import org.spongepowered.api.Sponge;

import javax.persistence.AttributeConverter;

public class TownsPermissionContextConverter implements AttributeConverter<TownsPermissionContext, String> {
    @Override
    public String convertToDatabaseColumn(TownsPermissionContext attribute) {
        return attribute.getId();
    }

    @Override
    public TownsPermissionContext convertToEntityAttribute(String dbData) {
        return Sponge.getRegistry().getType(TownsPermissionContext.class, dbData).get();
    }
}
