package com.sekwah.advancedportals.legacyspigot.warpeffects;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.legacyspigot.connector.container.LegacySpigotPlayerContainer;

public class EnderWarpEffect implements WarpEffect.Visual, WarpEffect.Sound {
    @Override
    public void onWarpSound(PlayerContainer playerContainer,
                            Action action) {
        if (playerContainer instanceof LegacySpigotPlayerContainer) {
            LegacySpigotPlayerContainer spigotPlayerContainer =
                (LegacySpigotPlayerContainer) playerContainer;
            Player player = spigotPlayerContainer.getPlayer();

            player.getWorld().playSound(player.getLocation(),
                    org.bukkit.Sound.valueOf("entity.enderman.teleport"), 1, 1);
        }
    }

    @Override
    public void onWarpVisual(PlayerContainer playerContainer,
                             Action action) {
        if (playerContainer instanceof LegacySpigotPlayerContainer) {
            LegacySpigotPlayerContainer spigotPlayerContainer =
                (LegacySpigotPlayerContainer) playerContainer;
            Player player = spigotPlayerContainer.getPlayer();
            World world = player.getWorld();
            Location loc = player.getLocation().clone();
            for (int i = 0; i < 10; i++) {
                world.playEffect(loc, Effect.ENDER_SIGNAL, 0);
            }
            loc.add(0D, 1D, 0D);
            for (int i = 0; i < 10; i++) {
                world.playEffect(loc, Effect.ENDER_SIGNAL, 0);
            }
        }
    }
}
