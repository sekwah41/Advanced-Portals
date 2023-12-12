package com.sekwah.advancedportals.core.connector.containers;

import java.util.UUID;

public interface ServerContainer {

    WorldContainer getWorld(String name);

    PlayerContainer getPlayer(String name);

    PlayerContainer getPlayer(UUID name);

    PlayerContainer[] getPlayers();

}
