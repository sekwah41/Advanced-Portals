package com.sekwah.advancedportals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Selection {

    private static Material blockType = Material.STAINED_GLASS;
    private static int timeout = 10;
    private static byte metadata = 14;

    @SuppressWarnings("deprecation")
    public static void loadData(AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");

        timeout = config.getConfig().getInt("ShowSelectionShowDuration");

        String BlockID = config.getConfig().getString("ShowSelectionBlockID");
        try {
            blockType = Material.getMaterial(Integer.parseInt(BlockID));
        } catch (Exception e) {
            blockType = Material.getMaterial(BlockID);
        }

        if (blockType == null) {
            blockType = Material.STAINED_GLASS;
        }

        metadata = (byte) config.getConfig().getInt("ShowSelectionBlockData");
    }

    @SuppressWarnings("deprecation")
    public static void show(final Player player, final AdvancedPortalsPlugin plugin, String portalName) {

        int LowX = 0;
        int LowY = 0;
        int LowZ = 0;

        int HighX = 0;
        int HighY = 0;
        int HighZ = 0;

        if (portalName != null) {
            ConfigAccessor portalConfig = new ConfigAccessor(plugin, "portals.yml");

            LowX = portalConfig.getConfig().getInt(portalName + ".pos2.X");
            LowY = portalConfig.getConfig().getInt(portalName + ".pos2.Y");
            LowZ = portalConfig.getConfig().getInt(portalName + ".pos2.Z");

            HighX = portalConfig.getConfig().getInt(portalName + ".pos1.X");
            HighY = portalConfig.getConfig().getInt(portalName + ".pos1.Y");
            HighZ = portalConfig.getConfig().getInt(portalName + ".pos1.Z");
        } else {
            if (player.getMetadata("Pos1X").get(0).asInt() > player.getMetadata("Pos2X").get(0).asInt()) {
                LowX = player.getMetadata("Pos2X").get(0).asInt();
                HighX = player.getMetadata("Pos1X").get(0).asInt();
            } else {
                LowX = player.getMetadata("Pos1X").get(0).asInt();
                HighX = player.getMetadata("Pos2X").get(0).asInt();
            }
            if (player.getMetadata("Pos1Y").get(0).asInt() > player.getMetadata("Pos2Y").get(0).asInt()) {
                LowY = player.getMetadata("Pos2Y").get(0).asInt();
                HighY = player.getMetadata("Pos1Y").get(0).asInt();
            } else {
                LowY = player.getMetadata("Pos1Y").get(0).asInt();
                HighY = player.getMetadata("Pos2Y").get(0).asInt();
            }
            if (player.getMetadata("Pos1Z").get(0).asInt() > player.getMetadata("Pos2Z").get(0).asInt()) {
                LowZ = player.getMetadata("Pos2Z").get(0).asInt();
                HighZ = player.getMetadata("Pos1Z").get(0).asInt();
            } else {
                LowZ = player.getMetadata("Pos1Z").get(0).asInt();
                HighZ = player.getMetadata("Pos2Z").get(0).asInt();
            }
        }

        final Location pos1 = new Location(player.getWorld(), LowX, LowY, LowZ);
        final Location pos2 = new Location(player.getWorld(), HighX, HighY, HighZ);

		/*
		 * There are alot of for loops at the moment, when i find an easier way to do these other that a load of if statements
		 * then i will change it, but for now its the best way i can think of for doing this. 
		 */

        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, LowY, LowZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, LowY, HighZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), LowX, LowY, z);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), HighX, LowY, z);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), LowX, y, LowZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), LowX, y, HighZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), HighX, y, LowZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), HighX, y, HighZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, HighY, HighZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, HighY, LowZ);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), LowX, HighY, z);
            player.sendBlockChange(loc, blockType, metadata);
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), HighX, HighY, z);
            player.sendBlockChange(loc, blockType, metadata);
        }


        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                Selection.hide(player, plugin, pos1, pos2);
            }
        }, timeout * 20);

    }

    @SuppressWarnings("deprecation")
    protected static void hide(Player player, AdvancedPortalsPlugin plugin, Location pos1, Location pos2) {

        int LowX = pos1.getBlockX();
        int LowY = pos1.getBlockY();
        int LowZ = pos1.getBlockZ();

        int HighX = pos2.getBlockX();
        int HighY = pos2.getBlockY();
        int HighZ = pos2.getBlockZ();

        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, LowY, LowZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, LowY, HighZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), LowX, LowY, z);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), HighX, LowY, z);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), LowX, y, LowZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), LowX, y, HighZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), HighX, y, LowZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int y = LowY; y <= HighY; y++) {
            Location loc = new Location(player.getWorld(), HighX, y, HighZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, HighY, HighZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int x = LowX; x <= HighX; x++) {
            Location loc = new Location(player.getWorld(), x, HighY, LowZ);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), LowX, HighY, z);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }
        for (int z = LowZ; z <= HighZ; z++) {
            Location loc = new Location(player.getWorld(), HighX, HighY, z);
            player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
        }

    }

}
