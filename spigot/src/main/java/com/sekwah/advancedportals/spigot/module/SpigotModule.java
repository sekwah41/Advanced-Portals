package com.sekwah.advancedportals.spigot.module;

import com.google.inject.AbstractModule;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.spigot.connector.command.SpigotCommandRegister;

public class SpigotModule extends AbstractModule {
    private final AdvancedPortalsPlugin plugin;

    public SpigotModule(AdvancedPortalsPlugin advancedPortalsPlugin) {
        this.plugin = advancedPortalsPlugin;
    }

    @Override
    protected void configure() {
        SpigotCommandRegister commandRegister = new SpigotCommandRegister(this.plugin);
        bind(CommandRegister.class).toInstance(commandRegister);
    }
}
