package com.sekwah.advancedportals.core;

import com.google.inject.Injector;

public class AdvancedPortalsCore {

    private Injector injector;

    public void onEnable() {
        AdvancedPortalsModule module = new AdvancedPortalsModule(this);
        injector = module.getInjector();
    }

    public void onDisable() {

    }

}
