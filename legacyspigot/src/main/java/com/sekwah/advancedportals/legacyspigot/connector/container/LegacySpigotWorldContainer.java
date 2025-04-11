package com.sekwah.advancedportals.legacyspigot.connector.container;

import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import org.bukkit.Material;
import org.bukkit.World;

public class LegacySpigotWorldContainer implements WorldContainer {
    private final World world;

    public LegacySpigotWorldContainer(World world) {
        this.world = world;
    }

    public void setBlock(BlockLocation location, String material) {
        Material mat = Material.getMaterial(material);
        if (mat != null)
            this.world
                .getBlockAt(location.getPosX(), location.getPosY(),
                            location.getPosZ())
                .setType(mat);
    }

    public String getBlock(BlockLocation location) {
        return this.world
            .getBlockAt(location.getPosX(), location.getPosY(),
                        location.getPosZ())
            .getType()
            .toString();
    }

    @Override
    public void disableBeacon(BlockLocation location) {
    }

    @Override
    public BlockAxis getBlockAxis(BlockLocation location) {
        return null;
    }

    @Override
    public void setBlockAxis(BlockLocation location, BlockAxis axis) {
    }

    @Override
    public void disableBeacon(AdvancedPortal portal) {
    }

    @Override
    public boolean isWaterlogged(BlockLocation location) {
        // Waterlogged blocks were introduced in 1.13.
        return false;
    }
}
