package com.sekwah.advancedportals.compat;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class v1_7_R4 implements NMS {
	
	@Override
	public void sendRawMessage(String rawMessage, Player player) {
		IChatBaseComponent comp = ChatSerializer.a(rawMessage);
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public void sendActionBarMessage(String rawMessage, Player player) {
		IChatBaseComponent comp = ChatSerializer.a(" " + rawMessage);
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
