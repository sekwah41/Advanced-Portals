package com.sekwah.advancedportals;

import org.bukkit.command.CommandSender;

public class PluginMessages {
	
	// This class is so then the common messages in commands or just messages over the commands are the same and can be
	//  easily changed.
	
	public static void UnknownCommand(CommandSender sender, String command) {
		sender.sendMessage("§c[§7AdvancedPortals§c] You need to type something after /" + command + "\n"
				+ "if you do not know what you can put or would like some help with the commands please type " + '"' + "/" + command + " help" + '"' + " or you can check out the Advanced Portals wiki"
						+ "\n ");
	}

	public static void NoPermission(CommandSender sender, String command) {
		sender.sendMessage("§c[§7AdvancedPortals§c] You do not have permission to perform that command!");
	}

}
