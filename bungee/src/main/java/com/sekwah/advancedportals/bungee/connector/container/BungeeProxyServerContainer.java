package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.proxycore.connector.container.ProxyServerContainer;
import net.md_5.bungee.api.connection.Server;

public class BungeeProxyServerContainer implements ProxyServerContainer {
    private final Server server;

    public BungeeProxyServerContainer(Server server) {
        this.server = server;
    }
}
