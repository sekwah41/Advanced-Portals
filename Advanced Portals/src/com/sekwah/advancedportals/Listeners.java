package com.sekwah.advancedportals;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Listeners implements Listener {
	
	private final AdvancedPortalsPlugin plugin;
	
    public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
    	// will check if the player is in the portal or not.
    	ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
    	
    }
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void oniteminteract(PlayerInteractEvent event) {
    	// will detect if the player is using an axe so the points of a portal can be set
    	// also any other detections such as sign interaction or basic block protection
    	Player player = event.getPlayer();
    	Location blockloc = event.getClickedBlock().getLocation();
    	if(event.getPlayer().getItemInHand().getTypeId() == Material.IRON_AXE.getId()) {
    		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
    			// stores the selection as metadata on the character so then it isn't saved anywhere, if the player logs out it will
    			//  have to be selected again if the player joins, also it does not affect any other players.
    			player.setMetadata("Pos1X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
    			player.setMetadata("Pos1Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
    			player.setMetadata("Pos1Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
    			player.sendMessage("§eYou have selected pos1! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ());
    			
    			// Stops the event so the block is not damaged
    			event.setCancelled(true);
    			
    			// Returns the event so no more code is executed(stops unnecessary code being executed)
    			return;
    		}
    		else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    			player.setMetadata("Pos2X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
    			player.setMetadata("Pos2Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
    			player.setMetadata("Pos2Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
    			player.sendMessage("§eYou have selected pos2! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ());
    			
    			// Stops the event so the block is not interacted with
    			event.setCancelled(true);
    			
    			// Returns the event so no more code is executed(stops unnecessary code being executed)
    			return;
    		}
    	}
    }
    
    
	
}
