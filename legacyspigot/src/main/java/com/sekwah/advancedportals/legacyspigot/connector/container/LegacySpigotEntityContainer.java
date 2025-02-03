package com.sekwah.advancedportals.legacyspigot.connector.container;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.containers.EntityContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Just a temporary container for whenever advanced portals needs to get data
 * from a player
 */
public class LegacySpigotEntityContainer implements EntityContainer {
    @Inject
    private AdvancedPortalsCore portalsCore;

    private final Entity entity;

    public LegacySpigotEntityContainer(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PlayerLocation getLoc() {
        Location loc = this.entity.getLocation();
        return new PlayerLocation(loc.getWorld().getName(), loc.getX(),
                                  loc.getY(), loc.getZ(), loc.getYaw(),
                                  loc.getPitch());
    }

    @Override
    public BlockLocation getBlockLoc() {
        Location loc = this.entity.getLocation();
        return new BlockLocation(loc.getWorld().getName(), loc.getBlockX(),
                                 loc.getBlockY(), loc.getBlockZ());
    }

    @Override
    public double getHeight() {
        return 1.5;
    }

    @Override
    public boolean teleport(PlayerLocation location) {
        return this.entity.teleport(new Location(
            Bukkit.getWorld(location.getWorldName()), location.getPosX(),
            location.getPosY(), location.getPosZ()));
    }

    @Override
    public WorldContainer getWorld() {
        return new LegacySpigotWorldContainer(this.entity.getWorld());
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }

    @Override
    public String getWorldName() {
        return this.entity.getWorld().getName();
    }

    @Override
    public void setVelocity(Vector vector) {
        this.entity.setVelocity(new org.bukkit.util.Vector(
            vector.getX(), vector.getY(), vector.getZ()));
    }
}
