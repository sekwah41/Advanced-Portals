package com.sekwah.advancedportals.forge.coreconnector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.coreconnector.command.CommandRegister;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Register the CommandTemplate files to the appropriate system
 */
public class ForgeCommandRegister implements CommandRegister {

    private final FMLServerStartingEvent event;

    public ForgeCommandRegister(FMLServerStartingEvent event) {
        this.event = event;
    }

    /**
     * Registers the command to the appropriate system
     * @param commandName
     * @param commandExecutor
     */
    public void registerCommand(String commandName, CommandTemplate commandExecutor) {
        event.registerServerCommand(new ForgeCommandHandler(commandName, commandExecutor));
    }
}
