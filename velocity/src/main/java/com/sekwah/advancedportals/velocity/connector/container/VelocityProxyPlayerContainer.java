package com.sekwah.advancedportals.velocity.connector.container;

import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import com.velocitypowered.api.proxy.Player;

public class VelocityProxyPlayerContainer implements ProxyPlayerContainer {

    private final Player player;

    public VelocityProxyPlayerContainer(Player player) {
        this.player = player;
    }

    @Override
    public String getUUID() {
        return player.getUniqueId().toString();
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    public Player getPlayer() {
        return this.player;
    }
}
