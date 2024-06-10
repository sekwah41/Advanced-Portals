package com.sekwah.advancedportals.velocity;

import com.google.inject.Inject;
import com.sekwah.advancedportals.proxy.ProxyMessages;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import org.slf4j.Logger;

@Plugin(
        name = "Advanced Portals",
        id = "advancedportals",
        version = "1.0.0-BETA-SNAPSHOT",
        authors = {"sekwah41"},
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/"
)

public class AdvancedPortalsPlugin {

    private final Logger logger;
    private final ProxyServer proxyServer;

    @Inject
    AdvancedPortalsPlugin(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event){
        ChannelIdentifier AP_CHANNEL = ProxyMessages.CHANNEL_NAME::toString;

        proxyServer.getEventManager().register(this, new VelocityListeners(AP_CHANNEL, proxyServer));
    }

    public Logger getLogger() {
        return logger;
    }
}
