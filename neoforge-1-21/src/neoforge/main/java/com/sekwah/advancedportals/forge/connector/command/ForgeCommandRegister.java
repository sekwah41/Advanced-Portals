package com.sekwah.advancedportals.forge.connector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;

public class ForgeCommandRegister implements CommandRegister {
    @Override
    public void registerCommand(String commandName, CommandTemplate commandExecutor) {
        System.out.println("Registering command: " + commandName);
    }
}
