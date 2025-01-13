package com.sekwah.advancedportals.proxycore;

import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.core.network.ProxyCommandPacket;
import com.sekwah.advancedportals.core.network.ProxyTransferDestiPacket;
import com.sekwah.advancedportals.core.network.ProxyTransferPacket;
import com.sekwah.advancedportals.core.network.ServerDestiPacket;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyJoinData;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyPlayerContainer;
import com.sekwah.advancedportals.proxycore.connector.container.ProxyServerContainer;
import com.sekwah.advancedportals.shadowed.guava.io.ByteArrayDataInput;
import com.sekwah.advancedportals.shadowed.guava.io.ByteStreams;
import java.util.HashMap;

public class AdvancedPortalsProxyCore {
    private final InfoLogger logger;
    private final ProxyContainer proxyContainer;

    public HashMap<String, ProxyJoinData> playerJoinMap = new HashMap<>();

    public AdvancedPortalsProxyCore(InfoLogger logger,
                                    ProxyContainer proxyContainer) {
        this.logger = logger;
        this.proxyContainer = proxyContainer;
    }

    public void onEnable() {
        this.logger.info(Lang.convertColors("&aSuccessfully enabled!"));
    }

    public void onDisable() {
        this.logger.info(Lang.convertColors("&cDisabling plugin!"));
    }

    public void onServerConnect(ProxyServerContainer server,
                                ProxyPlayerContainer player) {
        if (this.playerJoinMap.containsKey(player.getUUID())) {
            ProxyJoinData joinData = this.playerJoinMap.get(player.getUUID());
            if (joinData.isExpired())
                return;
            player.sendServerPluginMessage(
                new ServerDestiPacket(joinData.destination).encode());
            this.playerJoinMap.remove(player.getUUID());
        }
    }

    public void onPlayerDisconnect(ProxyPlayerContainer player) {
        this.playerJoinMap.remove(player.getUUID());
    }

    /**
     * Messages coming on the ProxyMessages.CHANNEL_NAME from the server, others
     * will be filtered out before now.
     * @param player
     * @param message
     */
    public void incomingMessage(ProxyPlayerContainer player, byte[] message) {
        ByteArrayDataInput buffer = ByteStreams.newDataInput(message);
        String messageType = buffer.readUTF();

        // Might be a bit overboard for some as they'll only have one value, but
        // try to keep the decode behavior with the encode behavior in the
        // packets
        switch (messageType) {
            case ProxyMessages.PROXY_TRANSFER:
                ProxyTransferPacket transferPacket = ProxyTransferPacket.decode(buffer);
                this.logger.info("Transfer request for " + player.getName() + " to " + transferPacket.getServerName());
                this.proxyContainer.transferPlayer(player, transferPacket.getServerName());
                break;
            case ProxyMessages.PROXY_COMMAND:
                ProxyCommandPacket commandPacket = ProxyCommandPacket.decode(buffer);
                this.logger.info("Command request for " + player.getName() + " to run /" + commandPacket.getCommand());
                this.proxyContainer.invokeCommand(player, commandPacket.getCommand());
                break;
            case ProxyMessages.PROXY_TRANSFER_DESTI:
                ProxyTransferDestiPacket transferDestiPacket = ProxyTransferDestiPacket.decode(buffer);
                this.proxyContainer.transferPlayer(player, transferDestiPacket.getServerName());
                this.playerJoinMap.put(player.getUUID(), new ProxyJoinData(transferDestiPacket.getDestination(), transferDestiPacket.getServerName()));
                break;
            default:
                this.logger.info("Unknown message type: " + messageType);
                break;
        }

    }
}
