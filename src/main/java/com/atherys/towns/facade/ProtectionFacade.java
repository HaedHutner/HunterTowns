package com.atherys.towns.facade;

import com.google.inject.Singleton;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.util.Arrays;
import java.util.List;

@Singleton
public class ProtectionFacade {

    private final List<ItemType> combatItems = Arrays.asList(
            ItemTypes.BOW,
            ItemTypes.SHIELD
    );

    public boolean isCombatItem(ItemType itemType) {
        return combatItems.contains(itemType);
    }

    public boolean isNonPlayerTarget(DamageEntityEvent event, IndirectEntityDamageSource src) {
        return (src.getIndirectSource() instanceof Player)
                && !(event.getTargetEntity() instanceof Player);
    }

    public boolean isRedstone(BlockType blockType) {
        return blockType.getTrait("POWERED").isPresent();
    }

    public boolean isTileEntity(InteractBlockEvent event) {
        return event.getTargetBlock().getLocation().map(location -> location.getTileEntity().isPresent()).orElse(false);
    }

    public boolean isDoor(BlockType blockType) {
        return blockType.getTrait("OPEN").isPresent();
    }
}
