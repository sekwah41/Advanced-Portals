package com.sekwah.advancedportals.bukkit.listeners;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.PluginMessages;
import com.sekwah.advancedportals.bukkit.api.events.WarpEvent;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.portals.AdvancedPortal;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class Listeners implements Listener {

    private static boolean UseOnlyServerAxe = false;
    private static Material WandMaterial;

    private final AdvancedPortalsPlugin plugin;

    @SuppressWarnings("deprecation")
    public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");

        String ItemID = config.getConfig().getString("AxeItemId");

        if (ItemID == null) {
            WandMaterial = Material.IRON_AXE;
        } else {
            WandMaterial = Material.getMaterial(ItemID);
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        int cleanPeriod = config.getConfig().getInt("CleanUpPeriod", 120);
        int period = 20 * 60 * cleanPeriod;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new CooldownDataRemovalTask(), period, period);
    }

    @SuppressWarnings("deprecation")
    public static void reloadValues(AdvancedPortalsPlugin plugin) {

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");

        String ItemID = config.getConfig().getString("AxeItemId");

        WandMaterial = Material.getMaterial(ItemID);
    }

    @EventHandler(ignoreCancelled = true)
    public void spawnMobEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL
                && Portal.inPortalRegion(event.getLocation(), Portal.getPortalProtectionRadius())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWorldChangeEvent(PlayerChangedWorldEvent event) {
        Portal.joinCooldown.put(event.getPlayer().getName(), System.currentTimeMillis());
    }

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player.hasMetadata("leaveDesti")) {
            Destination.warp(player, player.getMetadata("leaveDesti").get(0).asString(),
                    false, true);
        }
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
    	Player player = event.getPlayer();
		
    	Portal.joinCooldown.put(player.getName(), System.currentTimeMillis());
	
    	String uuid = player.getUniqueId().toString();
    	
    	if (plugin.PlayerDestiMap.containsKey(uuid)) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
				Destination.warp(player, plugin.PlayerDestiMap.get(uuid), false, true);
				plugin.PlayerDestiMap.remove(uuid);
				
			}, 1L);
			
    	}
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMoveEvent(PlayerMoveEvent event) {
        // will check if the player is in the portal or not.
        if (!Portal.portalsActive || event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        Location loc = event.getTo();
        Location eyeLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + player.getEyeHeight(), loc.getZ());

        checkTriggerLocations(player, false, loc, eyeLoc);

    }

    public void checkTriggerLocations(Player player, boolean useDelayed, Location... locations) {
        for (AdvancedPortal portal : Portal.portals) {
            boolean delayed = portal.hasArg("delayed") && portal.getArg("delayed").equalsIgnoreCase("true");
            for (Location loc : locations) {
                if (delayed == useDelayed) {
                    if (delayed ? Portal.locationInPortal(portal, loc, 1)
                            : Portal.locationInPortalTrigger(portal, loc)) {
                        if (portal.getTriggers().contains(Material.NETHER_PORTAL)) {
                            if (player.getGameMode().equals(GameMode.CREATIVE)) {
                                player.setMetadata("hasWarped", new FixedMetadataValue(plugin, true));
                                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new RemoveWarpData(player), 10);
                            }
                        }
                        if (portal.getTriggers().contains(Material.LAVA)) {
                            player.setMetadata("lavaWarped", new FixedMetadataValue(plugin, true));
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new RemoveLavaData(player), 10);
                        }
                        if (portal.inPortal.contains(player.getUniqueId()))
                            return;
                        WarpEvent warpEvent = new WarpEvent(player, portal);
                        plugin.getServer().getPluginManager().callEvent(warpEvent);

                        if (!warpEvent.isCancelled())
                            Portal.activate(player, portal);

                        if (!delayed)
                            portal.inPortal.add(player.getUniqueId());
                        return;
                    } else if (!delayed)
                        portal.inPortal.remove(player.getUniqueId());
                }
            }
        }
    }

    class CooldownDataRemovalTask implements Runnable {

        private int removed;

        @Override
        public void run() {
            boolean canRemove = true;
            while (canRemove) {
                canRemove = Portal.cooldown.entrySet().removeIf(e -> {
                    HashMap<String, Long> cds = e.getValue();
                    if (cds == null) {
                        removed++;
                        return true;
                    } else {
                        cds.entrySet().removeIf(entry -> shouldRemovePortalCooldown(entry));
                        if (cds.isEmpty()) {
                            removed++;
                            return true;
                        }
                    }
                    return false;
                });
            }

            // Make sure maps are never too big than they need to be
            if (removed > 16) {
                resizeMaps();
                removed = 0;
            }
        }

        private boolean shouldRemovePortalCooldown(Map.Entry<String, Long> entry) {
            String portalName = entry.getKey();
            AdvancedPortal portal = Portal.getPortal(portalName);
            if (portal != null) {
                long portalCD = entry.getValue();
                int diff = (int) ((System.currentTimeMillis() - portalCD) / 1000);
                int portalCooldown = -1;
                try {
                    portalCooldown = Integer.parseInt(portal.getArg("cooldowndelay"));
                    return diff >= portalCooldown; // cooldown expired
                } catch (Exception exc) {
                    return true;
                }
            }
            return true;
        }

        private void resizeMaps() {
            HashMap<String, HashMap<String, Long>> newCooldowns = new HashMap<String, HashMap<String, Long>>(Math.max(Portal.cooldown.size() * 2, 10));
            newCooldowns.putAll(Portal.cooldown);
            Portal.cooldown = newCooldowns;

            HashMap<String, Long> newJoinCooldowns = new HashMap<String, Long>(Math.max(Portal.joinCooldown.size() * 2, 10));
            newJoinCooldowns.putAll(Portal.joinCooldown);
            Portal.joinCooldown = newJoinCooldowns;
        }
    }

    // These are here because java 7 can only take finals straight into a runnable
    class RemoveLavaData implements Runnable {

        private Player player;

        public RemoveLavaData(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            player.removeMetadata("lavaWarped", plugin);
            player.setFireTicks(0);
        }
    };

    class RemoveWarpData implements Runnable {

        private Player player;

        public RemoveWarpData(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            if (player != null && player.isOnline()) {
                player.removeMetadata("hasWarped", plugin);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCombustEntityEvent(EntityCombustEvent event) {
        if (event.getEntity() instanceof Player && Portal.inPortalTriggerRegion(event.getEntity().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && (event.getCause() == EntityDamageEvent.DamageCause.LAVA
                || event.getCause() == EntityDamageEvent.DamageCause.FIRE
                || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
            if (event.getEntity().hasMetadata("lavaWarped")
                    | Portal.inPortalTriggerRegion(event.getEntity().getLocation()))
                event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPortalEvent(PlayerPortalEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();

        if (!player.hasMetadata("hasWarped")) {
            Location loc = event.getFrom();
            Location eyeLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + player.getEyeHeight(), loc.getZ());

            checkTriggerLocations(player, true, loc, eyeLoc);
        }

        if (player.hasMetadata("hasWarped") | Portal.inPortalRegion(event.getFrom(), 1))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemInteract(PlayerInteractEvent event) {

        // will detect if the player is using an axe so the points of a portal can be
        // set
        // also any other detections such as sign interaction or basic block protection
        Player player = event.getPlayer();

        if (player.hasMetadata("selectingPortal")
                && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            for (AdvancedPortal portal : Portal.portals) {
                if (Portal.locationInPortal(portal, event.getClickedBlock().getLocation(), 0)) {
                    player.sendMessage(
                            PluginMessages.customPrefix + "\u00A7a You have selected: \u00A7e" + portal.getName());
                    player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, portal.getName())); // adds the
                                                                                                            // name to
                                                                                                            // the
                                                                                                            // metadata
                                                                                                            // of the
                                                                                                            // character
                    event.setCancelled(true);
                    player.removeMetadata("selectingPortal", plugin);
                    return;
                }
            }
            player.sendMessage(PluginMessages.customPrefixFail
                    + "\u00A7c No portal was selected. If you would like to stop selecting please type \u00A7e/portal select \u00A7cagain!");
            event.setCancelled(true);
            return;
        }

        if (player.hasPermission("advancedportals.createportal")) {

            if (event.getItem() != null && event.getItem().getType() == WandMaterial // was type id
                    && (!UseOnlyServerAxe || (checkItemForName(event.getItem()) && event.getItem().getItemMeta()
                            .getDisplayName().equals("\u00A7ePortal Region Selector")))) {

                // This checks if the action was a left or right click and if it was directly
                // effecting a block.
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    Location blockloc = event.getClickedBlock().getLocation();
                    // stores the selection as metadata on the character so then it isn't saved
                    // anywhere, if the player logs out it will
                    // have to be selected again if the player joins, also it does not affect any
                    // other players.
                    player.setMetadata("Pos1X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
                    player.setMetadata("Pos1Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
                    player.setMetadata("Pos1Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
                    player.setMetadata("Pos1World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
                    player.sendMessage(
                            "\u00A7eYou have selected pos1! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY()
                                    + " Z:" + blockloc.getBlockZ() + " World: " + blockloc.getWorld().getName());

                    // Stops the event so the block is not damaged
                    event.setCancelled(true);

                    // Returns the event so no more code is executed(stops unnecessary code being
                    // executed)
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Location blockloc = event.getClickedBlock().getLocation();
                    player.setMetadata("Pos2X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
                    player.setMetadata("Pos2Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
                    player.setMetadata("Pos2Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
                    player.setMetadata("Pos2World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
                    player.sendMessage(
                            "\u00A7eYou have selected pos2! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY()
                                    + " Z:" + blockloc.getBlockZ() + " World: " + blockloc.getWorld().getName());

                    // Stops the event so the block is not interacted with
                    event.setCancelled(true);

                    // Returns the event so no more code is executed(stops unnecessary code being
                    // executed)
                }

            } else if (checkItemForName(event.getItem())
                    && event.getItem().getItemMeta().getDisplayName().equals("\u00A75Portal Block Placer")
                    && event.getAction() == Action.LEFT_CLICK_BLOCK
                    && event.getClickedBlock().getType() == Material.NETHER_PORTAL) {
                BlockData block = event.getClickedBlock().getBlockData();

                if (block instanceof Orientable) {
                    Orientable rotatable = (Orientable) block;
                    if (rotatable.getAxis() == Axis.X) {
                        rotatable.setAxis(Axis.Z);
                    } else {
                        rotatable.setAxis(Axis.X);
                    }
                    event.getClickedBlock().setBlockData(rotatable);
                }
                event.setCancelled(true);
            }
        }

    }

    private boolean checkItemForName(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName();
    }

}
