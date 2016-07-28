package com.sekwah.advancedportals.compat.bukkit;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class v1_10_R1 implements NMS {

    @Override
    public void sendRawMessage(String rawMessage, Player player) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(rawMessage);
        // "json message", position(0: chat (chat box), 1: system message (chat box), 2: above action bar)
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


    @Override
    public void sendActionBarMessage(String rawMessage, Player player) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(rawMessage);
        // "json message", position(0: chat (chat box), 1: system message (chat box), 2: above action bar)
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
