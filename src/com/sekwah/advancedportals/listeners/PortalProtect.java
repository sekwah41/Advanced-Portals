package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.AdvancedPortal;
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

	private double PortalProtectionRadius = 5D;
	
    public PortalProtect(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.PortalProtect = config.getConfig().getBoolean("PortalProtection");
        
        this.PortalProtectionRadius = config.getConfig().getDouble("PortalProtectionRadius");
        
        if(PortalProtect){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

	@SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event){

		if(!Portal.portalsActive){
			return;
		}

    	if(!event.getPlayer().hasPermission("advancedportals.build")){
    		Block block = event.getBlock();

            for(AdvancedPortal portal : Portal.Portals){
    			if(portal.worldName.equals(block.getWorld().getName())){

    				if((portal.pos1.getX() + PortalProtectionRadius) >= block.getX() && (portal.pos1.getY() + PortalProtectionRadius) >= block.getY() && (portal.pos1.getZ() + PortalProtectionRadius) >= block.getZ()){

    					if((portal.pos2.getX() - PortalProtectionRadius) <= block.getX() && (portal.pos2.getY() - PortalProtectionRadius) <= block.getY() && (portal.pos2.getZ() - PortalProtectionRadius) <= block.getZ()){
    						event.setCancelled(true);
                            break;

    					}
    				}

    			}
    		}
    	}
    }

	@SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event){

		if(!Portal.portalsActive){
			return;
		}
    	
    	if(!event.getPlayer().hasPermission("advancedportals.build")){
    		Block block = event.getBlock();

            for(AdvancedPortal portal : Portal.Portals){
    			if(portal.worldName.equals(block.getWorld().getName())){

    				if((portal.pos1.getX() + PortalProtectionRadius) >= block.getX() && (portal.pos1.getY() + PortalProtectionRadius) >= block.getY() && (portal.pos1.getZ() + PortalProtectionRadius) >= block.getZ()){

    					if((portal.pos2.getX() - PortalProtectionRadius) <= block.getX() && (portal.pos2.getY() - PortalProtectionRadius) <= block.getY() && (portal.pos2.getZ() - PortalProtectionRadius) <= block.getZ()){

    						event.setCancelled(true);
                            break;
    					}
    				}

    			}
    		}
    	}
    }
 	
    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosion(EntityExplodeEvent event){

		if(!Portal.portalsActive){
			return;
		}

    	List<Block> blockList = event.blockList();
    	for (int i = 0; i < blockList.size(); i++) {
    		Block block = blockList.get(i);
    		Object[] portals = Portal.Portals;
            for(AdvancedPortal portal : Portal.Portals){ // change for format for(int i = 0; i < portals.length; i++){
    			if(portal.worldName.equals(block.getWorld().getName())){

    				if((portal.pos1.getX() + PortalProtectionRadius) >= block.getX() && (portal.pos1.getY() + PortalProtectionRadius) >= block.getY() && (portal.pos1.getZ() + PortalProtectionRadius) >= block.getZ()){
    					
    					if((portal.pos2.getX() - PortalProtectionRadius) <= block.getX() && (portal.pos2.getY() - PortalProtectionRadius) <= block.getY() && (portal.pos2.getZ() - PortalProtectionRadius) <= block.getZ()){
    						blockList.remove(i);
    						i--;

    					}
    					
    				}

    			}
    		}
    	}
    }


}
