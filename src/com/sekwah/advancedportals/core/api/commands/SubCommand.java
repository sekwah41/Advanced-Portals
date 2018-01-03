package com.sekwah.advancedportals.core.api.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Subcommand that can be registered to parts.
 *
 * @author sekwah41
 */
public interface SubCommand {

    /**
     * @param sender
     * @param args arguments including the subcommand that has been specified.
     * @return if the command has worked (if false it will just display a message from the command suggesting to check help)
     */
    boolean onCommand(CommandSender sender, String[] args);

    /**
     *
     *
     * @param sender
     * @param args arguments including the subcommand that has been specified.
     * @return tab completion for the subcommand
     */
    List<String> onTabComplete(CommandSender sender, String[] args);

    /**
     * @return the string to show on the above help menu. (describing the subcommand)
     */
    String getHelpText();

}
