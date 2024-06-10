package com.sekwah.advancedportals.velocity.connector.container;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.tags.activation.CommandTag;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;

import java.util.List;
import java.util.UUID;

public class VelocityServerContainer implements ServerContainer {

    private final ProxyServer proxyServer;
    private final ServerConnection serverConnection;

    public VelocityServerContainer(ProxyServer proxyServer, ServerConnection serverConnection) {
        this.proxyServer = proxyServer;
        this.serverConnection = serverConnection;
    }

    @Override
    public PlayerContainer getPlayer(String name) {
        if(serverConnection == null) {
            var player = proxyServer.getPlayer(name);
            return player.map(VelocityPlayerContainer::new).orElse(null);
        }
        else if(proxyServer == null) {
            var player = serverConnection.getPlayer();
            return new VelocityPlayerContainer(player);
        }
        return null;
    }

    @Override
    public PlayerContainer getPlayer(UUID name) {
        if(proxyServer == null) return null;
        var player = proxyServer.getPlayer(name);
        return player.map(VelocityPlayerContainer::new).orElse(null);
    }

    @Override
    public PlayerContainer[] getPlayers() {
        if(serverConnection == null) {
            return proxyServer.getAllPlayers().stream()
                    .map(VelocityPlayerContainer::new)
                    .toArray(PlayerContainer[]::new);
        } else if (proxyServer == null) {
            return serverConnection.getServer().getPlayersConnected().stream()
                    .map(VelocityPlayerContainer::new)
                    .toArray(PlayerContainer[]::new);
        }
        return null;
    }

    @Override
    public void dispatchCommand(UUID uuid, String command, CommandTag.CommandLevel commandLevel) {
        if(proxyServer == null) return;
        //TODO: add command levels
        proxyServer.getCommandManager().executeAsync(proxyServer.getPlayer(uuid).orElse(null), command);
    }

    @Override
    public String getName() {
        if(serverConnection == null) return null;
        return serverConnection.getServerInfo().getName();
    }

    // Ignore the following for Velocity
    @Override
    public WorldContainer getWorld(String name) {
        return null;
    }

    @Override
    public List<String> getTriggerBlocks() {
        return null;
    }
}
