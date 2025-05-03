package com.sekwah.advancedportals.forge.connector.command;

import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.util.InfoLogger;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.HashMap;
import java.util.Map;

public class ForgeCommandRegister implements CommandRegister {

    @Inject
    private InfoLogger infoLogger;

    private Map<String, CommandTemplate> commands = new HashMap<>();

    @Override
    public void registerCommand(String commandName, CommandTemplate commandExecutor) {
        this.infoLogger.info("Registering command: " + commandName);
        this.commands.put(commandName, commandExecutor);
    }


    public void registerCommandsEvent(RegisterCommandsEvent event) {
        // Register commands on server start
        this.infoLogger.info("Registering commands on server start");

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        for ( Map.Entry<String, CommandTemplate> entry : commands.entrySet()) {
            String commandName = entry.getKey();
            CommandTemplate commandExecutor = entry.getValue();

            ForgeCommandHandler commandHandler = new ForgeCommandHandler(commandExecutor);

            // Register the command with the dispatcher
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSourceStack>literal(commandName).requires(commandHandler)
                            .executes(commandHandler)
            );
        }
    }
}
