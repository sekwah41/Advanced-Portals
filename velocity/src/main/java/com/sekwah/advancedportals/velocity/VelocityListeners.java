package com.sekwah.advancedportals.velocity;

import com.google.inject.Inject;
import com.sekwah.advancedportals.proxy.ProxyListeners;
import com.sekwah.advancedportals.velocity.connector.container.VelocityPlayerContainer;
import com.sekwah.advancedportals.velocity.connector.container.VelocityServerContainer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;

public class VelocityListeners {

    @Inject
    ProxyListeners proxyListeners;
    private final ChannelIdentifier AP_CHANNEL;
    private final ProxyServer proxyServer;


    public VelocityListeners(ChannelIdentifier apChannel, ProxyServer proxyServer) {
        this.AP_CHANNEL = apChannel;
        this.proxyServer = proxyServer;
    }

    @Subscribe
    public void postJoinEvent(ServerPostConnectEvent event) {
        proxyListeners.connectedEvent(new VelocityPlayerContainer(event.getPlayer()));
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if(!event.getIdentifier().equals(AP_CHANNEL)) return;
        if(!(event.getSource() instanceof ServerConnection serverConnection)) return;
        proxyListeners.pluginMessageEvent(event.getData(),
                new VelocityServerContainer(proxyServer, null),
                new VelocityPlayerContainer(serverConnection.getPlayer()));
        // So the client packets don't go through to other servers and to always trigger on AP channel
        event.setResult(PluginMessageEvent.ForwardResult.handled());
    }


}
