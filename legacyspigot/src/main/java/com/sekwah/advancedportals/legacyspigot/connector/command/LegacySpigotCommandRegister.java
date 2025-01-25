package com.sekwah.advancedportals.legacyspigot.connector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.legacyspigot.AdvancedPortalsPlugin;

/**
 * Register the CommandTemplate files to the appropriate system
 */
public class LegacySpigotCommandRegister implements CommandRegister {
    private final AdvancedPortalsPlugin plugin;

    public LegacySpigotCommandRegister(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the command to the appropriate system
     *
     * @param commandName
     * @param commandExecutor
     */
    public void registerCommand(String commandName,
                                CommandTemplate commandExecutor) {
        this.plugin.getCommand(commandName)
            .setExecutor(new LegacySpigotCommandHandler(commandExecutor));
    }
}
