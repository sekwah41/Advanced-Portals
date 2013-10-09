package com.sekwah.advancedportals;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DestinationCommand implements CommandExecutor {
	
	private AdvancedPortalsPlugin plugin;
	
	public DestinationCommand(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("destination").setExecutor(this);
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		
		
		return true;
	}


}
