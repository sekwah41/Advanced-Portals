package com.sekwah.advancedportals.bukkit.destinations;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.PluginMessages;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DestinationCommand implements CommandExecutor, TabCompleter {

    private final AdvancedPortalsPlugin plugin;

    public DestinationCommand(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("destination").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(PluginMessages.customPrefixFail + " You cannot use commands with the console.");
            return true;
        }
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
        if (args.length > 0) { switch (args[0].toLowerCase()) {
            case "create":
                if (sender.hasPermission("advancedportals.desti")) {
                    if (args.length > 1) {
                        String posX = config.getConfig().getString(args[1].toLowerCase() + ".pos.X");
                        if (posX == null) {
                            sender.sendMessage(PluginMessages.customPrefix + " You have created a new destination called \u00A7e" + args[1] + "!");
                            Player player = (Player) sender;
                            Destination.create(player.getLocation(), args[1]);
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail + " A destination by that name already exists!");
                        }
                    } else {
                        sender.sendMessage(PluginMessages.customPrefixFail + " Please state the name of the destination you would like to create!");
                    }
                } else {
                    sender.sendMessage(PluginMessages.customPrefixFail + " You do not have permission to create destinations!");
                }
                break;
            case "remove":
                ConfigAccessor portalConfig = new ConfigAccessor(plugin, "destinations.yml");
                if (sender.hasPermission("advancedportals.desti")) {
                    if (args.length > 1) {
                        String posX = portalConfig.getConfig().getString(args[1] + ".pos.X");
                        if (posX != null) {
                            Destination.remove(args[1]);
                            sender.sendMessage(PluginMessages.customPrefixFail + " The destination \u00A7e" + args[1] + "\u00A7c has been removed!");
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail + " No destination by that name exists.");
                        }
                    } else {
                        sender.sendMessage(PluginMessages.customPrefixFail + " You need to state the name of the destination you wish to remove.");
                    }
                } else {
                    sender.sendMessage(PluginMessages.customPrefixFail + " You do not have permission to remove destinations!");
                }
                break;
            case "list":
                String message = PluginMessages.customPrefix + " \u00A77Destinations \u00A7c:\u00A7a";
                Object[] destiObj = config.getConfig().getKeys(false).toArray();
                LinkedList<String> destis = new LinkedList<>();
                for (Object object : destiObj) {
                    destis.add(object.toString());
                }
                Collections.sort(destis);
                for (Object desti : destis.toArray()) message = message + " " + desti;
                sender.sendMessage(message);
                break;
            case "help":
                sender.sendMessage(PluginMessages.customPrefix + " Destination Help Menu");
                sender.sendMessage("\u00A7e\u00A7m----------------------------");
                sender.sendMessage("\u00A76/" + command + " create \u00A7c[name] \u00A7a- create destination at your location");
                sender.sendMessage("\u00A76/" + command + " remove \u00A7c[name] \u00A7a- remove destination");
                sender.sendMessage("\u00A76/" + command + " warp \u00A7c[name] \u00A7a- teleport to destination");
                sender.sendMessage("\u00A76/" + command + " list \u00A7a- list all destinations");
                sender.sendMessage("\u00A7e\u00A7m----------------------------");
                break;
            case "warp":
                if (!(sender.hasPermission("advancedportals.warp.*") || sender.hasPermission("advancedportals.warp." + args[1]))) {
                    sender.sendMessage(PluginMessages.customPrefixFail + " You don't have permission to warp to " + args[1] + "!");
                    return true;
                }
                if(args.length >= 2){
                    Destination.warp((Player) sender, args[1]);
                }
                else{
                    sender.sendMessage(PluginMessages.customPrefixFail + " You must specify a warp location!");
                }
                break;
            default:
                PluginMessages.UnknownCommand(sender, command);
                break;
        }
        } else {
            PluginMessages.UnknownCommand(sender, command);
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
        LinkedList<String> autoComplete = new LinkedList<>();
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
        if(args.length > 1 && args[0].equalsIgnoreCase("warp")){
            for (String string : config.getConfig().getKeys(false)) {
                if (sender.hasPermission("advancedportals.desti.*") | sender.hasPermission("advancedportals.desti." + string))
                    autoComplete.add(string);
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission("advancedportals.desti") | sender.hasPermission("advancedportals.createportal")) {
                autoComplete.addAll(Arrays.asList("create", "remove", "help"));
            }
            autoComplete.add("warp");
        }
        Collections.sort(autoComplete);
        for (Object result : autoComplete.toArray()) {
            if (!result.toString().startsWith(args[args.length - 1])) {
                autoComplete.remove(result);
            }
        }
        return autoComplete;
    }


}
