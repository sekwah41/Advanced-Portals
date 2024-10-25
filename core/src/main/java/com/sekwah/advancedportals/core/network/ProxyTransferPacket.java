package com.sekwah.advancedportals.core.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.ProxyMessages;

public class ProxyTransferPacket {

    private final String serverName;

    public ProxyTransferPacket(String serverName) {
        this.serverName = serverName;
    }

    public byte[] encode() {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeUTF(ProxyMessages.PROXY_TRANSFER);
        buffer.writeUTF(this.serverName);
        return buffer.toByteArray();

    }

    public static ProxyTransferPacket decode(ByteArrayDataInput buffer) {
        return new ProxyTransferPacket(buffer.readUTF());
    }

    public String getServerName() {
        return serverName;
    }
}
