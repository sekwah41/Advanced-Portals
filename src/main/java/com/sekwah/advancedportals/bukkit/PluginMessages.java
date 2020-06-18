package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import org.bukkit.command.CommandSender;

public class PluginMessages {
    private AdvancedPortalsPlugin plugin;
    public boolean useCustomPrefix = false;
    public static String customPrefix = "\u00A7a[\u00A7eAdvancedPortals\u00A7a]";
    public static String customPrefixFail = "\u00A7c[\u00A77AdvancedPortals\u00A7c]";

    public PluginMessages (AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        ConfigAccessor config = new ConfigAccessor(this.plugin, "config.yml");
        this.useCustomPrefix = config.getConfig().getBoolean("UseCustomPrefix");
        if (useCustomPrefix) {
            PluginMessages.customPrefix = config.getConfig().getString("CustomPrefix").replaceAll("&(?=[0-9a-fk-or])", "\u00A7");
            PluginMessages.customPrefixFail = config.getConfig().getString("CustomPrefixFail").replaceAll("&(?=[0-9a-fk-or])", "\u00A7");
        }
    }

    // This class is so then the common messages in commands or just messages over the commands are the same and can be
    //  easily changed.

    public static void UnknownCommand(CommandSender sender, String command) {
        sender.sendMessage(customPrefixFail + " You need to type something after /" + command + "\n");
        sender.sendMessage("\u00A7cIf you do not know what you can put or would like some help with the commands please type \u00A7e" + '"' + "\u00A7e/" + command + " help" + '"' + "\u00A7c\n");
    }

    public static void NoPermission(CommandSender sender, String command) {
        sender.sendMessage(customPrefixFail + " You do not have permission to perform that command!");
    }
}
