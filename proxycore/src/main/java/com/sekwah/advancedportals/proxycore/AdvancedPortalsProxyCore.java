package com.sekwah.advancedportals.proxycore;

import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.core.network.ProxyTransferPacket;
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
        this.logger.info("Message received from server via " + player.getName());

        var buffer = ByteStreams.newDataInput(message);
        var messageType = buffer.readUTF();

        // Might be a bit overboard for some as they'll only have one value, but try to keep the decode behavior with
        // the encode behavior in the packets
        switch (messageType) {
            case ProxyMessages.PROXY_TRANSFER:
                var msg = ProxyTransferPacket.decode(buffer);
                this.logger.info("Transfer request for " + player.getName() + " to " + msg.getServerName());
                this.proxyContainer.transferPlayer(player, msg.getServerName());
                break;
            default:
                this.logger.info("Unknown message type: " + messageType);
        }

    }
}
