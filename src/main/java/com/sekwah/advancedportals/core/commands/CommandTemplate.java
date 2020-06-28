package com.sekwah.advancedportals.core.commands;

import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.List;

/**
 * Already know spigot's auto complete possibilities
 *
 * Sponge https://docs.spongepowered.org/stable/en/plugin/commands/arguments.html#custom-command-elements
 */
public interface CommandTemplate {

    void onCommand(CommandSenderContainer sender, String commandExecuted, String[] args);

    /**
     * Fired when someone asks for a tab complete action.
     * @param sender
     * @param args
     * @return
     */
    List<String> onTabComplete(CommandSenderContainer sender, String[] args);

}
