package com.sekwah.advancedportals.destinations;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portalcontrolls.Portal;

public class Destination {
	
    private static AdvancedPortalsPlugin plugin;

	public Destination(AdvancedPortalsPlugin plugin) {
    	Destination.plugin = plugin;
    }
	
	public void create(Player creator, Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name + ".world", location.getWorld().getName());
		
		config.getConfig().set(name + ".pos.X", location.getX());
		config.getConfig().set(name + ".pos.Y", location.getY());
		config.getConfig().set(name + ".pos.Z", location.getZ());
		
		config.getConfig().set(name + ".creator", creator.getName());
		
		config.saveConfig();
	}
	
	public void move(Player creator, Location location, String name){
		
	}
	
	public void rename(Player creator, String oldName, String newName){
		
	}
	
	public void remove(Player player, String name){
		
	}
}
