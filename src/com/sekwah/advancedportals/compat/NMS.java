package com.sekwah.advancedportals.compat;

import org.bukkit.entity.Player;

public interface NMS {
	
	public void sendRawMessage(String rawMessage, Player player);

	public void sendActionBarMessage(String rawMessage, Player player);
}
