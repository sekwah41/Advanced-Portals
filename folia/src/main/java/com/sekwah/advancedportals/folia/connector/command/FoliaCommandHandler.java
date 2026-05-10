package com.sekwah.advancedportals.folia.connector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.folia.connector.container.FoliaCommandSenderContainer;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class FoliaCommandHandler implements CommandExecutor, TabCompleter {
    private final CommandTemplate commandExecutor;

    public FoliaCommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command,
                             String s, String[] args) {
        this.commandExecutor.onCommand(
            new FoliaCommandSenderContainer(commandSender), command.getName(),
            args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender,
                                      Command command, String s,
                                      String[] args) {
        return this.commandExecutor.onTabComplete(
            new FoliaCommandSenderContainer(commandSender), args);
    }
}
