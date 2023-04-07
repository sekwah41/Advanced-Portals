package com.sekwah.advancedportals.spigot.connector.container;

import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;

public class SpigotWorldContainer implements WorldContainer {

    private final World world;

    public SpigotWorldContainer(World world) {
        this.world = world;
    }

    public void setBlock(BlockLocation location, String material) {
        this.world.getBlockAt(location.posX, location.posY, location.posZ).setType(Material.getMaterial(material));
    }

    public void setBlockData(BlockLocation location, byte data) {
        MaterialData matData = world.getBlockAt(location.posX, location.posY, location.posZ).getState().getData();
        if(matData instanceof Directional) {
            Directional dir = (Directional) world.getBlockAt(location.posX, location.posY, location.posZ).getState().getData();
            dir.setFacingDirection(BlockFace.NORTH);
        }

    }

    public String getBlock(BlockLocation location) {
        return this.world.getBlockAt(location.posX, location.posY, location.posZ).getType().toString();
    }

    public byte getBlockData(BlockLocation location) {
        return 0;
    }
}
