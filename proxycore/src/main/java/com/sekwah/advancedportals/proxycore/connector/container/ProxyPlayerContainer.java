package com.sekwah.advancedportals.proxycore.connector.container;

public interface ProxyPlayerContainer {
    String getUUID();
    String getName();
    void sendServerPluginMessage(byte[] data);
}
