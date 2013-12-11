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
		if(args.length > 0){
			if(args[0].toLowerCase().equals("create")){
				if(sender.hasPermission("AdvancedPortals.create")){
					if(args.length > 1){
						ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
						String posX = config.getConfig().getString(args[1].toLowerCase() + ".pos.X");
						if(posX == null){
							
						}
						else{
							sender.sendMessage("§c[§7AdvancedPortals§c] A destination by that name already exists!!");
						}
					}
					else{
						sender.sendMessage("§c[§7AdvancedPortals§c] Please state the name of the destination you would like to create!");
					}
				}
				else{
					sender.sendMessage("§c[§7AdvancedPortals§c] You do not have permission to create portals so you cannot give yourself a §ePortal Region Selector§c!");
				}
			}
		}
		else{
			sender.sendMessage("§c[§7AdvancedPortals§c] You need to type something after /" + command + "\n"
					+ "if you do not know what you can put or would like some help with the commands please type /" + command + " help");
		}
		return true;
	}


}
