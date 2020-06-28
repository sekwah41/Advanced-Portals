package com.sekwah.advancedportals.spigot.coreconnector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.spigot.coreconnector.container.SpigotCommandSenderContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class SpigotCommandHandler implements CommandExecutor, TabCompleter {

    private final CommandTemplate commandExecutor;

    public SpigotCommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        this.commandExecutor.onCommand(new SpigotCommandSenderContainer(commandSender), command.getName(), args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return this.commandExecutor.onTabComplete(new SpigotCommandSenderContainer(commandSender), args);
    }
}
