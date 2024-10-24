package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeProxyPlayerContainer implements ProxyPlayerContainer {

    private final ProxiedPlayer player;

    public BungeeProxyPlayerContainer(ProxiedPlayer player) {
        this.player = player;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

}
