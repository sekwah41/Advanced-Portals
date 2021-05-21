package com.sekwah.advancedportals.core.commands;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.registry.SubCommandRegistry;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandWithSubCommands implements CommandTemplate {

    private final SubCommandRegistry subCommandRegistry;

    private final int subCommandsPerPage = 7;

    public CommandWithSubCommands() {
        this.subCommandRegistry = new SubCommandRegistry();
    }

    public boolean registerSubCommand(String arg, SubCommand subCommand, String... aliasArgs) {
        boolean hasRegistered = false;
        for(String additionalArg : aliasArgs) {
            hasRegistered = hasRegistered || this.subCommandRegistry.registerSubCommand(additionalArg,subCommand);
        }
        return hasRegistered || this.subCommandRegistry.registerSubCommand(arg,subCommand);
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
                        if(helpPage > pages) {
                            helpPage = pages;
                        }
                        if(helpPage <= 0) {
                            helpPage = 1;
                        }
                    }
                    catch(NumberFormatException e) {
                        String subCommand = args[1].toLowerCase();
                        if(this.subCommandRegistry.isArgRegistered(subCommand)) {
                            sender.sendMessage(Lang.translateInsertVariablesColor("command.help.subcommandheader",
                                    commandExecuted.substring(0,1).toUpperCase() + commandExecuted.substring(1).toLowerCase(), subCommand));
                            sender.sendMessage("\u00A77" + this.getSubCommand(subCommand).getDetailedHelpText());
                        }
                        else {
                            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateInsertVariablesColor("command.help.invalidhelp", args[1]));
                        }
                        return;
                    }
                }
                sender.sendMessage(Lang.translateInsertVariablesColor("command.help.header",
                        commandExecuted.substring(0,1).toUpperCase() + commandExecuted.substring(1).toLowerCase(), helpPage, pages));
                sender.sendMessage("\u00A7a█\u00A77 = Permission \u00A7c█\u00A77 = No Permission");
                int subCommandOffset = (helpPage - 1) * this.subCommandsPerPage;
                int displayEnd = subCommandOffset + this.subCommandsPerPage;
                if(displayEnd > subCommands.length) {
                    displayEnd = subCommands.length;
                }
                for(; subCommandOffset < displayEnd; subCommandOffset++) {
                    SubCommand subCommand = this.getSubCommand(subCommands[subCommandOffset]);
                    String colorCode = "\u00A7" + (subCommand.hasPermission(sender) ? "a" : "c");
                    sender.sendMessage("\u00A7e/" + commandExecuted + " " + subCommands[subCommandOffset]
                            + colorCode + " - " + subCommand.getBasicHelpText());
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
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateColor("command.subcommand.invalid"));
            }
        }
        else {
            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateInsertVariablesColor("command.noargs", commandExecuted));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            if(args[0].equalsIgnoreCase("help")) {
                List<String> allowedCommands = new ArrayList<>(this.subCommandRegistry.getSubCommands());
                Collections.sort(allowedCommands);
                return this.filterTabResults(allowedCommands, args[args.length - 1]);
            }
            else {
                for (String subCommandName : this.subCommandRegistry.getSubCommands()) {
                    if (subCommandName.equalsIgnoreCase(args[0])) {
                        SubCommand subCommand = this.getSubCommand(subCommandName);
                        if (subCommand.hasPermission(sender)) {
                            return this.filterTabResults(this.getSubCommand(subCommandName).onTabComplete(sender, args),
                                    args[args.length - 1]);
                        } else {
                            return null;
                        }
                    }
                }
            }
        }
        else {
            List<String> allowedCommands = new ArrayList<>();
            for (String subCommandName : this.subCommandRegistry.getSubCommands()) {
                SubCommand subCommand = this.getSubCommand(subCommandName);
                if(subCommand.hasPermission(sender)) {
                    allowedCommands.add(subCommandName);
                }
            }
            allowedCommands.add("help");
            Collections.sort(allowedCommands);
            return this.filterTabResults(allowedCommands, args[0]);
        }
        return null;
    }

    public List<String> filterTabResults(List<String> tabList, String lastArg) {
        if(tabList == null) {
            return null;
        }
        for(String arg : tabList.toArray(new String[0])) {
            if(!arg.startsWith(lastArg.toLowerCase())) {
                tabList.remove(arg);
            }
        }
        return tabList;
    }
}
