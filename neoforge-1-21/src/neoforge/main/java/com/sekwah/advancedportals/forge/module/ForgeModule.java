package com.sekwah.advancedportals.forge.module;

import com.google.inject.AbstractModule;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.forge.connector.command.ForgeCommandRegister;

public class ForgeModule extends AbstractModule {
    @Override
    protected void configure() {
        ForgeCommandRegister commandRegister = new ForgeCommandRegister();

        // This is to avoid the bind.to.in creating a separate instance for each of these.
        bind(ForgeCommandRegister.class).toInstance(commandRegister);
        bind(CommandRegister.class).toInstance(commandRegister);
    }
}
