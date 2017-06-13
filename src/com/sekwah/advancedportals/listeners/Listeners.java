package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.PluginMessages;
import com.sekwah.advancedportals.api.events.WarpEvent;
import com.sekwah.advancedportals.portals.AdvancedPortal;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Listeners implements Listener {
    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("\u00A7eP...
    private static boolean UseOnlyServerAxe = false;
    private static Material WandMaterial;

    private final AdvancedPortalsPlugin plugin;

    @SuppressWarnings("deprecation")
    public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");

        String ItemID = config.getConfig().getString("AxeItemId");

        if(ItemID == null){
            WandMaterial = Material.IRON_AXE;
        }
        else{
            try {
                WandMaterial = Material.getMaterial(Integer.parseInt(ItemID));
            } catch (Exception e) {
                WandMaterial = Material.getMaterial(ItemID);
            }
        }


        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("deprecation")
    public static void reloadValues(AdvancedPortalsPlugin plugin) {

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");

        String ItemID = config.getConfig().getString("AxeItemId");

        try {
            WandMaterial = Material.getMaterial(Integer.parseInt(ItemID));
        } catch (Exception e) {
            WandMaterial = Material.getMaterial(ItemID);
        }
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Portal.cooldown.put(event.getPlayer(), System.currentTimeMillis());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMoveEvent(PlayerMoveEvent event) {
        // will check if the player is in the portal or not.
        if (!Portal.portalsActive) {
            return;
        }

        Player player = event.getPlayer();
        //Location fromloc = event.getFrom();
        Location loc = event.getTo();
        Location eyeLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + player.getEyeHeight(), loc.getZ());
        for (AdvancedPortal portal : Portal.portals) {
            if (Portal.locationInPortalTrigger(portal, loc) || Portal.locationInPortalTrigger(portal, eyeLoc)) {
                if (portal.trigger.equals(Material.PORTAL)) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.setMetadata("hasWarped", new FixedMetadataValue(plugin, true));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new RemoveWarpData(player), 10);
                    }
                } else if (portal.trigger.equals(Material.LAVA)) {
                    player.setMetadata("lavaWarped", new FixedMetadataValue(plugin, true));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new RemoveLavaData(player), 10);
                }
                if (portal.inPortal.contains(player)) return;
                WarpEvent warpEvent = new WarpEvent(player, portal);
                plugin.getServer().getPluginManager().callEvent(warpEvent);
                if (!event.isCancelled()) Portal.activate(player, portal);
                portal.inPortal.add(player);
            } else portal.inPortal.remove(player);
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
        if (Portal.inPortalTriggerRegion(event.getEntity().getLocation()))
            event.setCancelled(true);
    }


    @EventHandler
    public void onDamEvent(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA || event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            if (event.getEntity().hasMetadata("lavaWarped") | Portal.inPortalTriggerRegion(event.getEntity().getLocation()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("hasWarped") | Portal.inPortalRegion(event.getFrom(),1))
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {

        // will detect if the player is using an axe so the points of a portal can be set
        // also any other detections such as sign interaction or basic block protection
        Player player = event.getPlayer();

        if (player.hasMetadata("selectingPortal") && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            for (AdvancedPortal portal : Portal.portals) {
                if (Portal.locationInPortal(portal, event.getClickedBlock().getLocation(), 0)) {
                    player.sendMessage(PluginMessages.customPrefix + "\u00A7a You have selected: \u00A7e" + portal.portalName);
                    player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, portal.portalName)); // adds the name to the metadata of the character
                    event.setCancelled(true);
                    player.removeMetadata("selectingPortal", plugin);
                    return;
                }
            }
            player.sendMessage(PluginMessages.customPrefixFail + "\u00A7c No portal was selected. If you would like to stop selecting please type \u00A7e/portal select \u00A7cagain!");
            event.setCancelled(true);
            return;
        }

        if (player.hasPermission("advancedportals.createportal")) {

            // UseOnlyServerMadeAxe being set to true makes is so only the axe generated by the server can be used so other iron axes can be used normally,
            //  by default its false but it is a nice feature in case the user wants to use the axe normally too, such as a admin playing survival or it being used
            //  as a weapon.
            // Null pointer exeption detected here on some servers(try decompiling the jar file to double check)
            /*try {
				// Use this to surround the code if needed
			}
			catch(NullPointerException e){

			}*/
            if (event.getItem() != null && event.getItem().getType() == WandMaterial // was type id
                    && (!UseOnlyServerAxe || (checkItemForName(event.getItem()) && event.getItem().getItemMeta().getDisplayName().equals("\u00A7ePortal Region Selector")))) {

                // This checks if the action was a left or right click and if it was directly effecting a block.
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    Location blockloc = event.getClickedBlock().getLocation();
                    // stores the selection as metadata on the character so then it isn't saved anywhere, if the player logs out it will
                    //  have to be selected again if the player joins, also it does not affect any other players.
                    player.setMetadata("Pos1X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
                    player.setMetadata("Pos1Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
                    player.setMetadata("Pos1Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
                    player.setMetadata("Pos1World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
                    player.sendMessage("\u00A7eYou have selected pos1! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY()
                            + " Z:" + blockloc.getBlockZ() + " World: " + blockloc.getWorld().getName());

                    // Stops the event so the block is not damaged
                    event.setCancelled(true);

                    // Returns the event so no more code is executed(stops unnecessary code being executed)
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    System.out.println(event.getHand());
                    Location blockloc = event.getClickedBlock().getLocation();
                    player.setMetadata("Pos2X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
                    player.setMetadata("Pos2Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
                    player.setMetadata("Pos2Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
                    player.setMetadata("Pos2World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
                    player.sendMessage("\u00A7eYou have selected pos2! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY()
                            + " Z:" + blockloc.getBlockZ() + " World: " + blockloc.getWorld().getName());

                    // Stops the event so the block is not interacted with
                    event.setCancelled(true);

                    // Returns the event so no more code is executed(stops unnecessary code being executed)
                }

            } else if (checkItemForName(event.getItem()) && event.getItem().getItemMeta().getDisplayName().equals("\u00A75Portal Block Placer") &&
                    event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.PORTAL) {
                Block block = event.getClickedBlock();
                if (block.getData() == 1) {
                    block.setData((byte) 2);
                } else {
                    block.setData((byte) 1);
                }
                event.setCancelled(true);
            }
        }

    }

    private boolean checkItemForName(ItemStack item){
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName();
    }


}
