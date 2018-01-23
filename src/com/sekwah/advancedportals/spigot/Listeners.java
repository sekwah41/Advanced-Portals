package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {

    private final AdvancedPortalsPlugin plugin;
    private final CoreListeners coreListeners;

    public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        this.coreListeners = plugin.getPortalsCore().getCoreListeners();
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        coreListeners.playerJoin(new PlayerContainer(event.getPlayer()));
    }

}
