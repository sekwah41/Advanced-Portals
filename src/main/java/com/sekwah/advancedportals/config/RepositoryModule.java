package com.sekwah.advancedportals.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.sekwah.advancedportals.*;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.repository.*;

public class RepositoryModule extends AbstractModule {

    private final AdvancedPortalsCore portalsCore;

    public RepositoryModule(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }

    @Override
    protected void configure() {
        bind(PortalRepository.class).to(PortalRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(DestinationRepository.class).to(DestinationRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(PortalRepository.class).to(PortalRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(ConfigRepository.class).to(ConfigRepositoryImpl.class).in(Scopes.SINGLETON);
        //bindListener(Matchers.Any(), new Log4JTypeListenr());
    }

    @Provides
    AdvancedPortalsCore providePortalsCore() {
        return this.portalsCore;
    }
}
