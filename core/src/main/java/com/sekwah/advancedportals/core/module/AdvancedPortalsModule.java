package com.sekwah.advancedportals.core.module;

import com.google.inject.*;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.config.Config;
import com.sekwah.advancedportals.core.serializeddata.config.ConfigProvider;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.repository.impl.ConfigRepositoryImpl;
import com.sekwah.advancedportals.core.repository.impl.DestinationRepositoryImpl;
import com.sekwah.advancedportals.core.repository.impl.PortalRepositoryImpl;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.InfoLogger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AdvancedPortalsModule extends AbstractModule {


    private Injector injector;
    private AdvancedPortalsCore advancedPortalsCore;
    private DataStorage dataStorage;

    private List<DelayedBinding> delayedBindings = new ArrayList<>();

    public AdvancedPortalsModule(AdvancedPortalsCore advancedPortalsCore) {
        this.advancedPortalsCore = advancedPortalsCore;
    }

    /**
     * https://github.com/google/guice/wiki/Bindings
     */
    @Override
    protected void configure() {
        bind(IPortalRepository.class).to(PortalRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(IDestinationRepository.class).to(DestinationRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(ConfigRepository.class).to(ConfigRepositoryImpl.class).in(Scopes.SINGLETON);

        // Instances
        bind(AdvancedPortalsCore.class).toInstance(advancedPortalsCore);
        bind(InfoLogger.class).toInstance(advancedPortalsCore.getInfoLogger());
        bind(DataStorage.class).toInstance(advancedPortalsCore.getDataStorage());
        bind(ServerContainer.class).toInstance(advancedPortalsCore.getServerContainer());

        // Providers
        bind(Config.class).toProvider(ConfigProvider.class);
        bind(TagRegistry.class).asEagerSingleton();

        // Delayed Bindings
        for(DelayedBinding delayedBinding : delayedBindings) {
            bind(delayedBinding.clazz).toInstance(delayedBinding.instance);
        }
    }

    // TODO change it so that it'll set these up during the injector.
    public <T> void addInstanceBinding(Class<T> clazz, T instance) {
        delayedBindings.add(new DelayedBinding<>(clazz, instance));
    }

    class DelayedBinding<T> {
        private final T instance;
        private final Class<T> clazz;

        public DelayedBinding(Class<T> clazz, T instance) {
            this.clazz = clazz;
            this.instance = instance;
        }
    }

    // Call this later than the calls to addInstanceBinding
    @Nonnull
    public Injector getInjector() {
        if(injector == null) {
            injector = Guice.createInjector(this);
        }
        return injector;
    }
}
