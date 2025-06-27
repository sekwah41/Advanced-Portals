package com.sekwah.advancedportals.legacyspigot.warpeffects;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.legacyspigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.legacyspigot.connector.container.LegacySpigotPlayerContainer;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class EnderWarpEffect implements WarpEffect.Visual, WarpEffect.Sound {
    org.bukkit.Sound sound;

    {
        try {
            sound = org.bukkit.Sound.valueOf("ENTITY_ENDERMEN_TELEPORT");
        } catch (IllegalArgumentException e) {
            try {
                sound = org.bukkit.Sound.valueOf("ENDERMAN_TELEPORT");
            } catch (IllegalArgumentException e2) {
                AdvancedPortalsPlugin.getInstance().getLogger().warning(
                    "Could not find the sound ENTITY_ENDERMAN_TELEPORT or ENDERMAN_TELEPORT");
            }
        }
    }

    @Override
    public void onWarpSound(PlayerContainer playerContainer, Action action) {
        if (playerContainer instanceof LegacySpigotPlayerContainer) {
            LegacySpigotPlayerContainer spigotPlayerContainer =
                (LegacySpigotPlayerContainer) playerContainer;
            Player player = spigotPlayerContainer.getPlayer();

            if (sound == null) {
                return;
            }
            player.getWorld().playSound(player.getLocation(), sound, 1, 1);
        }
    }

    @Override
    public void onWarpVisual(PlayerContainer playerContainer, Action action) {
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
