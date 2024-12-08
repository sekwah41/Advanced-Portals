package com.sekwah.advancedportals.spigot.connector.container;

import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import java.awt.*;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.block.data.Orientable;

public class SpigotWorldContainer implements WorldContainer {
    private final World world;

    // Should only be false for 1.13 and 1.13.2, though just to avoid possible
    // crashes
    private static boolean endGatewaySetAgeExists;

    static {
        try {
            endGatewaySetAgeExists =
                EndGateway.class.getMethod("setAge", long.class) != null;
        } catch (NoSuchMethodException e) {
            endGatewaySetAgeExists = false;
        }
    }

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

    @Override
    public void disableBeacon(BlockLocation location) {
        if (!endGatewaySetAgeExists)
            return;
        var block = this.world.getBlockAt(
            location.getPosX(), location.getPosY(), location.getPosZ());
        var blockType = block.getType();
        if (blockType == Material.END_GATEWAY
            && block.getState() instanceof EndGateway endGateway) {
            endGateway.setAge(Long.MIN_VALUE);
            endGateway.update();
        }
    }

    @Override
    public void disableBeacon(AdvancedPortal portal) {
        if (!endGatewaySetAgeExists)
            return;
        BlockLocation maxLoc = portal.getMaxLoc();
        BlockLocation minLoc = portal.getMinLoc();

        for (int x = minLoc.getPosX(); x <= maxLoc.getPosX(); x++) {
            for (int y = minLoc.getPosY(); y <= maxLoc.getPosY(); y++) {
                for (int z = minLoc.getPosZ(); z <= maxLoc.getPosZ(); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.END_GATEWAY) {
                        EndGateway tileState = (EndGateway) block.getState();
                        tileState.setAge(Long.MIN_VALUE);
                        tileState.update();
                    }
                }
            }
        }
    }
}
