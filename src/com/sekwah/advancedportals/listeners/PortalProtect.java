package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class PortalProtect implements Listener {

    @SuppressWarnings("unused")
    private final AdvancedPortalsPlugin plugin;

    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
    private boolean PortalProtect = true;

    private int PortalProtectionRadius = 5;

    public PortalProtect(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.PortalProtect = config.getConfig().getBoolean("PortalProtection");

        this.PortalProtectionRadius = config.getConfig().getInt("PortalProtectionRadius");

        if (PortalProtect) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("advancedportals.build")
                && Portal.inPortalRegion(event.getBlock().getLocation(), PortalProtectionRadius))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("advancedportals.build")
                && Portal.inPortalRegion(event.getBlock().getLocation(), PortalProtectionRadius))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosion(EntityExplodeEvent event) {

        List<Block> blockList = event.blockList();
        for (int i = 0; i < blockList.size(); i++) {
            Block block = blockList.get(i);
            if (Portal.inPortalRegion(block.getLocation(), PortalProtectionRadius)) {
                blockList.remove(i);
                i--;
            }
        }
    }


}
