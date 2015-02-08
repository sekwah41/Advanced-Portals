package com.sekwah.advancedportals.compat;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import org.bukkit.entity.Player;

public interface NMS {
	
	public void sendRawMessage(String rawMessage, Player player);

	public void sendActionBarMessage(String rawMessage, Player player);
}
