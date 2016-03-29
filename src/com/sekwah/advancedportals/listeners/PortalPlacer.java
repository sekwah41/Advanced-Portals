package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.AdvancedPortal;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PortalPlacer implements Listener {

	@SuppressWarnings("unused")
	private final AdvancedPortalsPlugin plugin;

    private final double PortalProtectionRadius;

    // The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
	private boolean PortalPlace = true;
	
    public PortalPlacer(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.PortalPlace = config.getConfig().getBoolean("CanBuildPortalBlock");

        this.PortalProtectionRadius = config.getConfig().getDouble("PortalProtectionRadius");
        
        if(PortalPlace){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
		Material material = block.getType();
		if (material == Material.PORTAL)
		{
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
			//event.getChangedType();
			//event.setCancelled(true);
		}
	}


}
