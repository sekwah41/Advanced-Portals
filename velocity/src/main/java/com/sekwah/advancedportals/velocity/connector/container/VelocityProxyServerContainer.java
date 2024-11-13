package com.sekwah.advancedportals.velocity.connector.container;

import com.sekwah.advancedportals.proxycore.connector.container.ProxyServerContainer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class VelocityProxyServerContainer implements ProxyServerContainer {
    private final RegisteredServer server;

    public VelocityProxyServerContainer(RegisteredServer server) {
        this.server = server;
    }

    @Override
    public String getServerName() {
        return this.server.getServerInfo().getName();
    }
}
