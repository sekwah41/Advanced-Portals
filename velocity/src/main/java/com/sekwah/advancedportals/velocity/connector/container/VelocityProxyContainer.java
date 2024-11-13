package com.sekwah.advancedportals.velocity.connector.container;

import com.sekwah.advancedportals.proxycore.connector.container.ProxyContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

public class VelocityProxyContainer implements ProxyContainer {
    private final ProxyServer proxy;

    public VelocityProxyContainer(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Override
    public void invokeCommand(ProxyPlayerContainer proxyPlayer, String command) {
        if(proxyPlayer instanceof VelocityProxyPlayerContainer playerContainer) {
            this.proxy.getCommandManager().executeAsync(playerContainer.getPlayer(), command);
        }
    }

    @Override
    public void transferPlayer(ProxyPlayerContainer proxyPlayer, String serverName) {
        if(proxyPlayer instanceof VelocityProxyPlayerContainer playerContainer) {
            this.proxy.getServer(serverName).ifPresentOrElse(
                server -> {
                    playerContainer.getPlayer().createConnectionRequest(server).fireAndForget();
                },
                () -> playerContainer.getPlayer().sendMessage(Component.text("Could not find server: " + serverName))
            );
        }
    }
}
