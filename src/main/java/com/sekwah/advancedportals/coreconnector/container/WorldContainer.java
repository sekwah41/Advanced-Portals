package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.entities.PortalLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;

public class WorldContainer {

    private final World world;

    public WorldContainer(World world) {
        this.world = world;
    }

    public void setBlock(PortalLocation location, String material) {
        this.world.getBlockAt(location.posX, location.posY, location.posZ).setType(Material.getMaterial(material));
    }

    public void setBlockData(PortalLocation location, byte data) {
        MaterialData matData = world.getBlockAt(location.posX, location.posY, location.posZ).getState().getData();
        if(matData instanceof Directional) {
            System.out.println("IS DIRECTIONAL");
            Directional dir = (Directional) world.getBlockAt(location.posX, location.posY, location.posZ).getState().getData();
            dir.setFacingDirection(BlockFace.NORTH);
        }

    }

    public String getBlock(PortalLocation location) {
        return this.world.getBlockAt(location.posX, location.posY, location.posZ).getType().toString();
    }

    public byte getBlockData(PortalLocation location) {
        return 0;
    }
}
