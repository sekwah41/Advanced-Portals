package com.sekwah.advancedportals.effects;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.Assets;
import com.sekwah.advancedportals.portals.AdvancedPortal;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpEffects {

	public boolean oldSoundLoc = true;

	public static Sound[] sounds = new Sound[2];

	public static boolean soundError = false;

	public WarpEffects(AdvancedPortalsPlugin plugin) {

		sounds[0] = findSound(plugin, "ENTITY_ENDERMEN_TELEPORT", "ENDERMAN_TELEPORT");

		// SPIGOT STOP CHANGING THE BLOODY NAMES! we already knew what an explosion was...
		sounds[1] = findSound(plugin, "ENTITY_GENERIC_EXPLODE", "EXPLODE");

	}

	public Sound findSound(AdvancedPortalsPlugin plugin, String newName, String oldName){
		Sound soundFound = null;
		try{
			soundFound = Sound.valueOf(newName);
			plugin.getLogger().info(newName + " found");
		} catch (IllegalArgumentException e) {
			try {
				soundFound = Sound.valueOf(oldName);
				plugin.getLogger().info("Using old effect name: " + oldName);
			} catch (IllegalArgumentException e2) {
				plugin.getLogger().warning("There was an error using both the old and new names for " + newName);
				soundError = true;
			}
		}
		return soundFound;
	}

	public static void activateParticles(Player player) {
		Location loc = player.getLocation();
		World world = player.getWorld();
		switch (Assets.currentWarpParticles){
			case 1: 
				for(int i = 0; i < 10; i++){
					world.playEffect(loc, Effect.ENDER_SIGNAL, 0);
				}
				loc.add(0D, 1D, 0D);
				for(int i = 0; i < 10; i++){
					world.playEffect(loc, Effect.ENDER_SIGNAL, 0);
				}
			default: break;
		}
		
	}

	public static void activateSound(Player player) {
		if(!soundError){
			Location loc = player.getLocation();
			World world = player.getWorld();
			switch (Assets.currentWarpParticles){
				case 1:
					world.playSound(loc, sounds[0], 1F, 1F);
				default: break;
			}
		}
	}

}
