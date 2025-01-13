package com.sekwah.advancedportals.spigot.warpeffects;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class EnderWarpEffect implements WarpEffect.Visual, WarpEffect.Sound {
    @Override
    public void onWarpSound(PlayerContainer playerContainer,
                            WarpEffect.Action action) {
        if (playerContainer instanceof SpigotPlayerContainer) {
            SpigotPlayerContainer spigotPlayerContainer =
                (SpigotPlayerContainer) playerContainer;
            org.bukkit.entity.Player player = spigotPlayerContainer.getPlayer();

            player.getWorld().playSound(player.getLocation(),
                                        "entity.enderman.teleport", 1, 1);
        }
    }

    @Override
    public void onWarpVisual(PlayerContainer playerContainer,
                             WarpEffect.Action action) {
        if (playerContainer instanceof SpigotPlayerContainer) {
            SpigotPlayerContainer spigotPlayerContainer =
                (SpigotPlayerContainer) playerContainer;
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
