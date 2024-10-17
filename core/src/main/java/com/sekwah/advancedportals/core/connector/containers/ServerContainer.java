package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.tags.CommandTag;
import java.util.List;
import java.util.UUID;

public interface ServerContainer {
    WorldContainer getWorld(String name);

    PlayerContainer getPlayer(String name);

    PlayerContainer getPlayer(UUID name);

    List<String> getTriggerBlocks();

    PlayerContainer[] getPlayers();

    void registerOutgoingChannel(String channel);

    void registerIncomingChannel(String channel);

    void dispatchCommand(UUID uuid, String command,
                         CommandTag.CommandLevel commandLevel);
}
