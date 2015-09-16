package com.sekwah.advancedportals.destinations;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.effects.WarpEffects;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class Destination {
	
    private static AdvancedPortalsPlugin plugin;

	public Destination(AdvancedPortalsPlugin plugin) {
    	Destination.plugin = plugin;
    }

	// TODO add permissions for destinations.
	
	public static void create(Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
		config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
		config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());
		
		config.getConfig().set(name.toLowerCase() + ".pos.pitch", location.getPitch());
		config.getConfig().set(name.toLowerCase() + ".pos.yaw", location.getYaw());
		
		config.saveConfig();
	}
	
	public static void move(Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
		config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
		config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());
		
		config.getConfig().set(name.toLowerCase() + ".pos.pitch", location.getPitch());
		config.getConfig().set(name.toLowerCase() + ".pos.yaw", location.getYaw());
		
		config.saveConfig();
	}
	
	public static void rename(String oldName, String newName){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(newName.toLowerCase() + ".world", config.getConfig().getString(oldName + ".world"));
		
		config.getConfig().set(newName.toLowerCase() + ".pos.X", config.getConfig().getDouble(oldName + ".pos.X"));
		config.getConfig().set(newName.toLowerCase() + ".pos.Y", config.getConfig().getDouble(oldName + ".pos.Y"));
		config.getConfig().set(newName.toLowerCase() + ".pos.Z", config.getConfig().getDouble(oldName + ".pos.Z"));
		
		config.getConfig().set(newName.toLowerCase() + ".pos.pitch", config.getConfig().getDouble(oldName + ".pos.pitch"));
		config.getConfig().set(newName.toLowerCase() + ".pos.yaw", config.getConfig().getDouble(oldName + ".pos.yaw"));

		remove(oldName);
		
		config.saveConfig();
	}
	
	public static void remove(String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", null);
		config.getConfig().set(name.toLowerCase() + ".pos.Y", null);
		config.getConfig().set(name.toLowerCase() + ".pos.Z", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos.pitch", null);
		config.getConfig().set(name.toLowerCase() + ".pos.yaw", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos", null);
		
		config.getConfig().set(name.toLowerCase(), null);
		
		config.saveConfig();
	}
	
	public static boolean warp(Player player, String name, boolean senderror){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		if(config.getConfig().getString(name + ".world") != null){
			Location loc = player.getLocation();
			if(Bukkit.getWorld(config.getConfig().getString(name + ".world")) != null){
				loc.setWorld(Bukkit.getWorld(config.getConfig().getString(name + ".world")));
				
				loc.setX(config.getConfig().getDouble(name + ".pos.X"));
				loc.setY(config.getConfig().getDouble(name + ".pos.Y"));
				loc.setZ(config.getConfig().getDouble(name + ".pos.Z"));
				
				loc.setPitch((float) config.getConfig().getDouble(name + ".pos.pitch"));
				loc.setYaw((float) config.getConfig().getDouble(name + ".pos.yaw"));
				
				WarpEffects.activateParticles(player);
				WarpEffects.activateSound(player);
				Chunk c = loc.getChunk();
				Entity riding = player.getVehicle();
				if (!c.isLoaded()) c.load();
				if(player.getVehicle() != null){
					riding.eject();
					riding.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
					player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
					riding.setPassenger(player);
				}
				else{
					player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
				}
				WarpEffects.activateParticles(player);
				WarpEffects.activateSound(player);
				
				
				return true;
			}
			else{
				if(senderror){
					player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The destination you are trying to warp to seems to be linked to a world that doesn't exist!");
					plugin.getLogger().log(Level.SEVERE, "The destination '" + name + "' is linked to the world "
							+ config.getConfig().getString(name + ".world") + " which doesnt seem to exist any more!");
				}
				return false;
			}
		}
		else{
			if(senderror){
				player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] There has been a problem warping you to the selected destination!");
				plugin.getLogger().log(Level.SEVERE, "The destination '" + name + "' has just had a warp "
						+ "attempt and either the data is corrupt or that destination doesn't exist!");
			}
			return false;
		}
		
	}
	
	/**
	 * Same as other warp but changes sender to player for you.
	 * 
	 * @param player the player being warped
	 * @param name name of the warp
	 * @return returns if the player has warped
	 */
	public static boolean warp(Player player, String name) {
		return warp(player, name, true);
		
	}
	
	/**
	 * Same as other warp but changes sender to player for you.
	 * 
	 * @param sender the player being warped
	 * @param name name of the warp
	 * @return returns if the player has warped
	 */
	public static boolean warp(CommandSender sender, String name, boolean senderror) {
		Player player = (Player)sender;
		return warp(player, name, senderror);
		
	}
	
	/**
	 * Same as other warp but changes sender to player for you.
	 * 
	 * @param sender the player being warped
	 * @param name name of the warp
	 * @return returns if the player has warped
	 */
	public static boolean warp(CommandSender sender, String name) {
		Player player = (Player)sender;
		return warp(player, name, true);
		
	}
	
	public static List<String> destiList(){
		
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		LinkedList<String> destiList = new LinkedList<>();
		
		Set<String> destiSet = config.getConfig().getKeys(false);
    	if(destiSet.size() > 0){
			for(Object desti: destiSet.toArray()){
    			destiSet.add(desti.toString());
    		}
    	}
		
		Collections.sort(destiList);
		return destiList;
	}
}
