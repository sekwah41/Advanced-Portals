package com.sekwah.advancedportals.core.commands;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.registry.SubCommandRegistry;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;

import java.util.ArrayList;
import java.util.List;

public class CommandWithSubCommands implements CommandTemplate {

    private final SubCommandRegistry subCommandRegistry;

    private final int subCommandsPerPage = 5;

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
                int helpPage = 1;
                String[] subCommands = this.subCommandRegistry.getSubCommands().toArray(new String[0]);
                int pages = (int) Math.ceil(subCommands.length / (float) this.subCommandsPerPage);
                if(args.length > 1) {
                    try {
                        helpPage = Integer.parseInt(args[1]);
                        if(helpPage < 0) {
                            helpPage = 1;
                        }
                        else if(helpPage > pages) {
                            helpPage = pages;
                        }
                    }
                    catch(NumberFormatException e) {
                        sender.sendMessage(Lang.translateInsertVariablesColor("command.help.invalidnum", args[1]));
                        return;
                    }
                }
                commandExecuted = commandExecuted.substring(0,1).toUpperCase() + commandExecuted.substring(1).toLowerCase();
                sender.sendMessage(Lang.translateInsertVariablesColor("command.help.header", commandExecuted, helpPage, pages));
                int subCommandOffset = (helpPage - 1) * this.subCommandsPerPage;
                int displayEnd = subCommandOffset + this.subCommandsPerPage;
                if(displayEnd > subCommands.length) {
                    displayEnd = subCommands.length;
                }
                for(; subCommandOffset < displayEnd; subCommandOffset++) {
                    sender.sendMessage("\u00A76/" + commandExecuted + " " + subCommands[subCommandOffset]
                            + "\u00A7a - " + this.getSubCommand(subCommands[subCommandOffset]).getBasicHelpText());
                }
            }
            else {
                for(String subCommandName : this.subCommandRegistry.getSubCommands()) {
                    if(subCommandName.equalsIgnoreCase(args[0])) {
                        SubCommand subCommand = this.getSubCommand(subCommandName);
                        if(subCommand.hasPermission(sender)) {
                            subCommand.onCommand(sender, args);
                        }
                        else {
                            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateInsertVariablesColor("command.subcommand.nopermission",
                                    commandExecuted));
                        }
                        return;
                    }
                }
            }
        }
        else {
            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateInsertVariablesColor("command.noargs", commandExecuted));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if(args.length > 0) {
            for (String subCommandName : this.subCommandRegistry.getSubCommands()) {
                if (subCommandName.equalsIgnoreCase(args[0])) {
                    SubCommand subCommand = this.getSubCommand(subCommandName);
                    if(subCommand.hasPermission(sender)) {
                        this.getSubCommand(subCommandName).onTabComplete(sender, args);
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        else {
            return this.subCommandRegistry.getSubCommands();
        }
        return null;
    }
}
