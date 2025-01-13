package com.sekwah.advancedportals.velocity;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.BuildConstants;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.proxycore.AdvancedPortalsProxyCore;
import com.sekwah.advancedportals.velocity.connector.container.VelocityProxyContainer;
import com.sekwah.advancedportals.velocity.connector.container.VelocityProxyPlayerContainer;
import com.sekwah.advancedportals.velocity.connector.container.VelocityProxyServerContainer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import org.slf4j.Logger;

@Plugin(authors = {"sekwah41"}, id = "advancedportals",
        name = "Advanced Portals",
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/",
        version = BuildConstants.VERSION)
public class AdvancedPortalsVelocityPlugin {
    private AdvancedPortalsProxyCore proxyCore;

    private final Logger logger;
    private final ProxyServer proxy;

    private LegacyChannelIdentifier AP_CHANNEL;

    @Inject
    public AdvancedPortalsVelocityPlugin(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
        this.proxyCore = new AdvancedPortalsProxyCore(
            new VelocityInfoLogger(this.logger, this.proxy),
            new VelocityProxyContainer(this.proxy));
        this.proxyCore.onEnable();
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        AP_CHANNEL = new LegacyChannelIdentifier(ProxyMessages.CHANNEL_NAME);

        proxy.getChannelRegistrar().register(AP_CHANNEL);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getIdentifier().equals(AP_CHANNEL)) {
            if (event.getSource()
                    instanceof ServerConnection) {
                ServerConnection serverConnection = (ServerConnection) event.getSource();
                this.proxyCore.incomingMessage(
                    new VelocityProxyPlayerContainer(
                        serverConnection.getPlayer(), AP_CHANNEL),
                    event.getData());
            }
            // So that client packets don't make it through to the servers,
            // always trigger on this channel.
            event.setResult(PluginMessageEvent.ForwardResult.handled());
        }
    }

    @Subscribe
    public void postJoinEvent(ServerPostConnectEvent event) {
        event.getPlayer().getCurrentServer().ifPresent(serverConnection -> {
            this.proxyCore.onServerConnect(
                new VelocityProxyServerContainer(serverConnection.getServer()),
                new VelocityProxyPlayerContainer(event.getPlayer(),
                                                 AP_CHANNEL));
        });
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        this.proxyCore.onPlayerDisconnect(
            new VelocityProxyPlayerContainer(event.getPlayer(), AP_CHANNEL));
    }
}
