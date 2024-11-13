package com.sekwah.advancedportals.bungee;

import com.sekwah.advancedportals.bungee.connector.container.BungeeProxyPlayerContainer;
import com.sekwah.advancedportals.bungee.connector.container.BungeeProxyServerContainer;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.proxycore.AdvancedPortalsProxyCore;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class EventListener implements Listener {


    private final AdvancedPortalsBungeePlugin plugin;
    private final AdvancedPortalsProxyCore proxyCore;

    public EventListener(AdvancedPortalsBungeePlugin plugin, AdvancedPortalsProxyCore proxyCore) {
        this.plugin = plugin;
        this.proxyCore = proxyCore;
    }

    @EventHandler
    public void onMessageReceived(PluginMessageEvent event) {
        if(!event.getTag().equalsIgnoreCase(ProxyMessages.CHANNEL_NAME)) return;
        event.setCancelled(true);

        if(!(event.getSender() instanceof Server)) return;

        if(event.getReceiver() instanceof ProxiedPlayer player) {
            this.proxyCore.incomingMessage(new BungeeProxyPlayerContainer(player), event.getData());
        }
    }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        this.proxyCore.onServerConnect(new BungeeProxyServerContainer(event.getServer()), new BungeeProxyPlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        this.proxyCore.onPlayerDisconnect(new BungeeProxyPlayerContainer(event.getPlayer()));
    }
}
