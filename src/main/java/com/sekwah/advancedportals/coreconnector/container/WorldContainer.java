package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.data.PortalLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;

public interface WorldContainer {

    void setBlock(PortalLocation location, String material);

    void setBlockData(PortalLocation location, byte data);

    String getBlock(PortalLocation location);

    byte getBlockData(PortalLocation location);
}
