package dev.haedhutner.towns.integration;

import dev.haedhutner.chat.AtherysChat;
import dev.haedhutner.towns.chat.NationChannel;
import dev.haedhutner.towns.chat.TownChannel;

public final class AtherysChatIntegration {
    public static void registerChannels() {
        AtherysChat.getInstance().getChatService().registerChannel(new TownChannel());
        AtherysChat.getInstance().getChatService().registerChannel(new NationChannel());
    }
}
