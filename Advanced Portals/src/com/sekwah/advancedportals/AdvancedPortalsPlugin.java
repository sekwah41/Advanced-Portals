package com.sekwah.advancedportals;

import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {
	
	public void onEnable() {
		
		new Listeners(this);
		this.getServer().getConsoleSender().sendMessage("§aAdvanced portals have been sucsessfully enabled!");
	}
	
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("§cAdvanced portals are being disabled!");
	}
	
	
}
