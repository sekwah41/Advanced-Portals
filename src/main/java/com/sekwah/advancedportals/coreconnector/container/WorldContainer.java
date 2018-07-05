package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.entities.PortalLocation;

public interface WorldContainer {

    void setBlock(PortalLocation location, String material);

    void setBlockData(PortalLocation location, byte data);

    String getBlock(PortalLocation location);

    byte getBlockData(PortalLocation location);
}
