package com.sekwah.advancedportals.proxycore;

import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyServerContainer;

public class AdvancedPortalsProxyCore {

    private final InfoLogger logger;
    private final ProxyContainer proxyContainer;

    public AdvancedPortalsProxyCore(InfoLogger logger, ProxyContainer proxyContainer) {
        this.logger = logger;
        this.proxyContainer = proxyContainer;
    }

    public void onEnable() {
        this.logger.info("\u00A7aSuccessfully enabled!");
    }

    public void onDisable() {

        this.logger.info("\u00A7cDisabling plugin!");
    }

    public void onServerConnect(ProxyServerContainer server, ProxyPlayerContainer player) {

    }

    /**
     * Messages coming on the ProxyMessages.CHANNEL_NAME from the server, others will be filtered out before now.
     * @param player
     * @param message
     */
    public void incomingMessage(ProxyPlayerContainer player, byte[] message) {
        this.logger.info("Message received from server");
    }
}
