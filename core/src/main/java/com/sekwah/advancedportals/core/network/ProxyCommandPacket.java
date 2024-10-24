package com.sekwah.advancedportals.core.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.sekwah.advancedportals.core.ProxyMessages;

public class ProxyCommandPacket {

    private final String command;

    public ProxyCommandPacket(String command) {
        this.command = command;
    }

    public static void encode(ProxyCommandPacket msg, ByteArrayDataOutput buffer) {
        buffer.writeUTF(ProxyMessages.PROXY_COMMAND);
        buffer.writeUTF(msg.command);
    }

    public static ProxyCommandPacket decode(ByteArrayDataInput buffer) {
        return new ProxyCommandPacket(buffer.readUTF());
    }

    public String getCommand() {
        return command;
    }
}
