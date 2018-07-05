package com.sekwah.advancedportals.coreconnector.command;


import com.sekwah.advancedportals.core.commands.CommandTemplate;

public abstract class CommandHandler {

    protected final CommandTemplate commandExecutor;

    public CommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

}
