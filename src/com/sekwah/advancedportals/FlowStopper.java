package com.sekwah.advancedportals;

import com.sekwah.advancedportals.portals.AdvancedPortal;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class FlowStopper implements Listener {

	@SuppressWarnings("unused")
	private final AdvancedPortalsPlugin plugin;

	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
	private boolean WaterFlow = true;

	public FlowStopper(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;

		ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
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

		if(!Portal.portalsActive){
			return;
		}

		for(AdvancedPortal portal : Portal.Portals){
			if(portal.worldName.equals(block.getWorld().getName())){

				if((portal.pos1.getX() + 3D) >= block.getX() && (portal.pos1.getY() + 3D) >= block.getY() && (portal.pos1.getZ() + 3D) >= block.getZ()){

					if((portal.pos2.getX() - 3D) <= block.getX() && (portal.pos2.getY() - 3D) <= block.getY() && (portal.pos2.getZ() - 3D) <= block.getZ()){
						
						event.setCancelled(true);

					}
				}

			}
			
			if(portal.worldName.equals(blockTo.getWorld().getName())){

				if((portal.pos1.getX() + 3D) >= blockTo.getX() && (portal.pos1.getY() + 3D) >= blockTo.getY() && (portal.pos1.getZ() + 3D) >= blockTo.getZ()){

					if((portal.pos2.getX() - 3D) <= blockTo.getX() && (portal.pos2.getY() - 3D) <= blockTo.getY() && (portal.pos2.getZ() - 3D) <= blockTo.getZ()){
						
						event.setCancelled(true);

					}
				}

			}
		}
	}


}
