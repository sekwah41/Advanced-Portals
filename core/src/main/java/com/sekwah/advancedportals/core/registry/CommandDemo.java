package com.sekwah.advancedportals.core.registry;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

public class CommandDemo implements CommandHandler {


    @Override
    public void onExecute(String commandName, String parentCommand, CommandSenderContainer sender, ImmutableList<String> args) {

    }

    @Override
    public void onCommandFailure(String[] command, CommandSenderContainer sender, CommandException exception, ImmutableList<String> args) {

    }
}
