package com.sekwah.advancedportals.spigot.utils;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import org.bukkit.Location;

public class ContainerHelpers {

    public static PlayerLocation toPlayerLocation(Location loc) {
        return new PlayerLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public static BlockLocation toBlockLocation(Location loc) {
        return new BlockLocation(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }


}
