package dev.haedhutner.towns.chat;

import dev.haedhutner.chat.model.AtherysChannel;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.model.entity.Town;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.*;

public class TownChannel extends AtherysChannel {
    public static final String PERMISSION = "atherystowns.town.chat";

    public TownChannel() {
        super("town");
        Set<String> aliases = new HashSet<>();
        aliases.add("tc");
        this.setAliases(aliases);
        this.setPermission(PERMISSION);
        this.setPrefix(HunterTowns.getInstance().getConfig().TOWN_CHAT_PREFIX);
        this.setSuffix("");
        this.setFormat("%cprefix %player: %message %csuffix");
        this.setName("&bTown");
    }


    @Override
    public Collection<MessageReceiver> getMembers(Object sender) {
        if (sender instanceof Player) {
            Optional<Town> playerTown = HunterTowns.getInstance().getResidentFacade().getPlayerTown((Player) sender);
            if (playerTown.isPresent()) {
                return new HashSet<>(HunterTowns.getInstance().getTownFacade().getOnlineTownMembers(playerTown.get()));
            }
        }

        return Collections.emptySet();
    }
}
