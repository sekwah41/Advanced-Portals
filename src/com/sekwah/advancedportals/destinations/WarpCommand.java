package com.sekwah.advancedportals.destinations;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.PluginMessages;
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
            Destination.warp((Player) sender, args[0]);
        } else {
            PluginMessages.UnknownCommand(sender, command);
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
