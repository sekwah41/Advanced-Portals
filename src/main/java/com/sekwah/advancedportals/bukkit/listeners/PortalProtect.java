package com.sekwah.advancedportals.bukkit.listeners;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.PluginMessages;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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

    private int PortalProtectionArea = 5;

    public PortalProtect(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.PortalProtect = config.getConfig().getBoolean("PortalProtection");

        this.PortalProtectionArea = config.getConfig().getInt("PortalProtectionArea", 5);

        if (PortalProtect) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if(!this.PortalProtect) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("advancedportals.build")
                && Portal.inPortalRegion(event.getBlock().getLocation(), PortalProtectionArea)) {
            event.setCancelled(true);
            player.sendMessage(PluginMessages.customPrefixFail + " You don't have permission to build here!");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!this.PortalProtect) return;

        Player player = event.getPlayer();
        if (PortalProtect && !player.hasPermission("advancedportals.build")
                && Portal.inPortalRegion(event.getBlock().getLocation(), PortalProtectionArea)) {
            event.setCancelled(true);
            player.sendMessage(PluginMessages.customPrefixFail + " You don't have permission to build here!");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosion(EntityExplodeEvent event) {
        if(!this.PortalProtect) return;

        List<Block> blockList = event.blockList();
        for (int i = 0; i < blockList.size(); i++) {
            Block block = blockList.get(i);
            if (Portal.inPortalRegion(block.getLocation(), PortalProtectionArea)) {
                blockList.remove(i);
                i--;
            }
        }
    }


}
