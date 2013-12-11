package com.sekwah.advancedportals;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.sekwah.advancedportals.portalcontrolls.Portal;

public class FlowStopper implements Listener {

	private final AdvancedPortalsPlugin plugin;

	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("§eP...
	private static boolean WaterFlow = true;
	
    public FlowStopper(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        this.WaterFlow = config.getConfig().getBoolean("StopWaterFlow");
        
        if(WaterFlow){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
        else{
        	plugin.getLogger().log(Level.INFO, "Water flow is currently allowed!");
        }
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPhysics(BlockPhysicsEvent event) {
		Material material = event.getBlock().getType();
		if (material == Material.STATIONARY_WATER)
		{
			event.setCancelled(true);
		}
		else if (material == Material.STATIONARY_LAVA){
			
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent event) {
		Block block = event.getToBlock();
		if (block.getType() == Material.WATER) {
			event.setCancelled(true);
		}
		else if (block.getType() == Material.LAVA) {
			event.setCancelled(true);
		}
	}


}
