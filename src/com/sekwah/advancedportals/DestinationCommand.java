package com.sekwah.advancedportals;

import com.sekwah.advancedportals.destinations.Destination;
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

    public static int PortalMessagesDisplay = 0;

    private AdvancedPortalsPlugin plugin;

    public DestinationCommand(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("destination").setExecutor(this);

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");

        PortalMessagesDisplay = config.getConfig().getInt("WarpMessageDisplay");


    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (args.length > 0) {
            if (args[0].toLowerCase().equals("create")) {
                if (sender.hasPermission("advancedportals.desti.create")) {
                    if (args.length > 1) {
                        ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
                        String posX = config.getConfig().getString(args[1].toLowerCase() + ".pos.X");
                        if (posX == null) {
                            sender.sendMessage("§a[\u00A7eAdvancedPortals\u00A7a] You have created a new destination called \u00A7e" + args[1] + "!");
                            Player player = sender.getServer().getPlayer(sender.getName());
                            Destination.create(player.getLocation(), args[1]);
                        } else {
                            sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] A destination by that name already exists!");
                        }
                    } else {
                        sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] Please state the name of the destination you would like to create!");
                    }
                } else {
                    sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You do not have permission to create portals so you cannot give yourself a \u00A7ePortal Region Selector\u00A7c!");
                }
            } else if (args[0].toLowerCase().equals("remove")) {
                ConfigAccessor portalConfig = new ConfigAccessor(plugin, "destinations.yml");
                if (args.length > 1) {
                    String posX = portalConfig.getConfig().getString(args[1] + ".pos.X");
                    if (posX != null) {
                        Destination.remove(args[1]);
                        sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The destination \u00A7e" + args[1] + "\u00A7c has been removed!");
                    } else {
                        sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No destination by that name exists.");
                    }
                } else {
                    sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to state the name of the destination you wish to remove.");
                }
            } else if (args[0].toLowerCase().equals("goto") || args[0].toLowerCase().equals("warp")) {
                if (args.length > 1) {
                    //System.out.println(args[1]);
                    ConfigAccessor configDesti = new ConfigAccessor(plugin, "destinations.yml");
                    if (configDesti.getConfig().getString(args[1] + ".world") != null) {
                        Destination.warp(sender, args[1]);
                        if (PortalMessagesDisplay == 1) {
                            sender.sendMessage("");
                            sender.sendMessage(plugin.customPrefixFail + "\u00A7a You have been warped to \u00A7e" + args[1].replaceAll("_", " ") + "\u00A7a.");
                            sender.sendMessage("");
                        } else if (PortalMessagesDisplay == 2) {
                            ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
                            plugin.compat.sendActionBarMessage("{\"text\":\"\u00A7aYou have warped to \u00A7e" + args[1].replaceAll("_", " ") + "\u00A7a.\"}", (Player) sender);
                            /**plugin.nmsAccess.sendActionBarMessage("[{text:\"You have warped to \",color:green},{text:\"" + config.getConfig().getString(Portal.Portals[portalId].portalName + ".destination").replaceAll("_", " ")
                             + "\",color:yellow},{\"text\":\".\",color:green}]", player);*/
                        }
                    } else {
                        sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No destination by that name exists.");
                    }
                } else {
                    sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to state the name of the destination you wish to exemptPlayer to.");
                }
            } else if (args[0].toLowerCase().equals("list")) {
                List<String> destiList = Destination.destiList();
                if (destiList.size() >= 1) {
                    if (args.length > 1) {
                        try {
                            int page = Integer.parseInt(args[1]);
                            if (page * 5 >= destiList.size() - 5) { // add this if statement so that the user cant select a list page higher than the max
                                if (destiList.size() / 5 == destiList.size()) {

                                }
                            }
                            sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Showing destinations page 1 of 1");
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

                    sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Showing destinations page 1 of 1");
                    for (int i = 0; i < 5; i++) {
                        if (i > destiList.size()) {
                            break;
                        }
                        sender.sendMessage(" \u00A7e" + destiList.get(i));
                    }

                    sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Showing destinations page 1 of 1");
                    for (int i = 0; i < 5; i++) {
                        if (i > destiList.size()) {
                            break;
                        }
                        sender.sendMessage(" \u00A7e" + destiList.get(i));
                    }
                } else {
                    sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] There are currently no defined destinations.");
                }
            }
        } else {
            sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to type something after /" + command + ", if you do not know what you can put or would like some help with the commands please type /" + command + " help");
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
