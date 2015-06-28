package com.sekwah.advancedportals.compat.bukkit;

import org.bukkit.entity.Player;

public interface NMS {
	
	void sendRawMessage(String rawMessage, Player player);

	void sendActionBarMessage(String rawMessage, Player player);
}
