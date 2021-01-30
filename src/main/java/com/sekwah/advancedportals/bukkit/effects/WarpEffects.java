package com.sekwah.advancedportals.bukkit.effects;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.portals.AdvancedPortal;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpEffects {

	private static AdvancedPortalsPlugin plugin = null;

	public boolean oldSoundLoc = true;

	public static Sound[] sounds = new Sound[2];

	public static boolean soundError = false;

	public WarpEffects(AdvancedPortalsPlugin pluginTemp) {

		plugin = pluginTemp;

		//sounds[0] = findSound(plugin, "ENTITY_ENDERMEN_TELEPORT", "ENDERMAN_TELEPORT");
		sounds[0] = Sound.ENTITY_ENDERMAN_TELEPORT;

		sounds[1] = Sound.ENTITY_GENERIC_EXPLODE;

	}

	public static Sound findSound(AdvancedPortalsPlugin plugin, String newName, String oldName){
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

	public static void activateEffect(Player player) {
		Location loc = player.getLocation();
		World world = player.getWorld();
		switch (plugin.getSettings().getCurrentWarpParticles()){
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

	public static void activateParticle(Player player, String particle_name) {
		Particle particle = Portal.getParticle(particle_name);
		if(particle == null) {
			plugin.getLogger().warning("wrong particle name: " + particle_name);
			return;
		}

		Location loc_from = player.getLocation();

		World world = player.getWorld();

		world.spawnParticle(particle, loc_from, 100, 1, 1, 1);

	}

	public static void activateSound(Player player) {
		if(!soundError){
			Location loc = player.getLocation();
			World world = player.getWorld();
			switch (plugin.getSettings().getCurrentWarpSound()){
				case 1:
					world.playSound(loc, sounds[0], 1F, 1F);
				default: break;
			}
		}
	}

}
