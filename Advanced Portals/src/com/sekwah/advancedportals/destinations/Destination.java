package com.sekwah.advancedportals.destinations;

import org.bukkit.Location;
import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;

public class Destination {
	
    private static AdvancedPortalsPlugin plugin;

	public Destination(AdvancedPortalsPlugin plugin) {
    	Destination.plugin = plugin;
    }
	
	public static void create(Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
		config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
		config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());
		
		config.saveConfig();
	}
	
	public static void move(Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
		config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
		config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());
		
		config.saveConfig();
	}
	
	public static void rename(String oldName, String newName){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(newName + ".world", config.getConfig().getString(oldName + ".world"));
		
		config.getConfig().set(newName + ".pos.X", config.getConfig().getInt(oldName + ".pos.X"));
		config.getConfig().set(newName + ".pos.Y", config.getConfig().getInt(oldName + ".pos.Y"));
		config.getConfig().set(newName + ".pos.Z", config.getConfig().getInt(oldName + ".pos.Z"));
		
		remove(oldName);
		
		config.saveConfig();
	}
	
	public static void remove(String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name + ".world", null);
		
		config.getConfig().set(name + ".pos.X", null);
		config.getConfig().set(name + ".pos.Y", null);
		config.getConfig().set(name + ".pos.Z", null);
		
		config.saveConfig();
	}
}
