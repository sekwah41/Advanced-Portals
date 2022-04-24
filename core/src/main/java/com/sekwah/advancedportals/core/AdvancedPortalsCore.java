package com.sekwah.advancedportals.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.config.CoreModule;

public class AdvancedPortalsCore {

    /**
     * https://github.com/google/guice/wiki/GettingStarted
     *
     */
    private Injector injector = Guice.createInjector(new CoreModule(this));

    /**
     *  For some platforms we could do this on construction but this just allows for a bit more control
     */
    public void onEnable() {
        AdvancedPortalsModule module = new AdvancedPortalsModule(this);
        injector = module.getInjector();
    }

    public void onDisable() {

    }

}
