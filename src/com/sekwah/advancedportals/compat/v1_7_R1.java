package com.sekwah.advancedportals.compat;

import net.minecraft.server.v1_7_R1.ChatSerializer;
import net.minecraft.server.v1_7_R1.IChatBaseComponent;
import net.minecraft.server.v1_7_R1.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_7_R1 implements NMS {
	
	@Override
	public void sendRawMessage(String rawMessage, Player player) {
		IChatBaseComponent comp = ChatSerializer.a(rawMessage);
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
}
