package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.bungee.AdvancedPortalsBungeePlugin;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeProxyContainer implements ProxyContainer {
    private final AdvancedPortalsBungeePlugin plugin;

    public BungeeProxyContainer(AdvancedPortalsBungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void invokeCommand(ProxyPlayerContainer proxyPlayer,
                              String command) {
        // Should never not be true but just to be safe
        if (proxyPlayer instanceof BungeeProxyPlayerContainer) {
            BungeeProxyPlayerContainer playerContainer =
                (BungeeProxyPlayerContainer) proxyPlayer;
            plugin.getProxy().getPluginManager().dispatchCommand(
                playerContainer.getPlayer(), command);
        }
    }

    @Override
    public void transferPlayer(ProxyPlayerContainer proxyPlayer,
                               String serverName) {
        // Should never not be true but just to be safe
        if (proxyPlayer instanceof BungeeProxyPlayerContainer) {
            BungeeProxyPlayerContainer playerContainer =
                (BungeeProxyPlayerContainer) proxyPlayer;
            ServerInfo serverInfo = plugin.getProxy().getServerInfo(serverName);
            ProxiedPlayer player = playerContainer.getPlayer();
            if (serverInfo == null) {
                player.sendMessage(new TextComponent(
                    Lang.convertColors("&cCould not find server: &e")
                    + serverName));
                return;
            }
            player.connect(serverInfo);
        }
    }
}
