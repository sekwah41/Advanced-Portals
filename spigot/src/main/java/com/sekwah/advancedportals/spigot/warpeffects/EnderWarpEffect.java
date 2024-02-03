package com.sekwah.advancedportals.spigot.warpeffects;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.WorldLocation;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;

public class EnderWarpEffect implements WarpEffect.Visual, WarpEffect.Sound {

    @Override
    public void onWarpSound(PlayerContainer playerContainer, WarpEffect.Action action, AdvancedPortal portal) {
        if(playerContainer instanceof SpigotPlayerContainer spigotPlayerContainer) {
            var player = spigotPlayerContainer.getPlayer();

            player.playSound(player.getLocation(), "entity.enderman.teleport", 1, 1);
        }
    }

    @Override
    public void onWarpVisual(PlayerContainer player, WarpEffect.Action action, AdvancedPortal portal) {
        if (action == WarpEffect.Action.ENTER) {
            player.spawnParticle(new WorldLocation(portal.getPortalLocation()), "ENDER_SIGNAL", 1, 1, 1, 1, 1);
        } else {
            player.spawnParticle(new WorldLocation(portal.getPortalLocation()), "ENDER_SIGNAL", 1, 1, 1, 1, 1);
        }
    }
}
