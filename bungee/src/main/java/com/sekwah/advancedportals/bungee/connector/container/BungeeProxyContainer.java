package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.bungee.AdvancedPortalsBungeePlugin;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;

public class BungeeProxyContainer implements ProxyContainer {

    private final AdvancedPortalsBungeePlugin plugin;

    public BungeeProxyContainer(AdvancedPortalsBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void invokeCommand(ProxyPlayerContainer proxyPlayer, String command) {
    }

    @Override
    public void transferPlayer(ProxyPlayerContainer proxyPlayer, String serverName) {

    }
}
