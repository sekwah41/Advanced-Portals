package com.sekwah.advancedportals.core.api.commands;

import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.List;

/**
 * Subcommand that can be registered under e.g. /portal show or /portal edit
 *
 * @author sekwah41
 */
public interface SubCommand {

    /**
     * @param sender
     * @param args arguments including the subcommand that has been specified.
     * @return if the command has worked (if false it will just display a message from the command suggesting to check help)
     */
    void onCommand(CommandSenderContainer sender, String[] args);

    boolean hasPermission(CommandSenderContainer sender);

    /**
     *
     *
     * @param sender
     * @param args arguments including the subcommand that has been specified.
     * @return tab completion for the subcommand
     */
    List<String> onTabComplete(CommandSenderContainer sender, String[] args);

    /**
     * @return the string to show next to the tag on the help menu.
     */
    String getBasicHelpText();

    /**
     * @return the string to show if help then the tag is listed.
     */
    String getDetailedHelpText();

}
