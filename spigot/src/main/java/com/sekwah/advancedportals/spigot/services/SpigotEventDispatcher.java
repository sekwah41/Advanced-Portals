package com.sekwah.advancedportals.spigot.services;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.EventDispatcher;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.spigot.api.events.WarpEvent;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;
import org.bukkit.Bukkit;

public class SpigotEventDispatcher implements EventDispatcher {
    @Override
    public boolean dispatchWarpEvent(PlayerContainer player, AdvancedPortal portal,
                                     ActivationData data, WarpStage stage) {
        if (player instanceof SpigotPlayerContainer) {
            WarpEvent event = new WarpEvent(((SpigotPlayerContainer) player).getPlayer(), portal, data, stage);
            Bukkit.getPluginManager().callEvent(event);
            return !event.isCancelled();
        }
        return true;
    }
}
