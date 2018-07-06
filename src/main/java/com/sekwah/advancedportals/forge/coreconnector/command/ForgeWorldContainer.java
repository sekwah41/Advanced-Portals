package com.sekwah.advancedportals.forge.coreconnector.command;

import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.WorldContainer;

public class ForgeWorldContainer implements WorldContainer {
    @Override
    public void setBlock(PortalLocation location, String material) {

    }

    @Override
    public void setBlockData(PortalLocation location, byte data) {

    }

    @Override
    public String getBlock(PortalLocation location) {
        return null;
    }

    @Override
    public byte getBlockData(PortalLocation location) {
        return 0;
    }
}
