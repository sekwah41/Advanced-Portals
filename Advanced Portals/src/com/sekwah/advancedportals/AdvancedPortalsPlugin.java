package com.sekwah.advancedportals;

import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {
	
	public void onEnable() {
		
		// thanks to the new config accessor code the config.saveDefaultConfig(); will now
		//  only copy the file if it doesnt exist!
        ConfigAccessor config = new ConfigAccessor(this, "Config.yml");
        config.saveDefaultConfig();
		
        
        // These register the commands
		new AdvancedPortalsCommand(this);
		new DestinationCommand(this);
		
		// These register the listeners
		new Listeners(this);
		
		this.getServer().getConsoleSender().sendMessage("§aAdvanced portals have been sucsessfully enabled!");
		
	}
	
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("§cAdvanced portals are being disabled!");
	}
	
	
}
