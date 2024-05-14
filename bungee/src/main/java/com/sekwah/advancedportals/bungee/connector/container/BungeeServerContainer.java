package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.tags.activation.CommandTag;
import net.md_5.bungee.api.ProxyServer;

import java.util.List;
import java.util.UUID;

public class BungeeServerContainer implements ServerContainer {

    private final ProxyServer proxyServer;

    public BungeeServerContainer(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public PlayerContainer getPlayer(String name) {
        return new BungeePlayerContainer(proxyServer.getPlayer(name));
    }

    @Override
    public PlayerContainer getPlayer(UUID name) {
        return new BungeePlayerContainer(proxyServer.getPlayer(name));
    }

    @Override
    public PlayerContainer[] getPlayers() {
        return proxyServer.getPlayers().stream()
                .map(BungeePlayerContainer::new)
                .toArray(PlayerContainer[]::new);
    }

    @Override
    public void dispatchCommand(UUID uuid, String command, CommandTag.CommandLevel commandLevel) {
        //TODO: add command levels
        proxyServer.getPluginManager().dispatchCommand(proxyServer.getPlayer(uuid), command);
    }

    @Override
    public String getName() {
        return proxyServer.getName();
    }

    // Not used in Bungee
    @Override
    public WorldContainer getWorld(String name) {
        return null;
    }

    @Override
    public List<String> getTriggerBlocks() {
        return null;
    }
}
