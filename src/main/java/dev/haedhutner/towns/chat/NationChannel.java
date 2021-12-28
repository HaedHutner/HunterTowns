package dev.haedhutner.towns.chat;

import dev.haedhutner.chat.model.ChatChannel;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.model.entity.Nation;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.*;

public class NationChannel extends ChatChannel {
    public static final String PERMISSION = "atherystowns.nation.chat";

    public NationChannel() {
        super("nation");
        Set<String> aliases = new HashSet<>();
        aliases.add("nc");
        this.setAliases(aliases);
        this.setPermission(PERMISSION);
        this.setPrefix(HunterTowns.getInstance().getConfig().NATION_CHAT_PREFIX);
        this.setSuffix("");
        this.setFormat("%cprefix %player: %message %csuffix");
        this.setName("&bNation");
    }

    @Override
    public Collection<MessageReceiver> getMembers(Object sender) {

        if (sender instanceof Player) {
            Optional<Nation> playerNation = HunterTowns.getInstance().getResidentFacade().getPlayerNation((Player) sender);
            if (playerNation.isPresent()) {
                return new HashSet<>(HunterTowns.getInstance().getNationFacade().getOnlineNationMembers(playerNation.get()));
            }
        }

        return Collections.emptySet();
    }
}
