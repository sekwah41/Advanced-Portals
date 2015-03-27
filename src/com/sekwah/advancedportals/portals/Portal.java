package com.sekwah.advancedportals.portals;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.destinations.Destination;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.logging.Level;

public class Portal {
	
	private static AdvancedPortalsPlugin plugin;
	
	public static boolean portalsActive = true;
	
	public static AdvancedPortal[] Portals;

	private static boolean ShowBungeeMessage;
	
    public Portal(AdvancedPortalsPlugin plugin) {
    	ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
    	ShowBungeeMessage = config.getConfig().getBoolean("ShowBungeeWarpMessage");
    	
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
    		Portals = new AdvancedPortal[PortalSet.toArray().length];
        	
    		for(int i = 0; i <= PortalSet.toArray().length - 1; i++){
    			Portals[i] = new AdvancedPortal();
    		}
    		
        	int portalId = 0;
        	for(Object portal: PortalSet.toArray()){
        		
        		Material blockType = Material.PORTAL;
        		String BlockID = config.getConfig().getString(portal.toString() + ".triggerblock");
        		try
        		{
        			Integer.parseInt(BlockID);
        			System.out.println("Block names must be given not IDs");
        		}
        		catch(NumberFormatException e)
        		{
        			blockType = Material.getMaterial(BlockID);
        		}
        		
        		if(blockType == null){
        			blockType = Material.PORTAL;
        		}
        		
        		Portals[portalId].trigger = blockType;
        		System.out.println("Test: " + portal.toString());
        		Portals[portalId].portalName = portal.toString();
        		Portals[portalId].worldName = config.getConfig().getString(portal.toString() + ".world");
        		World world = Bukkit.getWorld(config.getConfig().getString(portal.toString() + ".world"));
        		Portals[portalId].pos1 = new Location(world, config.getConfig().getInt(portal.toString() + ".pos1.X"), config.getConfig().getInt(portal.toString() + ".pos1.Y"), config.getConfig().getInt(portal.toString() + ".pos1.Z"));
        		Portals[portalId].pos2 = new Location(world, config.getConfig().getInt(portal.toString() + ".pos2.X"), config.getConfig().getInt(portal.toString() + ".pos2.Y"), config.getConfig().getInt(portal.toString() + ".pos2.Z"));
        		
    			portalId++;
        	}
        	portalsActive = true;
    	}
    	else{
    		portalsActive = false;
    	}
    	
    }
    
    public static String create(Location pos1, Location pos2 , String name, String destination, Material triggerBlock, PortalArg... extraData) {
    	return create(pos1, pos2, name, destination, triggerBlock, null, extraData);
    }
    
	public static String create(Location pos1, Location pos2, String name, String destination, Material triggerBlock, String serverName, PortalArg... portalArgs) {
		
		if(!pos1.getWorld().equals(pos2.getWorld())){
			plugin.getLogger().log(Level.WARNING, "pos1 and pos2 must be in the same world!");
			return "\u00A7cPortal creation error, pos1 and pos2 must be in the same world!";
		}
		
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
		
		Location checkpos1 = new Location(pos1.getWorld(), HighX, HighY, HighZ);
		Location checkpos2 = new Location(pos2.getWorld(), LowX, LowY, LowZ);
		
		if(checkPortalOverlap(checkpos1, checkpos2)){
			plugin.getLogger().log(Level.WARNING, "Portals must not overlap!");
			return "\u00A7cPortal creation error, portals must not overlap!";
		}
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name + ".world", pos1.getWorld().getName());
		
		config.getConfig().set(name + ".triggerblock", checkMaterial(triggerBlock));
		
		config.getConfig().set(name + ".destination", destination);
		
		config.getConfig().set(name + ".bungee", serverName);
		
		config.getConfig().set(name + ".pos1.X", HighX);
		config.getConfig().set(name + ".pos1.Y", HighY);
		config.getConfig().set(name + ".pos1.Z", HighZ);
		
		config.getConfig().set(name + ".pos2.X", LowX);
		config.getConfig().set(name + ".pos2.Y", LowY);
		config.getConfig().set(name + ".pos2.Z", LowZ);

        for(PortalArg arg: portalArgs){
            config.getConfig().set(name + ".portalArgs." + arg.argName, arg.value);
        }
		
		config.saveConfig();
		
		loadPortals();
		
		return "\u00A7aPortal creation successful!";
	}
	
	// make this actually work!
	private static boolean checkPortalOverlap(Location pos1, Location pos2) {
		
		if(portalsActive){
			int portalId = 0;
			for(@SuppressWarnings("unused") Object portal : Portal.Portals){
				if(Portals[portalId].worldName.equals(pos2.getWorld().getName())){ // checks that the cubes arnt overlapping by seeing if all 4 corners are not in side another
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos1.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos2.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos1.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos1.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos2.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos2.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos2.getBlockZ())){return true;}
					
					if(checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos1.getBlockZ())){return true;}
				}
				portalId++;
			}
		}
		return false;
	}

	private static boolean checkOverLapPortal(Location pos1, Location pos2, int posX, int posY, int posZ) {
			if(pos1.getX() >= posX && pos1.getY() >= posX && pos1.getZ() >= posZ){
				if((pos2.getX()) <= posX && pos2.getY() <= posY && pos2.getZ() <= posZ){
					return true;
					
				}
			}
			return false;
	}

	private static String checkMaterial(Material triggerBlock) {
		if(triggerBlock.equals(Material.WATER)){
			return "STATIONARY_WATER";
		}
		else if(triggerBlock.equals(Material.LAVA)){
			return "STATIONARY_LAVA";
		}
		return triggerBlock.toString();
	}
	
	public static String create(Location pos1, Location pos2, String portalName, String name, String destination, PortalArg... extraData) {
        return create(pos1, pos2, name, destination,(String) null);
	}
	
	@SuppressWarnings("deprecation")
	public static String create(Location pos1, Location pos2, String name, String destination, String serverName) { // add stuff for destination names or coordinates
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
		
		// TODO add a for loop which scans through the addArgs and adds them to the config so that the application can use them
		
		String result = create(pos1, pos2, name, destination, triggerBlockType, serverName);
		
		return result;
	}
	
	public static void redefine(Location pos1, Location pos2, String name){
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set(name + ".pos1.X", pos1.getX());
		config.getConfig().set(name + ".pos1.Y", pos1.getY());
		config.getConfig().set(name + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set(name + ".pos2.X", pos2.getX());
		config.getConfig().set(name + ".pos2.Y", pos2.getY());
		config.getConfig().set(name + ".pos2.Z", pos2.getZ());
		
		config.saveConfig();
		
		loadPortals();
		
	}
	
	public static void remove(String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		Object[] keys = config.getConfig().getKeys(true).toArray();
		for(int i = keys.length - 1; i >= 0; i--){
			String key = keys[i].toString();
			if(key.startsWith(name + ".")){
				config.getConfig().set(key, null);
			}
		}
		config.getConfig().set(name, null);

		// TODO add code to check if people have the portal selected and notify if removed.
		
		/**Set<String> keys = config.getConfig().getKeys(true);
		for(String key: keys){
			if(key.startsWith(name)){
				config.getConfig().set(key, null);
			}
		}*/
		
		/**config.getConfig().set(name + ".world", null);
		config.getConfig().set(name + ".triggerblock", null);
		config.getConfig().set(name + ".destination", null);
		
		config.getConfig().set(name + ".pos1.X", null);
		config.getConfig().set(name + ".pos1.Y", null);
		config.getConfig().set(name + ".pos1.Z", null);
		
		config.getConfig().set(name + ".pos2.X", null);
		config.getConfig().set(name + ".pos2.Y", null);
		config.getConfig().set(name + ".pos2.Z", null);
		
		config.getConfig().set(name + ".pos1", null);
		config.getConfig().set(name + ".pos2", null);
		
		config.getConfig().set(name, null);*/
		
		config.saveConfig();
		
		loadPortals();
	}
	
	public static boolean portalExists(String portalName){
		
		ConfigAccessor portalconfig = new ConfigAccessor(plugin, "Portals.yml");
		String posX = portalconfig.getConfig().getString(portalName + ".pos1.X");
		
		if(posX == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public static boolean activate(Player player, String portalName) {
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		// add other variables or filter code here, or somehow have a way to register them
		
		if(config.getConfig().getString(portalName + ".bungee") != null){
			if(ShowBungeeMessage){
				player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Attempting to warp to \u00A7e" + config.getConfig().getString(portalName + ".bungee") + "\u00A7a.");
			}
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(config.getConfig().getString(portalName + ".bungee"));
			player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
			return false;

		}
		else{
			if(config.getConfig().getString(portalName + ".destination") != null){
				ConfigAccessor configDesti = new ConfigAccessor(plugin, "Destinations.yml");
				String destiName = config.getConfig().getString(portalName + ".destination");
				if(configDesti.getConfig().getString(destiName  + ".world") != null){
                    String permission = config.getConfig().getString(portalName + ".portalArgs.permission");
                    if(permission != null && player.hasPermission(permission) || player.isOp()){
                        boolean warped = Destination.warp(player, destiName);
                        return warped;
                    }
					else{
                        player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You do not have permission to use this portal!");
                        return false;
                    }
				}
				else{
					player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The destination you are currently attempting to warp to doesnt exist!");
					plugin.getLogger().log(Level.SEVERE, "The portal '" + portalName + "' has just had a warp "
							+ "attempt and either the data is corrupt or that destination listed doesn't exist!");
					return false;
				}

			}
			else{
				player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal you are trying to use doesn't have a destination!");
				plugin.getLogger().log(Level.SEVERE, "The portal '" + portalName + "' has just had a warp "
						+ "attempt and either the data is corrupt or portal doesn't exist!");
				return false;
			}
		}
		
		// add code for if the portal doesnt have a destination but a teleport location
		
		
	}
	
	public static void rename(String oldName, String newName){
		
		// set it so it gets all data from one and puts it into another place
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		Set<String> keys = config.getConfig().getKeys(true);
		for(String key: keys){
			if(key.startsWith(oldName + ".")){
				if(config.getConfig().getString(key) != null){
					try
					{
						int intData = Integer.parseInt(config.getConfig().getString(key));
						config.getConfig().set(key.replace(oldName + ".", newName + "."), intData);
					}
					catch(Exception e)
					{
						config.getConfig().set(key.replace(oldName + ".", newName + "."), config.getConfig().getString(key));
					}
					
				}
			}
		}
		
		config.saveConfig();
		
		remove(oldName);
		
	}
	
	

}
