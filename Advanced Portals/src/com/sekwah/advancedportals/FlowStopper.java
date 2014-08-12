package com.sekwah.advancedportals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.sekwah.advancedportals.portalcontrolls.AdvancedPortal;
import com.sekwah.advancedportals.portalcontrolls.Portal;

public class FlowStopper implements Listener {

	@SuppressWarnings("unused")
	private final AdvancedPortalsPlugin plugin;

	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("§eP...
	private boolean WaterFlow = true;

	public FlowStopper(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;

		ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
		this.WaterFlow = config.getConfig().getBoolean("StopWaterFlow");

		if(WaterFlow){
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockFromTo(BlockFromToEvent event) {
		// when checking positions check the block and the to block
		Block blockTo = event.getToBlock();
		Block block = event.getBlock();
		
		AdvancedPortal[] portals = Portal.Portals;
		int portalId = 0;
		for(Object portal : portals){
			if(Portal.Portals[portalId].worldName.equals(block.getWorld().getName())){

				if((Portal.Portals[portalId].pos1.getX() + 3D) >= block.getX() && (Portal.Portals[portalId].pos1.getY() + 3D) >= block.getY() && (Portal.Portals[portalId].pos1.getZ() + 3D) >= block.getZ()){

					if((Portal.Portals[portalId].pos2.getX() - 3D) <= block.getX() && (Portal.Portals[portalId].pos2.getY() - 3D) <= block.getY() && (Portal.Portals[portalId].pos2.getZ() - 3D) <= block.getZ()){
						
						event.setCancelled(true);

					}
				}

			}
			
			if(Portal.Portals[portalId].worldName.equals(blockTo.getWorld().getName())){

				if((Portal.Portals[portalId].pos1.getX() + 3D) >= blockTo.getX() && (Portal.Portals[portalId].pos1.getY() + 3D) >= blockTo.getY() && (Portal.Portals[portalId].pos1.getZ() + 3D) >= blockTo.getZ()){

					if((Portal.Portals[portalId].pos2.getX() - 3D) <= blockTo.getX() && (Portal.Portals[portalId].pos2.getY() - 3D) <= blockTo.getY() && (Portal.Portals[portalId].pos2.getZ() - 3D) <= blockTo.getZ()){
						
						event.setCancelled(true);

					}
				}

			}
			portalId++;
		}
	}


}
