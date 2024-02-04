package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;

public interface EntityContainer {

    PlayerLocation getLoc();

    double getHeight();

    BlockLocation getBlockLoc();

    boolean teleport(PlayerLocation location);

    WorldContainer getWorld();

    String getName();

    String getWorldName();
}
