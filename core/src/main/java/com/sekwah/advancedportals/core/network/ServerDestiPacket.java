package com.sekwah.advancedportals.core.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.ProxyMessages;

public class ServerDestiPacket implements Packet {
    private final String destination;

    public ServerDestiPacket(String destination) {
        this.destination = destination;
    }

    public byte[] encode() {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeUTF(ProxyMessages.SERVER_DESTI);
        buffer.writeUTF(this.destination);
        return buffer.toByteArray();
    }

    public static ServerDestiPacket decode(ByteArrayDataInput buffer) {
        return new ServerDestiPacket(buffer.readUTF());
    }

    public String getDestination() {
        return destination;
    }
}
