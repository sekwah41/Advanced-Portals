package com.sekwah.advancedportals.bukkit.listeners;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class FlowStopper implements Listener {

    private final AdvancedPortalsPlugin plugin;

    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
    private boolean WaterFlow = true;

    public FlowStopper(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.WaterFlow = config.getConfig().getBoolean("StopWaterFlow");

        if (WaterFlow) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockFromTo(BlockFromToEvent event) {
        if (Portal.inPortalRegion(event.getBlock().getLocation(), 3) | Portal.inPortalRegion(event.getToBlock().getLocation(), 3))
            event.setCancelled(true);
    }


}
