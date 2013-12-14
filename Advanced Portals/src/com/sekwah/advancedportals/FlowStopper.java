package com.sekwah.advancedportals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent event) {
		// when checking positions check the block and the to block
		Block blockTo = event.getToBlock();
		Block block = event.getBlock();
		if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER
				|| blockTo.getType() == Material.WATER || blockTo.getType() == Material.STATIONARY_WATER) {

			event.setCancelled(true);

		}
		else if (block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA
				|| blockTo.getType() == Material.LAVA || blockTo.getType() == Material.STATIONARY_LAVA) {

			event.setCancelled(true);

		}
	}


}
