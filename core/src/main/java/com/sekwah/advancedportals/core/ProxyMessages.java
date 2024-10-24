package com.sekwah.advancedportals.core;

/**
 * messages going to the proxy will start with proxy: messages going to the server will start with server:
 */
public class ProxyMessages {

    public static String CHANNEL_NAME = "advancedportals";

    public static String PROXY_TRANSFER_DESTI = "proxy:transfer_desti";
    public static String PROXY_TRANSFER = "proxy:transfer";
    public static String PROXY_DESTI = "proxy:destination";
    public static String PROXY_COMMAND = "proxy:command";

    public static String SERVER_DESTI = "server:destination";
}
