package com.sekwah.advancedportals.bungee;

import com.google.inject.Inject;
import com.sekwah.advancedportals.bungee.connector.container.BungeePlayerContainer;
import com.sekwah.advancedportals.bungee.connector.container.BungeeServerContainer;
import com.sekwah.advancedportals.proxy.ProxyListeners;
import com.sekwah.advancedportals.proxy.ProxyMessages;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListeners implements Listener {

    @Inject
    ProxyListeners proxyListeners;
    private final ProxyServer proxyServer;
    
    public BungeeListeners(AdvancedPortalsPlugin advancedPortalsPlugin) {
        this.proxyServer = advancedPortalsPlugin.getProxy();
    }

    @EventHandler
    public void postJoinEvent(ServerConnectedEvent event) {
        proxyListeners.connectedEvent(new BungeePlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onMessageReceived(PluginMessageEvent event) {
        if(!event.getTag().equalsIgnoreCase(ProxyMessages.CHANNEL_NAME.toString()) || !(event.getSender() instanceof Server)) return;
        if(!(event.getReceiver() instanceof ProxiedPlayer proxiedPlayer)) return;

        proxyListeners.pluginMessageEvent(event.getData(),
                new BungeeServerContainer(proxyServer),
                new BungeePlayerContainer(proxiedPlayer));
        event.setCancelled(true);
    }
}
