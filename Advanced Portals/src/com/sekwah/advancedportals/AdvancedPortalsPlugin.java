package com.sekwah.advancedportals;

import org.bukkit.plugin.java.JavaPlugin;

import com.sekwah.advancedportals.portalcontrolls.Portal;

public class AdvancedPortalsPlugin extends JavaPlugin {
	
	public void onEnable() {
		
		// Opens a channel that messages bungeeCord
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		// thanks to the new config accessor code the config.saveDefaultConfig(); will now
		//  only copy the file if it doesnt exist!
        ConfigAccessor config = new ConfigAccessor(this, "Config.yml");
        config.saveDefaultConfig();
		
        
        // Loads the portal and destination editors
        new Portal(this);
        
        // These register the commands
		new AdvancedPortalsCommand(this);
		new DestinationCommand(this);
		
		// These register the listeners
		new Listeners(this);
		
		Selection.LoadData(this);
		
		this.getServer().getConsoleSender().sendMessage("§aAdvanced portals have been sucsessfully enabled!");
		
	}
	
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("§cAdvanced portals are being disabled!");
	}
	
	
}
