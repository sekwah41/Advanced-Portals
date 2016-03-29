package com.sekwah.advancedportals;

import com.sekwah.advancedportals.destinations.Destination;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    @SuppressWarnings("unused")
    private AdvancedPortalsPlugin plugin;

    public WarpCommand(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;


        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        boolean useWarpCommand = !config.getConfig().getBoolean("DisableWarpCommand");
        //plugin.getCommand("warp").setExecutor(this);
        plugin.getCommand("awarp").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (args.length > 0) {
            if (Destination.warp(sender, args[0], false)) {
                if (DestinationCommand.PortalMessagesDisplay == 1) {
                    sender.sendMessage("");
                    sender.sendMessage(plugin.customPrefixFail + "\u00A7a You have been warped to \u00A7e" + args[0].replaceAll("_", " ") + "\u00A7a.");
                    sender.sendMessage("");
                } else if (DestinationCommand.PortalMessagesDisplay == 2) {
                    ConfigAccessor config = new ConfigAccessor(plugin, "destinations.yml");
                    plugin.nmsAccess.sendActionBarMessage("{\"text\":\"\u00A7aYou have warped to \u00A7e" + args[0].replaceAll("_", " ") + "\u00A7a.\"}", (Player) sender);
                    /**plugin.nmsAccess.sendActionBarMessage("[{text:\"You have warped to \",color:green},{text:\"" + config.getConfig().getString(Portal.Portals[portalId].portalName + ".destination").replaceAll("_", " ")
                     + "\",color:yellow},{\"text\":\".\",color:green}]", player);*/
                }
            } else {
                sender.sendMessage("");
                sender.sendMessage(plugin.customPrefixFail + "\u00A7c The destination you tried to warp to does not exist!");
                sender.sendMessage("");
            }
        } else {
            sender.sendMessage("");
            sender.sendMessage(plugin.customPrefixFail + "\u00A7c You need to type a destination after /" + command + "!");
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
