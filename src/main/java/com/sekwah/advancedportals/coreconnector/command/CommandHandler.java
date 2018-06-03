package com.sekwah.advancedportals.coreconnector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final CommandTemplate commandExecutor;

    public CommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        this.commandExecutor.onCommand(new CommandSenderContainer(commandSender), command.getName(), args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return this.commandExecutor.onTabComplete(new CommandSenderContainer(commandSender), args);
    }
}
