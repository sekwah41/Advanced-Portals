package com.sekwah.advancedportals.portalcontrolls;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;

public class Portal {
	
	private static AdvancedPortalsPlugin plugin;
	
	public static String[] Portals;
	
	public static Material[] triggers;
	
	public static World[] world;
	
	public static Location[] pos1;
	
	public static Location[] pos2;
	
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
    	Portals = (String[]) PortalSet.toArray();
    	int portalId = 0;
    	for(String portal: Portals){
    		portal.toString();
    		Material blockType;
    		String BlockID = config.getConfig().getString(portal.toString() + ".triggerblock");
    		try
    		{
    			blockType = Material.getMaterial(Integer.parseInt(BlockID));
    		}
    		catch(Exception e)
    		{
    			blockType = Material.getMaterial(BlockID);
    		}
    		triggers[portalId] = blockType;
    		
    		world[portalId] = org.bukkit.Bukkit.getWorld(config.getConfig().getString(portal.toString() + ".world"));
			pos1[portalId] = new Location(world[portalId], config.getConfig().getInt(portal.toString() + ".pos1.X"), config.getConfig().getInt(portal.toString() + ".pos1.Y"), config.getConfig().getInt(portal.toString() + ".pos1.Z"));
			pos2[portalId] = new Location(world[portalId], config.getConfig().getInt(portal.toString() + ".pos2.X"), config.getConfig().getInt(portal.toString() + ".pos2.Y"), config.getConfig().getInt(portal.toString() + ".pos2.Z"));
    		
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
