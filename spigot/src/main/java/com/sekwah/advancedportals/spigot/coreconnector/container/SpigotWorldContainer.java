package com.sekwah.advancedportals.spigot.coreconnector.container;

import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.connector.container.WorldContainer;
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

    public void setBlock(PortalLocation location, String material) {
        this.world.getBlockAt(location.posX, location.posY, location.posZ).setType(Material.getMaterial(material));
    }

    public void setBlockData(PortalLocation location, byte data) {
        MaterialData matData = world.getBlockAt(location.posX, location.posY, location.posZ).getState().getData();
        if(matData instanceof Directional) {
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
