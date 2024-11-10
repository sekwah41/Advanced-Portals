package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.core.ProxyMessages;
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

    @Override
    public String getUUID() {
        return this.player.getUniqueId().toString();
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    public void sendPluginMessage(byte[] data) {
        this.player.getServer().sendData(ProxyMessages.CHANNEL_NAME, data);
    }

}
