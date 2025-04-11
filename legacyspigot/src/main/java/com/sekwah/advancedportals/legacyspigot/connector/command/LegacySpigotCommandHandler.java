package com.sekwah.advancedportals.legacyspigot.connector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.legacyspigot.connector.container.LegacySpigotCommandSenderContainer;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class LegacySpigotCommandHandler
    implements CommandExecutor, TabCompleter {
    private final CommandTemplate commandExecutor;

    public LegacySpigotCommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command,
                             String s, String[] args) {
        this.commandExecutor.onCommand(
            new LegacySpigotCommandSenderContainer(commandSender),
            command.getName(), args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender,
                                      Command command, String s,
                                      String[] args) {
        return this.commandExecutor.onTabComplete(
            new LegacySpigotCommandSenderContainer(commandSender), args);
    }
}
