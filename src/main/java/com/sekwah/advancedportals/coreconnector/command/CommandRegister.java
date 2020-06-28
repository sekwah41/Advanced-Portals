package com.sekwah.advancedportals.coreconnector.command;

import com.sekwah.advancedportals.commands.CommandTemplate;

public interface CommandRegister {

    /**
     * Registers the command to the appropriate system
     * @param commandName
     * @param commandExecutor
     */
    void registerCommand(String commandName, CommandTemplate commandExecutor);

}
