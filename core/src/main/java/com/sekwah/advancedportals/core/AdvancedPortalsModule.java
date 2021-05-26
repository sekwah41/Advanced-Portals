package com.sekwah.advancedportals.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.config.Config;
import com.sekwah.advancedportals.core.config.ConfigHandler;

import javax.annotation.Nonnull;

public class AdvancedPortalsModule extends AbstractModule {


    private Injector injector;
    private AdvancedPortalsCore advancedPortalsCore;

    public AdvancedPortalsModule(AdvancedPortalsCore advancedPortalsCore) {
        this.advancedPortalsCore = advancedPortalsCore;
    }

    @Override
    protected void configure() {
        // Instances
        bind(AdvancedPortalsCore.class).toInstance(advancedPortalsCore);

        // Providers
        bind(Config.class).toProvider(ConfigHandler.class);
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Nonnull
    public Injector getInjector() {
        return injector;
    }
}
