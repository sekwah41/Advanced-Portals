package com.sekwah.advancedportals.spigot.coreconnector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.core.connector.command.CommandRegister;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;

/**
 * Register the CommandTemplate files to the appropriate system
 */
public class SpigotCommandRegister implements CommandRegister {

    private final AdvancedPortalsPlugin plugin;

    public SpigotCommandRegister(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the command to the appropriate system
     * @param commandName
     * @param commandExecutor
     */
    public void registerCommand(String commandName, CommandTemplate commandExecutor) {
        this.plugin.getCommand(commandName).setExecutor(new SpigotCommandHandler(commandExecutor));
    }
}
