package com.sekwah.advancedportals;

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
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        this.PortalProtect = config.getConfig().getBoolean("PortalProtection");
        
        this.PortalProtectionRadius = config.getConfig().getDouble("PortalProtectionRadius");
        
        if(PortalProtect){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

	@SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event){
    	
    	if(!event.getPlayer().hasPermission("advancedportals.build")){
    		Block block = event.getBlock();

    		Object[] portals = Portal.Portals;
    		int portalId = 0;
    		for(Object portal : portals){
    			if(Portal.Portals[portalId].worldName.equals(block.getWorld().getName())){

    				if((Portal.Portals[portalId].pos1.getX() + PortalProtectionRadius) >= block.getX() && (Portal.Portals[portalId].pos1.getY() + PortalProtectionRadius) >= block.getY() && (Portal.Portals[portalId].pos1.getZ() + PortalProtectionRadius) >= block.getZ()){

    					if((Portal.Portals[portalId].pos2.getX() - PortalProtectionRadius) <= block.getX() && (Portal.Portals[portalId].pos2.getY() - PortalProtectionRadius) <= block.getY() && (Portal.Portals[portalId].pos2.getZ() - PortalProtectionRadius) <= block.getZ()){

    						event.setCancelled(true);

    					}
    				}

    			}
    		}
    	}
    }

	@SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event){
    	
    	if(!event.getPlayer().hasPermission("advancedportals.build")){
    		Block block = event.getBlock();

    		Object[] portals = Portal.Portals;
    		for(int portalId = 0; portalId < portals.length; portalId++){
    			if(Portal.Portals[portalId].worldName.equals(block.getWorld().getName())){

    				if((Portal.Portals[portalId].pos1.getX() + PortalProtectionRadius) >= block.getX() && (Portal.Portals[portalId].pos1.getY() + PortalProtectionRadius) >= block.getY() && (Portal.Portals[portalId].pos1.getZ() + PortalProtectionRadius) >= block.getZ()){

    					if((Portal.Portals[portalId].pos2.getX() - PortalProtectionRadius) <= block.getX() && (Portal.Portals[portalId].pos2.getY() - PortalProtectionRadius) <= block.getY() && (Portal.Portals[portalId].pos2.getZ() - PortalProtectionRadius) <= block.getZ()){

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
    			if(Portal.Portals[portalId].worldName.equals(block.getWorld().getName())){

    				if((Portal.Portals[portalId].pos1.getX() + PortalProtectionRadius) >= block.getX() && (Portal.Portals[portalId].pos1.getY() + PortalProtectionRadius) >= block.getY() && (Portal.Portals[portalId].pos1.getZ() + PortalProtectionRadius) >= block.getZ()){
    					
    					if((Portal.Portals[portalId].pos2.getX() - PortalProtectionRadius) <= block.getX() && (Portal.Portals[portalId].pos2.getY() - PortalProtectionRadius) <= block.getY() && (Portal.Portals[portalId].pos2.getZ() - PortalProtectionRadius) <= block.getZ()){
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
