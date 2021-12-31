package dev.haedhutner.towns.chat;

import dev.haedhutner.chat.model.ChatChannel;
import dev.haedhutner.chat.service.ChatService;
import dev.haedhutner.core.HunterCore;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.TownsConfig;
import dev.haedhutner.towns.facade.ResidentFacade;
import dev.haedhutner.towns.facade.TownFacade;
import dev.haedhutner.towns.model.entity.Town;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.chat.ChatType;

import javax.annotation.Nullable;
import java.util.*;

public class TownChannel extends ChatChannel {
    public static final String PERMISSION = "atherystowns.town.chat";

    public TownChannel() {
        super("town");
        Set<String> aliases = new HashSet<>();
        aliases.add("tc");
        this.setAliases(aliases);
        this.setPermission(PERMISSION);
        this.setPrefix(HunterTowns.getInstance(TownsConfig.class).TOWN_CHAT_PREFIX);
        this.setSuffix("");
        this.setFormat("%cprefix %player: %message %csuffix");
        this.setName("&bTown");
    }


    @Override
    public Optional<Text> transformMessage(@Nullable Object sender, MessageReceiver recipient, Text original, ChatType type) {
        return HunterCore.getInstance(ChatService.class).formatMessage(this, sender, recipient, original);
    }

    @Override
    public Collection<MessageReceiver> getMembers() {
        return HunterCore.getInstance(ChatService.class).getChannelMembers(this);
    }

    @Override
    public void send(@Nullable Object sender, Text original, ChatType type) {
        HunterCore.getInstance(ChatService.class).sendMessageToChannel(this, sender, original, type);
    }

    @Override
    public Collection<MessageReceiver> getMembers(Object sender) {
        if (sender instanceof Player) {
            Optional<Town> playerTown = HunterTowns.getInstance(ResidentFacade.class).getPlayerTown((Player) sender);
            if (playerTown.isPresent()) {
                return new HashSet<>(HunterTowns.getInstance(TownFacade.class).getOnlineTownMembers(playerTown.get()));
            }
        }

        return Collections.emptySet();
    }
}
