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

    List<String> onTabComplete(CommandHandler sender, String[] args);

}
