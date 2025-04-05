package com.sekwah.advancedportals.forge.connector.command;

import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.util.InfoLogger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

public class ForgeCommandRegister implements CommandRegister {

    @Inject
    private InfoLogger infoLogger;

    private List<Command> commands = new ArrayList<Command>();

    @Override
    public void registerCommand(String commandName, CommandTemplate commandExecutor) {
        this.infoLogger.info("Registering command: " + commandName);
        var command = Commands.literal(commandName).executes(ctx -> Command.SINGLE_SUCCESS).build();
    }


    public void registerCommandsEvent(RegisterCommandsEvent event) {
        // Register commands on server start
        this.infoLogger.info("Registering commands on server start");

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.
    }
}
