package com.sekwah.advancedportals.core.registry;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

public interface CommandHandler {
    void onExecute(String commandName, String parentCommand, CommandSenderContainer sender, ImmutableList<String> args);

    default void onCommandFailure(String[] command, CommandSenderContainer sender, CommandException exception, ImmutableList<String> args) {
        sender.sendMessage(exception.getMessage());
    }
}
