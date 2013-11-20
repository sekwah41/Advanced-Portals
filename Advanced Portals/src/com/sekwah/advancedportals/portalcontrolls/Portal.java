package com.sekwah.advancedportals.portalcontrolls;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;

public class Portal {
	
	private static AdvancedPortalsPlugin plugin;
	
    public Portal(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

	public static void create(Player player, Material triggerBlockId, Location pos1, Location pos2 , String name) {
		
	}
	
	public static void create(Player player, Location pos1, Location pos2, String name) { // add stuff for destination names or coordinates
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
		
		config.getConfig().set("portals." + name + ".world", pos1.getWorld().getName());
		config.getConfig().set("portals." + name + ".hastriggerblock", false);
		
		config.getConfig().set("portals." + name + ".pos1.X", pos1.getX());
		config.getConfig().set("portals." + name + ".pos1.Y", pos1.getY());
		config.getConfig().set("portals." + name + ".pos1.Z", pos1.getZ());
		
		config.getConfig().set("portals." + name + ".pos2.X", pos2.getX());
		config.getConfig().set("portals." + name + ".pos2.Y", pos2.getY());
		config.getConfig().set("portals." + name + ".pos2.Z", pos2.getZ());
		
		config.getConfig().set("portals." + name + ".creator", player.getName());
	}
	
	public static void redefine(Player player, Location pos1, Location pos2, String name){
		
	}
	
	public static void remove(Player player, String name){
		
	}
	
	

}
