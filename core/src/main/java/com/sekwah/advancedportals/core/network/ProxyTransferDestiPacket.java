package com.sekwah.advancedportals.core.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.ProxyMessages;

public class ProxyTransferDestiPacket implements Packet {

    private final String serverName;
    private final String destination;

    public ProxyTransferDestiPacket(String serverName, String destination) {
        this.serverName = serverName;
        this.destination = destination;
    }

    public byte[] encode() {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeUTF(ProxyMessages.PROXY_TRANSFER_DESTI);
        buffer.writeUTF(this.serverName);
        buffer.writeUTF(this.destination);
        return buffer.toByteArray();

    }

    public static ProxyTransferDestiPacket decode(ByteArrayDataInput buffer) {
        return new ProxyTransferDestiPacket(buffer.readUTF(), buffer.readUTF());
    }

    public String getServerName() {
        return serverName;
    }

    public String getDestination() {
        return destination;
    }
}
