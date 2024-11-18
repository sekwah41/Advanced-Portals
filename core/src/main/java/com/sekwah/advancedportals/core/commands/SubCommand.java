package com.sekwah.advancedportals.core.commands;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;

import java.util.List;

public interface SubCommand {
    /**
     * @param sender
     * @param args arguments including the subcommand that has been specified.
     * @return if the command has worked (if false it will just display a
     *     message from the command suggesting to check help)
     */
    void onCommand(CommandSenderContainer sender, String[] args);

    boolean hasPermission(CommandSenderContainer sender);

    /**
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

    interface SubCommandOnInit {
        void registered();
    }
}
