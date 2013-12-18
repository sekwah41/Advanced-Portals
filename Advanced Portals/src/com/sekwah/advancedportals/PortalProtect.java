package com.sekwah.advancedportals;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.sekwah.advancedportals.portalcontrolls.Portal;

public class PortalProtect implements Listener {
	
	private final AdvancedPortalsPlugin plugin;

	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("§eP...
	private boolean PortalProtect = true;

	private double PortalProtectionRadius = 5D;
	
    public PortalProtect(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        this.PortalProtect = config.getConfig().getBoolean("PortalProtection");
        
        this.PortalProtectionRadius = config.getConfig().getDouble("PortalProtectionRadius");
        
        if(PortalProtect){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event){
    	
    	if(!event.getPlayer().hasPermission("advancedportals.build")){
    		Block block = event.getBlock();

    		Object[] portals = Portal.Portals;
    		int portalId = 0;
    		for(Object portal : portals){
    			if(Portal.worldName[portalId].equals(block.getWorld().getName())){

    				if((Portal.pos1[portalId].getX() + PortalProtectionRadius) >= block.getX() && (Portal.pos1[portalId].getY() + PortalProtectionRadius) >= block.getY() && (Portal.pos1[portalId].getZ() + PortalProtectionRadius) >= block.getZ()){

    					if((Portal.pos2[portalId].getX() - PortalProtectionRadius) <= block.getX() && (Portal.pos2[portalId].getY() - PortalProtectionRadius) <= block.getY() && (Portal.pos2[portalId].getZ() - PortalProtectionRadius) <= block.getZ()){

    						event.setCancelled(true);

    					}
    				}

    			}
    		}
    	}
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event){
    	
    	if(!event.getPlayer().hasPermission("advancedportals.build")){
    		Block block = event.getBlock();

    		Object[] portals = Portal.Portals;
    		int portalId = 0;
    		for(Object portal : portals){
    			if(Portal.worldName[portalId].equals(block.getWorld().getName())){

    				if((Portal.pos1[portalId].getX() + PortalProtectionRadius) >= block.getX() && (Portal.pos1[portalId].getY() + PortalProtectionRadius) >= block.getY() && (Portal.pos1[portalId].getZ() + PortalProtectionRadius) >= block.getZ()){

    					if((Portal.pos2[portalId].getX() - PortalProtectionRadius) <= block.getX() && (Portal.pos2[portalId].getY() - PortalProtectionRadius) <= block.getY() && (Portal.pos2[portalId].getZ() - PortalProtectionRadius) <= block.getZ()){

    						event.setCancelled(true);

    					}
    				}

    			}
    		}
    	}
    }
 	
    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosion(EntityExplodeEvent event){
    	List<Block> blockList = event.blockList();
    	for (int i = 0; i < blockList.size(); i++) {
    		Block block = blockList.get(i);
    		Object[] portals = Portal.Portals;
    		int portalId = 0;
    		for(Object portal : portals){ // change for format for(int i = 0; i < portals.length; i++){
    			if(Portal.worldName[portalId].equals(block.getWorld().getName())){

    				if((Portal.pos1[portalId].getX() + PortalProtectionRadius) >= block.getX() && (Portal.pos1[portalId].getY() + PortalProtectionRadius) >= block.getY() && (Portal.pos1[portalId].getZ() + PortalProtectionRadius) >= block.getZ()){
    					
    					if((Portal.pos2[portalId].getX() - PortalProtectionRadius) <= block.getX() && (Portal.pos2[portalId].getY() - PortalProtectionRadius) <= block.getY() && (Portal.pos2[portalId].getZ() - PortalProtectionRadius) <= block.getZ()){
    						blockList.remove(i);
    						i--;

    					}
    					
    				}

    			}
    			portalId++;
    		}
    	}
    }


}
