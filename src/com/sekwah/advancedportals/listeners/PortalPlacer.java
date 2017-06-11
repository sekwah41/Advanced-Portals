package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PortalPlacer implements Listener {

    @SuppressWarnings("unused")
    private final AdvancedPortalsPlugin plugin;

    private final int PortalProtectionRadius;

    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
    private boolean PortalPlace = true;

    public PortalPlacer(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.PortalPlace = config.getConfig().getBoolean("CanBuildPortalBlock");

        this.PortalProtectionRadius = config.getConfig().getInt("PortalProtectionRadius");

        if (PortalPlace) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().hasPermission("advancedportals.build") && event.getItemInHand().getItemMeta().getDisplayName().equals("\u00A75Portal Block Placer")){
            event.getBlockPlaced().setType(Material.PORTAL);
        }
        else if (event.getPlayer().hasPermission("advancedportals.build") && event.getItemInHand().getItemMeta().getDisplayName().equals("\u00A78End Portal Block Placer")){
            event.getBlockPlaced().setType(Material.ENDER_PORTAL);
        }
        else if (event.getPlayer().hasPermission("advancedportals.build") && event.getItemInHand().getItemMeta().getDisplayName().equals("\u00A78Gateway Block Placer")){
            event.getBlockPlaced().setType(Material.END_GATEWAY);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        //Material material = block.getType();
        if (event.getChangedType() == Material.PORTAL/*(material == Material.PORTAL)*/ && Portal.inPortalRegion(block.getLocation(), PortalProtectionRadius))
            event.setCancelled(true);
    }
}
