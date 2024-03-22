package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.regex.qual.Regex;

public class PluginMessages {
    private static String WARP_MESSAGE;
    private static String COOLDOWN_PROTECTION_MESSAGE;
    private static String PORTAL_COOLDOWN_MESSAGE;
    private static String NO_PERMISSION_PORTAL;
    private static String NO_BUILD_PERMISSION;
    public boolean useCustomPrefix = false;
    public static String customPrefix = "§a[§eAdvancedPortals§a]";
    public static String customPrefixFail = "§c[§7AdvancedPortals§c]";

    // TODO: Create function for replacing all '&'
    public PluginMessages (AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "messages.yml");

        this.useCustomPrefix = config.getConfig().getBoolean("UseCustomPrefix");
        String regexColorConverter = "&(?=[0-9a-fk-orx])";

        if (useCustomPrefix) {
            PluginMessages.customPrefix = config.getConfig().getString("CustomPrefix", "§a[§eAdvancedPortals§a]").replaceAll(regexColorConverter, "§");
            PluginMessages.customPrefixFail = config.getConfig().getString("CustomPrefixFail", "§c[§7AdvancedPortals§c]").replaceAll(regexColorConverter, "§");
        }

        WARP_MESSAGE = config.getConfig().getString("WarpMessage", "§aYou have warped to §e<warp>§a").replaceAll(regexColorConverter, "§");
        COOLDOWN_PROTECTION_MESSAGE = config.getConfig().getString("CooldownMessage", "§cThere is §e<time>§c join cooldown protection left.").replaceAll(regexColorConverter, "§");
        NO_PERMISSION_PORTAL = config.getConfig().getString("NoPermissionPortal", "§cYou do not have permission to use this portal!").replaceAll(regexColorConverter, "§");
        PORTAL_COOLDOWN_MESSAGE = config.getConfig().getString("PortalCooldownMessage", "§cPlease wait §e<time> §cuntil attempting to enter this portal again").replaceAll(regexColorConverter, "§");
        NO_BUILD_PERMISSION = config.getConfig().getString("NoBuildPermission", "§cYou don't have permission to build here!").replaceAll(regexColorConverter, "§");
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

    public static String getNoPermissionPortal() {
        return NO_PERMISSION_PORTAL;
    }

    public static String getNoBuildPermission() {
        return NO_BUILD_PERMISSION;
    }

    public static void UnknownCommand(CommandSender sender, String command) {
        sender.sendMessage(customPrefixFail + " You need to type something after /" + command + "\n");
        sender.sendMessage("§cIf you do not know what you can put or would like some help with the commands please type §e" + '"' + "§e/" + command + " help" + '"' + "§c\n");
    }

    public static void NoPermission(CommandSender sender, String command) {
        sender.sendMessage(customPrefixFail + " You do not have permission to perform that command!");
    }
}
