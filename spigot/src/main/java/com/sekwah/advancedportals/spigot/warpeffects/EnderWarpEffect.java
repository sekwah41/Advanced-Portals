package com.sekwah.advancedportals.spigot.warpeffects;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Particle;

public class EnderWarpEffect implements WarpEffect.Visual, WarpEffect.Sound {
    @Override
    public void onWarpSound(PlayerContainer playerContainer,
                            WarpEffect.Action action) {
        if (playerContainer
            instanceof SpigotPlayerContainer spigotPlayerContainer) {
            var player = spigotPlayerContainer.getPlayer();

            player.getWorld().playSound(player.getLocation(),
                                        "entity.enderman.teleport", 1, 1);

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1);
        }
    }

    @Override
    public void onWarpVisual(PlayerContainer playerContainer,
                             WarpEffect.Action action) {
        if (playerContainer
            instanceof SpigotPlayerContainer spigotPlayerContainer) {
            var player = spigotPlayerContainer.getPlayer();
            var world = player.getWorld();
            var loc = player.getLocation().clone();
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
