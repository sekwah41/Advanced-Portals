package com.sekwah.advancedportals.effects;

import com.sekwah.advancedportals.Assets;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarpEffects {

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
		Location loc = player.getLocation();
		World world = player.getWorld();
		switch (Assets.currentWarpParticles){
			case 1: 
				world.playSound(loc, Sound.ENDERMAN_TELEPORT, 1F, 1F);
			default: break;
		}
			
		
	}

}
