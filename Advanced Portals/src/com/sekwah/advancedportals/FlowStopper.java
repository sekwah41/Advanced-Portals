package com.sekwah.advancedportals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

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
		
		Object[] portals = Portal.Portals;
		int portalId = 0;
		for(Object portal : portals){
			if(Portal.worldName[portalId].equals(block.getWorld().getName())){

				if((Portal.pos1[portalId].getX() + 3D) >= block.getX() && (Portal.pos1[portalId].getY() + 3D) >= block.getY() && (Portal.pos1[portalId].getZ() + 3D) >= block.getZ()){

					if((Portal.pos2[portalId].getX() - 3D) <= block.getX() && (Portal.pos2[portalId].getY() - 3D) <= block.getY() && (Portal.pos2[portalId].getZ() - 3D) <= block.getZ()){
						
						event.setCancelled(true);

					}
				}

			}
			
			if(Portal.worldName[portalId].equals(blockTo.getWorld().getName())){

				if((Portal.pos1[portalId].getX() + 3D) >= blockTo.getX() && (Portal.pos1[portalId].getY() + 3D) >= blockTo.getY() && (Portal.pos1[portalId].getZ() + 3D) >= blockTo.getZ()){

					if((Portal.pos2[portalId].getX() - 3D) <= blockTo.getX() && (Portal.pos2[portalId].getY() - 3D) <= blockTo.getY() && (Portal.pos2[portalId].getZ() - 3D) <= blockTo.getZ()){
						
						event.setCancelled(true);

					}
				}

			}
			portalId++;
		}
	}


}
