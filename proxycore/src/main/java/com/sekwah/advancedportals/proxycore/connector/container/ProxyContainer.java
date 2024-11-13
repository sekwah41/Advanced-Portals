package com.sekwah.advancedportals.proxycore.connector.container;

public interface ProxyContainer {
    void invokeCommand(ProxyPlayerContainer proxyPlayer, String command);

    void transferPlayer(ProxyPlayerContainer proxyPlayer, String serverName);

}
