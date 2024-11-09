package com.sekwah.advancedportals.proxycore;

import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.core.network.ProxyCommandPacket;
import com.sekwah.advancedportals.core.network.ProxyTransferDestiPacket;
import com.sekwah.advancedportals.core.network.ProxyTransferPacket;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyJoinData;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyServerContainer;

import java.util.HashMap;

public class AdvancedPortalsProxyCore {

    private final InfoLogger logger;
    private final ProxyContainer proxyContainer;

    /**
     * Keep a list of destinations players have been moved to
     *
     // TODO add a time to check against for this data to expire, and add a way to clean the data up every now and then
     */
    public HashMap<String, ProxyJoinData> PlayerJoinMap = new HashMap<>();

    public AdvancedPortalsProxyCore(InfoLogger logger, ProxyContainer proxyContainer) {
        this.logger = logger;
        this.proxyContainer = proxyContainer;
    }

    public void onEnable() {
        this.logger.info(Lang.convertColors("&aSuccessfully enabled!"));
    }

    public void onDisable() {
        this.logger.info(Lang.convertColors("&cDisabling plugin!"));
    }

    public void onServerConnect(ProxyServerContainer server, ProxyPlayerContainer player) {

    }

    /**
     * Messages coming on the ProxyMessages.CHANNEL_NAME from the server, others will be filtered out before now.
     * @param player
     * @param message
     */
    public void incomingMessage(ProxyPlayerContainer player, byte[] message) {

        var buffer = ByteStreams.newDataInput(message);
        var messageType = buffer.readUTF();

        // Might be a bit overboard for some as they'll only have one value, but try to keep the decode behavior with
        // the encode behavior in the packets
        switch (messageType) {
            case ProxyMessages.PROXY_TRANSFER:
                var transferPacket = ProxyTransferPacket.decode(buffer);
                this.logger.info("Transfer request for " + player.getName() + " to " + transferPacket.getServerName());
                this.proxyContainer.transferPlayer(player, transferPacket.getServerName());
                break;
            case ProxyMessages.PROXY_COMMAND:
                var commandPacket = ProxyCommandPacket.decode(buffer);
                this.logger.info("Command request for " + player.getName() + " to run /" + commandPacket.getCommand());
                this.proxyContainer.invokeCommand(player, commandPacket.getCommand());
                break;
            case ProxyMessages.PROXY_TRANSFER_DESTI:
                var transferDestiPacket = ProxyTransferDestiPacket.decode(buffer);
                this.logger.info("Transfer request for " + player.getName() + " to " + transferDestiPacket.getServerName() + " with destination " + transferDestiPacket.getDestination());
                this.proxyContainer.transferPlayer(player, transferDestiPacket.getServerName());
                break;
            default:
                this.logger.info("Unknown message type: " + messageType);
        }

    }
}
