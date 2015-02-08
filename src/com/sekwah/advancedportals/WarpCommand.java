package com.sekwah.advancedportals;

import com.sekwah.advancedportals.destinations.Destination;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

	@SuppressWarnings("unused")
	private AdvancedPortalsPlugin plugin;
	
	public WarpCommand(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("warp").setExecutor(this);
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if(args.length > 0){
			if(Destination.warp(sender, args[0], false)){
				sender.sendMessage("");
				sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] You have been warped to \u00A7e" + args[0] + "\u00A7a.");
				sender.sendMessage("");
			}
			else{
				sender.sendMessage("");
				sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The destination you tried to warp to does not exist!");
				sender.sendMessage("");
			}
		}
		else{
			sender.sendMessage("");
			sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to type a destination after /" + command + "!");
			sender.sendMessage("");
		}
		return true;
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
		LinkedList<String> autoComplete = new LinkedList<String>();
		
		/**if(sender.hasPermission("AdvancedPortals.CreatePortal")){
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
		}*/
		return autoComplete;
	}


}
