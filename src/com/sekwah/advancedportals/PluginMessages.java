package com.sekwah.advancedportals;

import org.bukkit.command.CommandSender;

public class PluginMessages {
	
	// This class is so then the common messages in commands or just messages over the commands are the same and can be
	//  easily changed.
	
	public static void UnknownCommand(CommandSender sender, String command) {
		sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to type something after /" + command + "\n");
		sender.sendMessage("\u00A7cIf you do not know what you can put or would like some help with the commands please type \u00A7e" + '"' + "\u00A7e/" + command + " help" + '"' + "\u00A7c\n");
	}

	public static void NoPermission(CommandSender sender, String command) {
		sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You do not have permission to perform that command!");
	}

}
