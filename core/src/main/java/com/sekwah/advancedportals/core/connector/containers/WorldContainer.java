package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;

public interface WorldContainer {

    void setBlock(BlockLocation location, String material);

    String getBlock(BlockLocation location);

    BlockAxis getBlockAxis(BlockLocation location);

    void setBlockAxis(BlockLocation location, BlockAxis axis);
}
