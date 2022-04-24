package com.sekwah.advancedportals.core.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;

public class CoreModule extends AbstractModule {

    private final AdvancedPortalsCore portalsCore;

    /**
     * Parts provided by the core module. Check the implementation for its individual integrations.
     * @param portalsCore
     */
    public CoreModule(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }

    @Override
    protected void configure() {
        //        bind(IPortalRepository.class).to(PortalRepository.class).in(Scopes.SINGLETON);
        //        bind(IDestinationRepository.class).to(DestinationRepository.class).in(Scopes.SINGLETON);
        //        bind(IPortalRepository.class).to(PortalRepository.class).in(Scopes.SINGLETON);
        //        bind(ConfigRepository.class).to(ConfigRepositoryImpl.class).in(Scopes.SINGLETON);
        //bindListener(Matchers.Any(), new Log4JTypeListenr());
    }

    // https://github.com/google/guice/wiki/GettingStarted
    @Provides
    AdvancedPortalsCore providePortalsCore() {
        return this.portalsCore;
    }
}
