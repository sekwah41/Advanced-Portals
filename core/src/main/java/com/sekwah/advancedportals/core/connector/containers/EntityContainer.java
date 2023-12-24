package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;

public interface EntityContainer {

    PlayerLocation getLoc();

    double getHeight();

    BlockLocation getBlockLoc();

    void teleport(PlayerLocation location);

    WorldContainer getWorld();

}
