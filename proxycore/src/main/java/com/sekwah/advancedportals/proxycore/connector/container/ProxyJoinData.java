package com.sekwah.advancedportals.proxycore.connector.container;

public class ProxyJoinData {

    public final String destination;
    public final String serverName;
    public final long joinTime;

    public ProxyJoinData(String destination, String serverName) {
        this.destination = destination;
        this.serverName = serverName;
        this.joinTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.joinTime > 1000 * 15; // 15 seconds
    }
}
