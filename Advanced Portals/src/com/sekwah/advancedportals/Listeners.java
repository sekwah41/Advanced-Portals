package com.sekwah.advancedportals;

import net.minecraft.server.v1_6_R3.Item;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Listeners implements Listener {
	
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
    	// will check if the player is in the portal or not.
    	
    }
    
    
    
    
    @EventHandler
    public void oniteminteract(PlayerInteractEvent event) {
    	// will detect if the player is using an axe so the points of a portal can be set
    	// also any other detections such as sign interaction or basic block protection
    	if(event.getPlayer().getItemInHand().getTypeId() == Material.IRON_AXE.getId()) {
    		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
    			
    		}
    		else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    			
    		}
    	}
    }
    
    
	
}
