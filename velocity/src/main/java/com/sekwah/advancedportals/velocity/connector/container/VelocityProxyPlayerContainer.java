package com.sekwah.advancedportals.velocity.connector.container;

import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import net.kyori.adventure.text.Component;

public class VelocityProxyPlayerContainer implements ProxyPlayerContainer {

    private final Player player;
    private final LegacyChannelIdentifier channel;

    public VelocityProxyPlayerContainer(Player player, LegacyChannelIdentifier channel) {
        this.player = player;
        this.channel = channel;
    }

    @Override
    public String getUUID() {
        return player.getUniqueId().toString();
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public void sendServerPluginMessage(byte[] data) {
        player.getCurrentServer().ifPresent(serverConnection -> serverConnection.sendPluginMessage(channel, data));
    }

    public Player getPlayer() {
        return this.player;
    }
}
