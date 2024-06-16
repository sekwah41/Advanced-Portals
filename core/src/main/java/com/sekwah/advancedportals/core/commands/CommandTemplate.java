package com.sekwah.advancedportals.core.commands;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;

import java.util.List;

/**
 * Already know spigot's auto complete possibilities
 *
 * <p>Sponge
 * https://docs.spongepowered.org/stable/en/plugin/commands/arguments.html#custom-command-elements
 */
public interface CommandTemplate {

    void onCommand(CommandSenderContainer sender, String commandExecuted, String[] args);

    /**
     * Fired when someone asks for a tab complete action.
     *
     * @param sender whoever triggered the command e.g. command block, server or player
     * @param args arguments for the command
     * @return a lot of strings that are possible completions
     */
    List<String> onTabComplete(CommandSenderContainer sender, String[] args);
}
