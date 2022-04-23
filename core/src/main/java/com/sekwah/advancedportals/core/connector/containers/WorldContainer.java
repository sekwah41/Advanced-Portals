package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.data.BlockLocation;

public interface WorldContainer {

    void setBlock(BlockLocation location, String material);

    void setBlockData(BlockLocation location, byte data);

    String getBlock(BlockLocation location);

    byte getBlockData(BlockLocation location);
}
