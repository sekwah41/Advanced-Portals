package com.sekwah.advancedportals.spigot.warpeffects;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;

public class SpigotWarpEffects {
    @Inject
    private WarpEffectRegistry warpEffectRegistry;

    public void registerEffects() {
        warpEffectRegistry.registerEffect("ender", new EnderWarpEffect());
    }
}
