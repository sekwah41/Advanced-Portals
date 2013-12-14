package com.sekwah.advancedportals.portalcontrolls;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;

public class Portal {
	
	private static AdvancedPortalsPlugin plugin;
	
	public static Object[] Portals;
	
	public static Object[] triggers;
	
	public static Object[] pos1;
	
	public static Object[] pos2;
	
    public Portal(AdvancedPortalsPlugin plugin) {
        Portal.plugin = plugin;
        Portal.loadPortals();
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
    	int portalId = 0;
    	for(Object portal: Portals){
    		portal.toString();
    		triggers[portalId] = config.getConfig().getString(portal.toString() + ".triggerblock");
    		// pos1[portalId] = config.getConfig().getString(portal.toString() + ".triggerblock"); // will be a location
    		// pos2[portalId] = config.getConfig().getString(portal.toString() + ".triggerblock"); // will be a location
    		portalId++;
    	}
    	
    }
    
	public static void create(Location pos1, Location pos2 , String name, String destination , Material triggerBlockId) {
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", pos1.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".triggerblock", triggerBlockId.toString());
		
		config.getConfig().set(name.toLowerCase() + ".destination", destination);
		
		config.getConfig().set(name.toLowerCase() + ".pos1.X", pos1.getX());
		config.getConfig().set(name.toLowerCase() + ".pos1.Y", pos1.getY());
		config.getConfig().set(name.toLowerCase() + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set(name.toLowerCase() + ".pos2.X", pos2.getX());
		config.getConfig().set(name.toLowerCase() + ".pos2.Y", pos2.getY());
		config.getConfig().set(name.toLowerCase() + ".pos2.Z", pos2.getZ());
		
		config.saveConfig();
		
		loadPortals();
	}
	
	@SuppressWarnings("deprecation")
	public static void create(Location pos1, Location pos2, String name, String destination) { // add stuff for destination names or coordinates
		ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
		
		Material triggerBlockType;
		String BlockID = config.getConfig().getString("DefaultPortalTriggerBlock");
		try
		{
			triggerBlockType = Material.getMaterial(Integer.parseInt(BlockID));
		}
		catch(Exception e)
		{
			triggerBlockType = Material.getMaterial(BlockID);
		}
		
		if(triggerBlockType == null){
			triggerBlockType = Material.PORTAL;
		}
		
		create(pos1, pos2, name, destination, triggerBlockType);
		
		loadPortals();
	}
	
	public static void redefine(Location pos1, Location pos2, String name){
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name.toLowerCase() + ".pos1.X", pos1.getX());
		config.getConfig().set(name.toLowerCase() + ".pos1.Y", pos1.getY());
		config.getConfig().set(name.toLowerCase() + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set(name.toLowerCase() + ".pos2.X", pos2.getX());
		config.getConfig().set(name.toLowerCase() + ".pos2.Y", pos2.getY());
		config.getConfig().set(name.toLowerCase() + ".pos2.Z", pos2.getZ());
		
		config.saveConfig();
		
		loadPortals();
		
	}
	
	public static void remove(String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", null);
		config.getConfig().set(name.toLowerCase() + ".hastriggerblock", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos1.X", null);
		config.getConfig().set(name.toLowerCase() + ".pos1.Y", null);
		config.getConfig().set(name.toLowerCase() + ".pos1.Z", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos2.X", null);
		config.getConfig().set(name.toLowerCase() + ".pos2.Y", null);
		config.getConfig().set(name.toLowerCase() + ".pos2.Z", null);
		
		config.saveConfig();
		
		loadPortals();
	}
	
	

}
