package com.sekwah.advancedportals.core.api.commands;

import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;

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
    boolean onCommand(CommandSenderContainer sender, String[] args);

    /**
     *
     *
     * @param sender
     * @param args arguments including the subcommand that has been specified.
     * @return tab completion for the subcommand
     */
    List<String> onTabComplete(CommandSenderContainer sender, String[] args);

    /**
     * @return the string to show on the above help menu. (describing the subcommand)
     */
    String getHelpText();

}
