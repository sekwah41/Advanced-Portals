package com.sekwah.advancedportals.core;

import com.google.inject.Injector;

public class AdvancedPortalsCore {

    private Injector injector;

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
