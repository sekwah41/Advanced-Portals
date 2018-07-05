package com.sekwah.advancedportals.coreconnector.spigot.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.coreconnector.command.CommandHandler;
import com.sekwah.advancedportals.coreconnector.spigot.container.SpigotCommandSenderContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class SpigotCommandHandler extends CommandHandler implements CommandExecutor, TabCompleter {

    public SpigotCommandHandler(CommandTemplate commandExecutor) {
        super(commandExecutor);
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
