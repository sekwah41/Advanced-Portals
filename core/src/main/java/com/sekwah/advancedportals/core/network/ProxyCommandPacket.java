package com.sekwah.advancedportals.core.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.ProxyMessages;

public class ProxyCommandPacket implements Packet {

    private final String command;

    public ProxyCommandPacket(String command) {
        this.command = command;
    }

    public byte[] encode() {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeUTF(ProxyMessages.PROXY_COMMAND);
        buffer.writeUTF(this.command);
        return buffer.toByteArray();
    }

    public static ProxyCommandPacket decode(ByteArrayDataInput buffer) {
        return new ProxyCommandPacket(buffer.readUTF());
    }

    public String getCommand() {
        return command;
    }
}
