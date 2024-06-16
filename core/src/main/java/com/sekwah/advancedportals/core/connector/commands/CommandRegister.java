package com.sekwah.advancedportals.core.connector.commands;

import com.sekwah.advancedportals.core.commands.CommandTemplate;

public interface CommandRegister {

    /**
     * Registers the command to the appropriate system
     *
     * @param commandName
     * @param commandExecutor
     */
    void registerCommand(String commandName, CommandTemplate commandExecutor);
}
