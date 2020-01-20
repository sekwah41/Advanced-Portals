package com.sekwah.advancedportals.bukkit.destinations;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.PluginMessages;
import com.sekwah.advancedportals.bukkit.effects.WarpEffects;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.logging.Level;

public class Destination {

    private static AdvancedPortalsPlugin plugin;

    private static boolean TELEPORT_RIDING = false;
    public static int PORTAL_MESSAGE_DISPLAY = 0;

    public Destination(AdvancedPortalsPlugin plugin) {
        Destination.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        TELEPORT_RIDING = config.getConfig().getBoolean("WarpRiddenEntity");
        PORTAL_MESSAGE_DISPLAY = config.getConfig().getInt("WarpMessageDisplay");
    }

    // TODO add permissions for destinations.

    // TODO try keeping the chunks loaded and add different delays to events to make
    // the horse teleport when you have more time.(its an annoying bug caused by changed)
    // in mc

    public static void create(Location location, String name) {
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");

        config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());

        config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
        config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
        config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());

        config.getConfig().set(name.toLowerCase() + ".pos.pitch", location.getPitch());
        config.getConfig().set(name.toLowerCase() + ".pos.yaw", location.getYaw());

        config.saveConfig();
    }

    public static void move(Location location, String name) {
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");

        config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());

        config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
        config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
        config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());

        config.getConfig().set(name.toLowerCase() + ".pos.pitch", location.getPitch());
        config.getConfig().set(name.toLowerCase() + ".pos.yaw", location.getYaw());

        config.saveConfig();
    }

    public static void rename(String oldName, String newName) {
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");

        config.getConfig().set(newName.toLowerCase() + ".world", config.getConfig().getString(oldName + ".world"));

        config.getConfig().set(newName.toLowerCase() + ".pos.X", config.getConfig().getDouble(oldName + ".pos.X"));
        config.getConfig().set(newName.toLowerCase() + ".pos.Y", config.getConfig().getDouble(oldName + ".pos.Y"));
        config.getConfig().set(newName.toLowerCase() + ".pos.Z", config.getConfig().getDouble(oldName + ".pos.Z"));

        config.getConfig().set(newName.toLowerCase() + ".pos.pitch", config.getConfig().getDouble(oldName + ".pos.pitch"));
        config.getConfig().set(newName.toLowerCase() + ".pos.yaw", config.getConfig().getDouble(oldName + ".pos.yaw"));

        remove(oldName);

        config.saveConfig();
    }

    public static void remove(String name) {
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");

        config.getConfig().set(name.toLowerCase() + ".world", null);

        config.getConfig().set(name.toLowerCase() + ".pos.X", null);
        config.getConfig().set(name.toLowerCase() + ".pos.Y", null);
        config.getConfig().set(name.toLowerCase() + ".pos.Z", null);

        config.getConfig().set(name.toLowerCase() + ".pos.pitch", null);
        config.getConfig().set(name.toLowerCase() + ".pos.yaw", null);

        config.getConfig().set(name.toLowerCase() + ".pos", null);

        config.getConfig().set(name.toLowerCase(), null);

        config.saveConfig();
    }

    public static boolean warp(Player player, String name) {
        return warp(player, name, false);
    }

    public static boolean warp(Player player, String name, boolean hideActionbar) {
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
        if (config.getConfig().getString(name + ".world") != null) {
            Location loc = player.getLocation();
            if (Bukkit.getWorld(config.getConfig().getString(name + ".world")) != null) {
                loc.setWorld(Bukkit.getWorld(config.getConfig().getString(name + ".world")));

                loc.setX(config.getConfig().getDouble(name + ".pos.X"));
                loc.setY(config.getConfig().getDouble(name + ".pos.Y"));
                loc.setZ(config.getConfig().getDouble(name + ".pos.Z"));

                loc.setPitch((float) config.getConfig().getDouble(name + ".pos.pitch"));
                loc.setYaw((float) config.getConfig().getDouble(name + ".pos.yaw"));

                WarpEffects.activateParticles(player);
                WarpEffects.activateSound(player);
                Chunk c = loc.getChunk();
                Entity riding = player.getVehicle();
                if (!c.isLoaded()) c.load();

                if (player.getVehicle() != null && TELEPORT_RIDING) {

                    riding.eject();
                    riding.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    riding.setPassenger(player);

                } else {
                    player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
                WarpEffects.activateParticles(player);
                WarpEffects.activateSound(player);

                if (PORTAL_MESSAGE_DISPLAY == 1) {
                    player.sendMessage("");
                    player.sendMessage(PluginMessages.customPrefixFail + "\u00A7a You have been warped to \u00A7e" + name.replaceAll("_", " ") + "\u00A7a.");
                    player.sendMessage("");
                } else if (PORTAL_MESSAGE_DISPLAY == 2 && !hideActionbar) {
                    plugin.compat.sendActionBarMessage("\u00A7aYou have warped to \u00A7e" + name.replaceAll("_", " ") + "\u00A7a.", player);
                }

                return true;
            } else {
                player.sendMessage(PluginMessages.customPrefixFail + "\u00A7c The destination you are trying to warp to seems to be linked to a world that doesn't exist!");
                plugin.getLogger().log(Level.SEVERE, "The destination '" + name + "' is linked to the world "
                        + config.getConfig().getString(name + ".world") + " which doesnt seem to exist any more!");
            }
        } else {
            player.sendMessage(PluginMessages.customPrefix + "\u00A7c The destination you are currently attempting to warp to doesnt exist!");
            plugin.getLogger().log(Level.SEVERE, "The destination '" + name + "' has just had a warp "
                    + "attempt and either the data is corrupt or that destination doesn't exist!");
        }
        return false;
    }
}
