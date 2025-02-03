package com.sekwah.advancedportals.legacyspigot.warpeffects;

import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;
import com.google.inject.Inject;

public class SpigotWarpEffects {
    @Inject
    private WarpEffectRegistry warpEffectRegistry;

    public void registerEffects() {
        warpEffectRegistry.registerEffect("ender", new EnderWarpEffect());
    }
}
