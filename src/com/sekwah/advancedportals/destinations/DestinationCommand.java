package com.sekwah.advancedportals.destinations;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.PluginMessages;
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

    private AdvancedPortalsPlugin plugin;

    public DestinationCommand(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("destination").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
        if (args.length > 0) { switch (args[0].toLowerCase()) {
            case "create":
                if (sender.hasPermission("advancedportals.desti.create")) {
                    if (args.length > 1) {
                        String posX = config.getConfig().getString(args[1].toLowerCase() + ".pos.X");
                        if (posX == null) {
                            sender.sendMessage(PluginMessages.customPrefix + " You have created a new destination called \u00A7e" + args[1] + "!");
                            Player player = sender.getServer().getPlayer(sender.getName());
                            Destination.create(player.getLocation(), args[1]);
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail + " A destination by that name already exists!");
                        }
                    } else {
                        sender.sendMessage(PluginMessages.customPrefixFail + " Please state the name of the destination you would like to create!");
                    }
                } else {
                    sender.sendMessage(PluginMessages.customPrefixFail + " You do not have permission to create portals so you cannot give yourself a \u00A7ePortal Region Selector\u00A7c!");
                }
                break;
            case "remove":
                ConfigAccessor portalConfig = new ConfigAccessor(plugin, "destinations.yml");
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
                break;
            case "goto":
            case "warp":
                if (args.length > 1) {
                    Destination.warp((Player) sender, args[1]);
                } else {
                    sender.sendMessage(PluginMessages.customPrefixFail + " You need to state the name of the destination you wish to warp to.");
                }
                break;
            case "list":
                List<String> destiList = Destination.destiList();
                if (destiList.size() >= 1) {
                    if (args.length > 1) {
                        try {
                            int page = Integer.parseInt(args[1]);
                            if (page * 5 >= destiList.size() - 5) { // add this if statement so that the user cant select a list page higher than the max
                                if (destiList.size() / 5 == destiList.size()) {

                                }
                            }
                            sender.sendMessage(PluginMessages.customPrefix + " Showing destinations page 1 of 1");
                            for (int i = (page - 1) * 5; i < page * 5; i++) {
                                if (i > destiList.size()) {
                                    break;
                                }
                                sender.sendMessage(" \u00A7e" + destiList.get(i));
                            }
                            return true;
                        } catch (Exception e) {
                        }
                    }

                    sender.sendMessage(PluginMessages.customPrefix + " Showing destinations page 1 of 1");
                    for (int i = 0; i < 5; i++) {
                        if (i > destiList.size()) {
                            break;
                        }
                        sender.sendMessage(" \u00A7e" + destiList.get(i));
                    }

                    sender.sendMessage(PluginMessages.customPrefix + " Showing destinations page 1 of 1");
                    for (int i = 0; i < 5; i++) {
                        if (i > destiList.size()) {
                            break;
                        }
                        sender.sendMessage(" \u00A7e" + destiList.get(i));
                    }
                } else {
                    sender.sendMessage(PluginMessages.customPrefixFail + " There are currently no defined destinations.");
                }
                break;
            }
        } else {
            PluginMessages.UnknownCommand(sender, command);
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
        LinkedList<String> autoComplete = new LinkedList<String>();

        if (sender.hasPermission("AdvancedPortals.CreatePortal")) {
            if (args.length == 1) {
                autoComplete.addAll(Arrays.asList("create", "goto", "redefine", "move", "rename", "remove"));
            } else if (args[0].toLowerCase().equals("create")) {
            }
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
