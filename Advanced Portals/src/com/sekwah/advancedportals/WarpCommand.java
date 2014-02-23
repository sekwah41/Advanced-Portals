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

public class WarpCommand implements CommandExecutor, TabCompleter {
	
	private AdvancedPortalsPlugin plugin;
	
	public WarpCommand(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("warp").setExecutor(this);
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if(args.length > 0){
			if(Destination.warp(sender, args[0], false)){
				sender.sendMessage("§a[§eAdvancedPortals§a] You have been warped to §e" + args[0] + ".");
			}
			else{
				sender.sendMessage("§c[§7AdvancedPortals§c] The destination you tried to warp to does not exist!");
			}
		}
		else{
			sender.sendMessage("§c[§7AdvancedPortals§c] You need to type a destination after /" + command + "!");
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
