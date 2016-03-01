package com.sekwah.advancedportals.effects;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.Assets;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpEffects {

	public boolean oldSoundLoc = true;

	public static Sound[] sounds = new Sound[1];

	public static boolean soundError = false;

	public WarpEffects(AdvancedPortalsPlugin plugin) {
		try {
			sounds[0] = Sound.valueOf("ENTITY_ENDERMEN_TELEPORT");
			plugin.getLogger().info("Sounds found");
		} catch (IllegalArgumentException e) {
			plugin.getLogger().info("Using old effect names");
			try {
			sounds[0] = Sound.valueOf("ENDERMAN_TELEPORT");
			} catch (IllegalArgumentException e2) {
				plugin.getLogger().warning("There was an error using both the old and new names.");
				soundError = true;
			}
		}
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
