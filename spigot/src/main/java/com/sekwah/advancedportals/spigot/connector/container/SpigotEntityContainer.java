package com.sekwah.advancedportals.spigot.connector.container;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.containers.EntityContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.spigot.reflection.MinecraftCustomPayload;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public class SpigotEntityContainer implements EntityContainer {

    @Inject
    private AdvancedPortalsCore portalsCore;

    private final Entity entity;

    public SpigotEntityContainer(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PlayerLocation getLoc() {
        Location loc = this.entity.getLocation();
        return new PlayerLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    @Override
    public BlockLocation getBlockLoc() {
        Location loc = this.entity.getLocation();
        return new BlockLocation(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public double getHeight() {
        return this.entity.getHeight();
    }

    @Override
    public void teleport(PlayerLocation location) {
        this.entity.teleport(new Location(Bukkit.getWorld(location.getWorldName()), location.getPosX(), location.getPosY(), location.getPosZ()));
    }


    @Override
    public WorldContainer getWorld() {
        return new SpigotWorldContainer(this.entity.getWorld());
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }
}
