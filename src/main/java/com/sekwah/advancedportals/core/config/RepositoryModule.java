package com.sekwah.advancedportals.core.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.repository.*;

public class RepositoryModule extends AbstractModule {

    private final AdvancedPortalsCore portalsCore;

    public RepositoryModule(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }

    @Override
    protected void configure() {
        bind(PortalRepository.class).to(PortalRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(DestinationRepository.class).to(DestinationRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(PortalTempDataRepository.class).to(PortalTempDataRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(ConfigRepository.class).to(ConfigRepositoryImpl.class).in(Scopes.SINGLETON);
    }

    @Provides
    AdvancedPortalsCore providePortalsCore() {
        return this.portalsCore;
    }
}
