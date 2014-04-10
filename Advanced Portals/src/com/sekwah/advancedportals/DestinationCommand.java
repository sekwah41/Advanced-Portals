package com.sekwah.advancedportals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.destinations.Destination;
import com.sekwah.advancedportals.portalcontrolls.Portal;

public class DestinationCommand implements CommandExecutor, TabCompleter {
	
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
							sender.sendMessage("§a[§eAdvancedPortals§a] You have created a new destination called " + args[1] +  "!");
							Player player = sender.getServer().getPlayer(sender.getName());
							Destination.create(player.getLocation(), args[1]);
						}
						else{
							sender.sendMessage("§c[§7AdvancedPortals§c] A destination by that name already exists!");
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
			else if(args[0].toLowerCase().equals("remove")) {
				ConfigAccessor portalConfig = new ConfigAccessor(plugin, "Destinations.yml");
				if(args.length > 1){
					String posX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
					if(posX != null){
						Destination.remove(args[1]);
						sender.sendMessage("§c[§7AdvancedPortals§c] The portal §e" + args[1] + "§c has been removed!");
					}
					else{
						sender.sendMessage("§c[§7AdvancedPortals§c] No portal by that name exists!");
					}
				}
				else{
					sender.sendMessage("§c[§7AdvancedPortals§c] You need to state the name of the destination you wish to remove!");
				}
			}
		}
		else{
			sender.sendMessage("§c[§7AdvancedPortals§c] You need to type something after /" + command + "\n"
					+ "if you do not know what you can put or would like some help with the commands please type /" + command + " help");
		}
		return true;
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
		LinkedList<String> autoComplete = new LinkedList<String>();
		
		if(sender.hasPermission("AdvancedPortals.CreatePortal")){
			if(args.length == 1){
				autoComplete.addAll(Arrays.asList("create", "goto", "redefine", "move", "rename", "remove"));
			}
			else if(args[0].toLowerCase().equals("create")){
			}
		}
		Collections.sort(autoComplete);
		for(Object result: autoComplete.toArray()){
			if(!result.toString().startsWith(args[args.length - 1])){
				autoComplete.remove(result);
			}
		}
		return autoComplete;
	}


}
