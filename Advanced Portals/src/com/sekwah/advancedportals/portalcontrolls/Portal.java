package com.sekwah.advancedportals.portalcontrolls;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.DataCollector.DataCollector;
import com.sekwah.advancedportals.destinations.Destination;

public class Portal {
	
	private static AdvancedPortalsPlugin plugin;
	
	public static boolean portalsActive = true;
	
	public static Object[] Portals;
	
	public static Material[] triggers;
	
	public static String[] worldName;
	
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
    	if(PortalSet.size() > 0){
        	Portals = PortalSet.toArray();
        	
        	// allocates the memory for the arrays
        	worldName = new String[Portals.length];
        	triggers = new Material[Portals.length];
        	pos1 = new Location[Portals.length];
        	pos2 = new Location[Portals.length];
        	
        	int portalId = 0;
        	for(Object portal: Portals){
        		
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
        		
        		worldName[portalId] = config.getConfig().getString(portal.toString() + ".world");
        		World world = Bukkit.getWorld(config.getConfig().getString(portal.toString() + ".world"));
    			pos1[portalId] = new Location(world, config.getConfig().getInt(portal.toString() + ".pos1.X"), config.getConfig().getInt(portal.toString() + ".pos1.Y"), config.getConfig().getInt(portal.toString() + ".pos1.Z"));
    			pos2[portalId] = new Location(world, config.getConfig().getInt(portal.toString() + ".pos2.X"), config.getConfig().getInt(portal.toString() + ".pos2.Y"), config.getConfig().getInt(portal.toString() + ".pos2.Z"));
        		
    			portalId++;
        	}
        	portalsActive = true;
    	}
    	else{
    		portalsActive = false;
    	}
    	
    }
    
	public static void create(Location pos1, Location pos2 , String name, String destination , Material triggerBlockId) {
		
		
		int LowX = 0;
		int LowY = 0;		
		int LowZ = 0;
		
		int HighX = 0;
		int HighY = 0;
		int HighZ = 0;
		
		if(pos1.getX() > pos2.getX()){
			LowX = (int) pos2.getX();
			HighX = (int) pos1.getX();
		}
		else{
			LowX = (int) pos1.getX();
			HighX = (int) pos2.getX();
		}
		if(pos1.getY() > pos2.getY()){
			LowY = (int) pos2.getY();
			HighY = (int) pos1.getY();
		}
		else{
			LowY = (int) pos1.getY();
			HighY = (int) pos2.getY();
		}
		if(pos1.getZ() > pos2.getZ()){
			LowZ = (int) pos2.getZ();
			HighZ = (int) pos1.getZ();
		}
		else{
			LowZ = (int) pos1.getZ();
			HighZ = (int) pos2.getZ();
		}
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", pos1.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".triggerblock", triggerBlockId.toString());
		
		config.getConfig().set(name.toLowerCase() + ".destination", destination);
		
		config.getConfig().set(name.toLowerCase() + ".pos1.X", HighX);
		config.getConfig().set(name.toLowerCase() + ".pos1.Y", HighY);
		config.getConfig().set(name.toLowerCase() + ".pos1.Z", HighZ);
		
		config.getConfig().set(name.toLowerCase() + ".pos2.X", LowX);
		config.getConfig().set(name.toLowerCase() + ".pos2.Y", LowY);
		config.getConfig().set(name.toLowerCase() + ".pos2.Z", LowZ);
		
		config.saveConfig();
		
		DataCollector.portalCreated(triggerBlockId.toString());
		
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
		config.getConfig().set(name.toLowerCase() + ".triggerblock", null);
		config.getConfig().set(name.toLowerCase() + ".destination", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos1.X", null);
		config.getConfig().set(name.toLowerCase() + ".pos1.Y", null);
		config.getConfig().set(name.toLowerCase() + ".pos1.Z", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos2.X", null);
		config.getConfig().set(name.toLowerCase() + ".pos2.Y", null);
		config.getConfig().set(name.toLowerCase() + ".pos2.Z", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos1", null);
		config.getConfig().set(name.toLowerCase() + ".pos2", null);
		
		config.getConfig().set(name.toLowerCase(), null);
		
		config.saveConfig();
		
		loadPortals();
	}

	public static void activate(Player player, String portalName) {
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");

		if(config.getConfig().getString(portalName + ".destination") != null){
			ConfigAccessor configDesti = new ConfigAccessor(plugin, "Destinations.yml");
			String destiName = config.getConfig().getString(portalName + ".destination");
			if(configDesti.getConfig().getString(destiName  + ".world") != null){
				Destination.warp(player, destiName);
			}
			else{
				player.sendMessage("§cThe destination you are attempting to warp to doesnt exist!");
				plugin.getLogger().log(Level.SEVERE, "The portal '" + portalName + "' has just had a warp "
						+ "attempt and either the data is corrupt or that destination listed doesn't exist!");
			}
		}
		else{
			player.sendMessage("§cThe destination you are attempting to warp to doesnt exist!");
			plugin.getLogger().log(Level.SEVERE, "The portal '" + portalName + "' has just had a warp "
					+ "attempt and either the data is corrupt or portal doesn't exist!");
		}
		
		// add code for if the portal doesnt have a destination but a teleport location
		
		
	}
	
	

}
