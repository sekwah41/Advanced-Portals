package com.sekwah.advancedportals.folia.connector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.folia.AdvancedPortalsPlugin;

/**
 * Register the CommandTemplate files to the appropriate system
 */
public class FoliaCommandRegister implements CommandRegister {
    private final AdvancedPortalsPlugin plugin;

    public FoliaCommandRegister(AdvancedPortalsPlugin plugin) {
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
            .setExecutor(new FoliaCommandHandler(commandExecutor));
    }
}
