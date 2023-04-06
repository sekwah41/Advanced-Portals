package com.sekwah.advancedportals.core.module;

import com.google.inject.*;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.config.Config;
import com.sekwah.advancedportals.core.config.ConfigProvider;
import com.sekwah.advancedportals.core.data.DataStorage;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.ConfigRepositoryImpl;
import com.sekwah.advancedportals.core.util.InfoLogger;

import javax.annotation.Nonnull;

public class AdvancedPortalsModule extends AbstractModule {


    private Injector injector;
    private AdvancedPortalsCore advancedPortalsCore;

    public AdvancedPortalsModule(AdvancedPortalsCore advancedPortalsCore) {
        this.advancedPortalsCore = advancedPortalsCore;
        createInjector();
    }

    @Override
    protected void configure() {
        // Instances
        bind(AdvancedPortalsCore.class).toInstance(advancedPortalsCore);
        bind(InfoLogger.class).toInstance(advancedPortalsCore.getInfoLogger());
        bind(DataStorage.class).toInstance(advancedPortalsCore.getDataStorage());
        bind(ConfigRepository.class).to(ConfigRepositoryImpl.class).in(Scopes.SINGLETON);

        // Providers
        bind(Config.class).toProvider(ConfigProvider.class);
    }

    public Injector createInjector() {
        if(injector == null) {
            injector = Guice.createInjector(this);
        }
        return injector;
    }

    @Nonnull
    public Injector getInjector() {
        return injector;
    }
}
