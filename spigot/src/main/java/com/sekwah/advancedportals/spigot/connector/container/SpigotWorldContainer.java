package com.sekwah.advancedportals.spigot.connector.container;

import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.Orientable;

public class SpigotWorldContainer implements WorldContainer {
    private final World world;

    public SpigotWorldContainer(World world) {
        this.world = world;
    }

    public void setBlock(BlockLocation location, String material) {
        Material mat = Material.getMaterial(material, false);
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
    public BlockAxis getBlockAxis(BlockLocation location) {
        var block = world.getBlockAt(location.getPosX(), location.getPosY(),
                                     location.getPosZ());
        var matData = block.getState().getBlockData();
        if (matData instanceof Orientable rotatable) {
            try {
                return BlockAxis.valueOf(rotatable.getAxis().toString());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void setBlockAxis(BlockLocation location, BlockAxis axis) {
        var block = world.getBlockAt(location.getPosX(), location.getPosY(),
                                     location.getPosZ());
        var matData = block.getState().getBlockData();
        if (matData instanceof Orientable rotatable) {
            rotatable.setAxis(Axis.valueOf(axis.toString()));
            block.setBlockData(rotatable);
        }
    }
}
