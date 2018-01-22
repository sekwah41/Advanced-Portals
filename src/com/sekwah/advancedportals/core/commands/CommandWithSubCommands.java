package com.sekwah.advancedportals.core.commands;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.registry.SubCommandRegistry;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;

import java.util.ArrayList;
import java.util.List;

public class CommandWithSubCommands implements CommandTemplate {

    private final SubCommandRegistry subCommandRegistry;

    public CommandWithSubCommands() {
        this.subCommandRegistry = new SubCommandRegistry();
    }

    public boolean registerSubCommand(String arg, SubCommand subCommand) {
        return this.subCommandRegistry.registerSubCommand(arg,subCommand);
    }

    public ArrayList<String> getSubCommands(){
        return this.subCommandRegistry.getSubCommands();
    }

    public boolean isArgRegistered(String arg){
        return this.subCommandRegistry.isArgRegistered(arg);
    }

    public SubCommand getSubCommand(String arg){
        return this.subCommandRegistry.getSubCommand(arg);
    }


    @Override
    public void onCommand(CommandSenderContainer sender, String commandExecuted, String[] args) {
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("help")) {
                // TODO do help menu here sorted alphabetically
            }
            else {

            }
        }
        else {
            sender.sendMessage(Lang.translateInsertVariablesColor(Lang.translate("command.noargs"), Lang.translate("messageprefix.negative"), commandExecuted));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }
}
