package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PluginMessages {
    private static String WARP_MESSAGE;
    private static String COOLDOWN_PROTECTION_MESSAGE;
    private static String PORTAL_COOLDOWN_MESSAGE;
    private static String NO_PERMISSION_PORTAL;
    public boolean useCustomPrefix = false;
    public static String customPrefix = "\u00A7a[\u00A7eAdvancedPortals\u00A7a]";
    public static String customPrefixFail = "\u00A7c[\u00A77AdvancedPortals\u00A7c]";

    public PluginMessages (AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "messages.yml");
        this.useCustomPrefix = config.getConfig().getBoolean("UseCustomPrefix");
        if (useCustomPrefix) {
            PluginMessages.customPrefix = config.getConfig().getString("CustomPrefix").replaceAll("&(?=[0-9a-fk-or])", "\u00A7");
            PluginMessages.customPrefixFail = config.getConfig().getString("CustomPrefixFail").replaceAll("&(?=[0-9a-fk-or])", "\u00A7");
        }

        WARP_MESSAGE = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("WarpMessage", "&aYou have warped to &e<warp>&a"));
        COOLDOWN_PROTECTION_MESSAGE = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("CooldownMessage", "&cThere is &e<time>&c join cooldown protection left."));
        NO_PERMISSION_PORTAL = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("NoPermissionPortal", "&cYou do not have permission to use this portal!"));
        PORTAL_COOLDOWN_MESSAGE = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("PortalCooldownMessage", "&cPlease wait &e<time> &cuntil attempting to enter this portal again"));
    }

    // This class is so then the common messages in commands or just messages over the commands are the same and can be
    //  easily changed.

    public static String getWarpMessage(String warp) {
        String cleanedWarp = warp.replace("_", " ");
        return WARP_MESSAGE.replace("<warp>", cleanedWarp);
    }

    public static String getCooldownProtectionMessage(int time) {
        String secondsOrSecond = (time == 1) ? " second" : " seconds";
        return COOLDOWN_PROTECTION_MESSAGE.replace("<time>", time + secondsOrSecond);
    }

    public static String getPortalCooldownMessage(int time) {
        String secondsOrSecond = (time == 1) ? " second" : " seconds";
        return COOLDOWN_PROTECTION_MESSAGE.replace("<time>", time + secondsOrSecond);
    }

    public static String getNoPermissionPortal() {
        return NO_PERMISSION_PORTAL;
    }

    public static void UnknownCommand(CommandSender sender, String command) {
        sender.sendMessage(customPrefixFail + " You need to type something after /" + command + "\n");
        sender.sendMessage("\u00A7cIf you do not know what you can put or would like some help with the commands please type \u00A7e" + '"' + "\u00A7e/" + command + " help" + '"' + "\u00A7c\n");
    }

    public static void NoPermission(CommandSender sender, String command) {
        sender.sendMessage(customPrefixFail + " You do not have permission to perform that command!");
    }
}
