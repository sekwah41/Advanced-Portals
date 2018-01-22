package com.sekwah.advancedportals.coreconnector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;

/**
 * Register the CommandTemplate files to the appropriate system
 */
public class CommandRegister {

    private final AdvancedPortalsPlugin plugin;

    public CommandRegister(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the command to the appropriate system
     * @param commandName
     * @param commandExecutor
     */
    public void registerCommand(String commandName, CommandTemplate commandExecutor) {
        this.plugin.getCommand(commandName).setExecutor(new CommandHandler(commandExecutor));
    }
}
