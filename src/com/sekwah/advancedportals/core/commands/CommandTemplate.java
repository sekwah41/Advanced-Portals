package com.sekwah.advancedportals.core.commands;

import com.sun.corba.se.impl.activation.CommandHandler;

import java.util.List;

/**
 * Already know spigot's auto complete possibilities
 *
 * Sponge https://docs.spongepowered.org/stable/en/plugin/commands/arguments.html#custom-command-elements
 */
public interface CommandTemplate {

    void onCommand(CommandHandler sender, String[] args);

    /**
     * Fired when someone asks for a tab complete action.
     * @param sender
     * @param args
     * @return
     */
    List<String> onTabComplete(CommandHandler sender, String[] args);

}
