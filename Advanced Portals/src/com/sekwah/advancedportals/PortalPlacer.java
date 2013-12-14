package com.sekwah.advancedportals;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PortalPlacer implements Listener {

	@SuppressWarnings("unused")
	private final AdvancedPortalsPlugin plugin;

	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("§eP...
	private boolean PortalPlace = true;
	
    public PortalPlacer(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        this.PortalPlace = config.getConfig().getBoolean("CanBuildPortalBlock");
        
        if(PortalPlace){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPhysics(BlockPhysicsEvent event) {
		Material material = event.getBlock().getType();
		if (material == Material.PORTAL)
		{
			event.getChangedType();
			event.setCancelled(true);
		}
	}


}
