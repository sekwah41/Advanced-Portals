package com.sekwah.advancedportals.listeners;

import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.events.WarpEvent;
import com.sekwah.advancedportals.portals.AdvancedPortal;
import com.sekwah.advancedportals.portals.Portal;
import com.sun.org.apache.regexp.internal.RE;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class Listeners implements Listener {

    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("\u00A7eP...
    private static boolean UseOnlyServerAxe = false;
    private static Material WandMaterial;
    private static boolean ShowBungeeMessage;
    private final AdvancedPortalsPlugin plugin;
    private int PortalMessagesDisplay = 2;

    @SuppressWarnings("deprecation")
    public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");

        String ItemID = config.getConfig().getString("AxeItemId");

        PortalMessagesDisplay = config.getConfig().getInt("WarpMessageDisplay");

        try {
            WandMaterial = Material.getMaterial(Integer.parseInt(ItemID));
        } catch (Exception e) {
            WandMaterial = Material.getMaterial(ItemID);
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("deprecation")
    public static void reloadValues(AdvancedPortalsPlugin plugin) {

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");

        ShowBungeeMessage = config.getConfig().getBoolean("ShowBungeeWarpMessage");

        String ItemID = config.getConfig().getString("AxeItemId");

        try {
            WandMaterial = Material.getMaterial(Integer.parseInt(ItemID));
        } catch (Exception e) {
            WandMaterial = Material.getMaterial(ItemID);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMoveEvent(PlayerMoveEvent event) {
        // will check if the player is in the portal or not.
        if (!Portal.portalsActive) {
            return;
        }

        Player player = event.getPlayer();

        Location fromloc = event.getFrom();
        Location loc = event.getTo();
        // Potentially fixes that stupid error cauzed by a bukkit update.
        // Would save event.getTo() as eyeLoc and change the Y position but that seemed to teleport players.
        Location eyeLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + player.getEyeHeight(), loc.getZ());
        //System.out.println(loc.getBlock().getType()); // for debugging, remove or comment out when not needed
        // This is probably the culprite of the bloody problem, setting the location its pointing to the event location
        // rather than sorta making a clone of the object.
        //System.out.println(loc.getBlock().getType()); // for debugging, remove or comment out when not needed
        for (AdvancedPortal portal : Portal.Portals) {
            if (loc.getWorld() != null && portal.worldName.equals(loc.getWorld().getName())) {
                if (portal.trigger.equals(loc.getBlock().getType())
                        || portal.trigger.equals(eyeLoc.getBlock().getType())) {
                    if ((portal.pos1.getX() + 1D) >= loc.getX() && (portal.pos1.getY()) >= loc.getY() && (portal.pos1.getZ() + 1D) >= loc.getZ()) {
                        if (portal.pos2.getX() <= loc.getX() && portal.pos2.getY() <= loc.getY() && portal.pos2.getZ() <= loc.getZ()) {


                            WarpEvent warpEvent = new WarpEvent(player, portal);
                            plugin.getServer().getPluginManager().callEvent(warpEvent);

                            if (!event.isCancelled()) {
                                boolean warped = Portal.activate(player, portal);
                                if (PortalMessagesDisplay == 1 && warped) {
                                    player.sendMessage("");
                                    player.sendMessage(plugin.customPrefixFail + "\u00A7a You have been warped to \u00A7e" + portal.destiation.replaceAll("_", " ") + "\u00A7.");
                                    player.sendMessage("");
                                } else if (PortalMessagesDisplay == 2 && warped) {
                                    ConfigAccessor config = new ConfigAccessor(plugin, "portals.yml");
                                    plugin.nmsAccess.sendActionBarMessage("{\"text\":\"\u00A7aYou have been warped to \u00A7e" + portal.destiation.replaceAll("_", " ") + "\u00A7a.\"}", player);
                                    /**plugin.nmsAccess.sendActionBarMessage("[{text:\"You have warped to \",color:green},{text:\"" + config.getConfig().getString(portal.portalName + ".destination").replaceAll("_", " ")
                                     + "\",color:yellow},{\"text\":\".\",color:green}]", player);*/
                                }

                                if (warped) {
                                    //event.setFrom(player.getLocation());
                                    //event.setTo(player.getLocation());

                                    //event.setCancelled(true);
                                } else {
                                    player.teleport(fromloc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                                    event.setCancelled(true);
                                }
                            }
                            if (portal.trigger.equals(Material.PORTAL)) {
                                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                                    player.setMetadata("hasWarped", new FixedMetadataValue(plugin, true));
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new RemoveWarpData(player), 10);
                                }
                            } else if (portal.trigger.equals(Material.LAVA)) {
                                player.setMetadata("lavaWarped", new FixedMetadataValue(plugin, true));
                                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new RemoveLavaData(player), 10);
                            }

                        }
                    }

                }
            }
        }

    }

    // These are here because java 7 can only take finals straight into a runnable
    class RemoveLavaData implements Runnable{


        private Player player;

        public RemoveLavaData(Player player){
            this.player = player;
        }

        @Override
        public void run() {
            player.removeMetadata("lavaWarped", plugin);
            player.setFireTicks(0);
        }
    };

    class RemoveWarpData implements Runnable{


        private Player player;

        public RemoveWarpData(Player player){
            this.player = player;
        }

        @Override
        public void run() {
            if (player != null && player.isOnline()) {
                player.removeMetadata("hasWarped", plugin);
            }
        }
    };

    @EventHandler
    public void onCombustEntityEvent(EntityCombustEvent event) {
        if (!Portal.portalsActive) {
            return;
        }
        Location loc = event.getEntity().getLocation();
        for (AdvancedPortal portal : Portal.Portals) {
            if (portal.worldName.equals(loc.getWorld().getName())) {

                if ((portal.pos1.getX() + 3D) >= loc.getX() && (portal.pos1.getY() + 3D) >= loc.getY() && (portal.pos1.getZ() + 3D) >= loc.getZ()) {

                    if ((portal.pos2.getX() - 3D) <= loc.getX() && (portal.pos2.getY() - 3D) <= loc.getY() && (portal.pos2.getZ() - 3D) <= loc.getZ()) {
                        event.setCancelled(true);
                        return;
                    }
                }

            }
        }
    }


    @EventHandler
    public void onDamEvent(EntityDamageEvent event) {
        if (!Portal.portalsActive) {
            return;
        }
        //System.out.println(event.getCause());
        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA || event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            Location loc = event.getEntity().getLocation();
            if (event.getEntity().hasMetadata("lavaWarped")) {
                event.setCancelled(true);
                return;
            }
            for (AdvancedPortal portal : Portal.Portals) {
                if (portal.worldName.equals(loc.getWorld().getName())) {

                    if ((portal.pos1.getX() + 3D) >= loc.getX() && (portal.pos1.getY() + 3D) >= loc.getY() && (portal.pos1.getZ() + 3D) >= loc.getZ()) {

                        if ((portal.pos2.getX() - 3D) <= loc.getX() && (portal.pos2.getY() - 3D) <= loc.getY() && (portal.pos2.getZ() - 3D) <= loc.getZ()) {
                            event.setCancelled(true);
                            return;

                        }
                    }

                }
            }
        }

    }

    // https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/player/PlayerTeleportEvent.TeleportCause.html
    // Also listen to this possibly.
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {

        if (!Portal.portalsActive) {
            return;
        }
        Player player = event.getPlayer();

        if (player.hasMetadata("hasWarped")) {
            event.setCancelled(true);
            return;
        }

        Location loc = player.getLocation();
        Object[] portals = Portal.Portals;
        for (AdvancedPortal portal : Portal.Portals) {
            if (portal.worldName.equals(player.getWorld().getName())) {

                if ((portal.pos1.getX() + 1D) >= loc.getX() && (portal.pos1.getY() + 1D) >= loc.getY() && (portal.pos1.getZ() + 1D) >= loc.getZ()) {

                    if ((portal.pos2.getX()) <= loc.getX() && (portal.pos2.getY()) <= loc.getY() && (portal.pos2.getZ()) <= loc.getZ()) {

                        event.setCancelled(true);

                    }
                }

            }
        }


    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {

        // will detect if the player is using an axe so the points of a portal can be set
        // also any other detections such as sign interaction or basic block protection
        Player player = event.getPlayer();

        if (player.hasMetadata("selectingPortal") && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (!Portal.portalsActive) {
                player.sendMessage("\u00A7a[\u00A77AdvancedPortals\u00A7c] There are no portals that exist to select. Portal selection canceled.");
                event.setCancelled(true);
                player.removeMetadata("selectingPortal", plugin);
                return;
            }
            Block block = event.getClickedBlock();
            for (AdvancedPortal portal : Portal.Portals) {
                if (portal.worldName.equals(block.getWorld().getName())) {

                    if ((portal.pos1.getX()) >= block.getX() && (portal.pos1.getY()) >= block.getY() && (portal.pos1.getZ()) >= block.getZ()) {

                        if ((portal.pos2.getX()) <= block.getX() && (portal.pos2.getY()) <= block.getY() && (portal.pos2.getZ()) <= block.getZ()) {
                            player.sendMessage(plugin.customPrefixFail + "\u00A7a You have selected: \u00A7e" + portal.portalName);
                            player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, portal.portalName)); // adds the name to the metadata of the character
                            event.setCancelled(true);
                            player.removeMetadata("selectingPortal", plugin);
                            return;

                        }
                    }

                }
            }
            player.sendMessage(plugin.customPrefixFail + "\u00A7c No portal was selected. If you would like to stop selecting please type \u00A7e/portal select \u00A7cagain!");
            event.setCancelled(true);
            return;
        }

        if (player.hasPermission("AdvancedPortals.CreatePortal")) {

            // UseOnlyServerMadeAxe being set to true makes is so only the axe generated by the server can be used so other iron axes can be used normally,
            //  by default its false but it is a nice feature in case the user wants to use the axe normally too, such as a admin playing survival or it being used
            //  as a weapon.
            // Null pointer exeption detected here on some servers(try decompiling the jar file to double check)
            /*try {
				// Use this to surround the code if needed
			}
			catch(NullPointerException e){

			}*/
            if (event.getItem() != null && event.getPlayer().getItemInHand().getType() == WandMaterial // was type id
                    && (!UseOnlyServerAxe || (event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().equals("\u00A7ePortal Region Selector")))) {

                // This checks if the action was a left or right click and if it was directly effecting a block.
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    Location blockloc = event.getClickedBlock().getLocation();
                    // stores the selection as metadata on the character so then it isn't saved anywhere, if the player logs out it will
                    //  have to be selected again if the player joins, also it does not affect any other players.
                    player.setMetadata("Pos1X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
                    player.setMetadata("Pos1Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
                    player.setMetadata("Pos1Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
                    player.setMetadata("Pos1World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
                    player.sendMessage("\u00A7eYou have selected pos1! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ() + " World: " + blockloc.getWorld().getName());

                    // Stops the event so the block is not damaged
                    event.setCancelled(true);

                    // Returns the event so no more code is executed(stops unnecessary code being executed)
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Location blockloc = event.getClickedBlock().getLocation();
                    player.setMetadata("Pos2X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
                    player.setMetadata("Pos2Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
                    player.setMetadata("Pos2Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
                    player.setMetadata("Pos2World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
                    player.sendMessage("\u00A7eYou have selected pos2! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ() + " World: " + blockloc.getWorld().getName());

                    // Stops the event so the block is not interacted with
                    event.setCancelled(true);

                    // Returns the event so no more code is executed(stops unnecessary code being executed)
                }

            }

        }

    }


}
