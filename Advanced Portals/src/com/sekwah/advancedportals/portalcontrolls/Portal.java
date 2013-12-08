package com.sekwah.advancedportals.portalcontrolls;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;

public class Portal {
	
	private static AdvancedPortalsPlugin plugin;
	
	public static Object[] Portals;
	
    public Portal(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        this.loadPortals();
    }
    
	/**
	 * This can be used to move the get keys to different sections
	 * 
	 * ConfigurationSection section = config.getSection("sectionname");
	 * 
	 * section.getKeys(false);
	 * 
	 */
    
    public static void loadPortals(){
    	
    	ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
    	Set<String> PortalSet = config.getConfig().getKeys(false);
    	Portals = PortalSet.toArray();
    	
    }
    
	public static void create(Player player, Material triggerBlockId, Location pos1, Location pos2 , String name) {
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name + ".world", pos1.getWorld().getName());
		config.getConfig().set(name + ".hastriggerblock", true);
		
		config.getConfig().set(name + ".triggerblock", triggerBlockId.getId());
		
		config.getConfig().set(name + ".pos1.X", pos1.getX());
		config.getConfig().set(name + ".pos1.Y", pos1.getY());
		config.getConfig().set(name + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set(name + ".pos2.X", pos2.getX());
		config.getConfig().set(name + ".pos2.Y", pos2.getY());
		config.getConfig().set(name + ".pos2.Z", pos2.getZ());
		
		config.getConfig().set(name + ".creator", player.getName());
		
		config.saveConfig();
	}
	
	public static void create(Player player, Location pos1, Location pos2, String name) { // add stuff for destination names or coordinates
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name + ".world", pos1.getWorld().getName());
		config.getConfig().set(name + ".hastriggerblock", false);
		
		config.getConfig().set(name + ".pos1.X", pos1.getX());
		config.getConfig().set(name + ".pos1.Y", pos1.getY());
		config.getConfig().set(name + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set(name + ".pos2.X", pos2.getX());
		config.getConfig().set(name + ".pos2.Y", pos2.getY());
		config.getConfig().set(name + ".pos2.Z", pos2.getZ());
		
		config.getConfig().set(name + ".creator", player.getName());
	}
	
	public static void redefine(Player player, Location pos1, Location pos2, String name){
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name + ".pos1.X", pos1.getX());
		config.getConfig().set(name + ".pos1.Y", pos1.getY());
		config.getConfig().set(name + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set(name + ".pos2.X", pos2.getX());
		config.getConfig().set(name + ".pos2.Y", pos2.getY());
		config.getConfig().set(name + ".pos2.Z", pos2.getZ());
		
		config.saveConfig();
		
	}
	
	public static void remove(Player player, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name + ".world", null);
		config.getConfig().set(name + ".hastriggerblock", null);
		
		config.getConfig().set(name + ".pos1.X", null);
		config.getConfig().set(name + ".pos1.Y", null);
		config.getConfig().set(name + ".pos1.Z", null);
		
		config.getConfig().set(name + ".pos2.X", null);
		config.getConfig().set(name + ".pos2.Y", null);
		config.getConfig().set(name + ".pos2.Z", null);
		
		config.getConfig().set(name + ".creator", null);
		
		config.saveConfig();
	}
	
	

}
