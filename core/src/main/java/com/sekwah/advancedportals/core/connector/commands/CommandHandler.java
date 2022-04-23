package com.sekwah.advancedportals.core.connector.commands;


import com.sekwah.advancedportals.core.commands.CommandTemplate;

public abstract class CommandHandler {


    private final CommandTemplate commandExecutor;

    public CommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
