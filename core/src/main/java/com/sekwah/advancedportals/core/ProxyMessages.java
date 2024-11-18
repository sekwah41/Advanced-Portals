package com.sekwah.advancedportals.core;

/**
 * messages going to the proxy will start with proxy: messages going to the
 * server will start with server:
 */
public class ProxyMessages {
    /**
     * Could split by channel messages we will handle it ourselves
     */
    public static final String CHANNEL_NAME = "advancedportals:message";

    public static final String PROXY_TRANSFER_DESTI = "proxy:transfer_desti";
    public static final String PROXY_TRANSFER = "proxy:transfer";
    public static final String PROXY_COMMAND = "proxy:command";

    public static final String SERVER_DESTI = "server:destination";
}
