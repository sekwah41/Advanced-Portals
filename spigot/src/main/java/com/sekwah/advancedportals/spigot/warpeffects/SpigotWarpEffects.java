package com.sekwah.advancedportals.spigot.warpeffects;

import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;
import javax.inject.Inject;

public class SpigotWarpEffects {
    @Inject
    private WarpEffectRegistry warpEffectRegistry;

    public void registerEffects() {
        warpEffectRegistry.registerEffect("ender", new EnderWarpEffect());
    }
}
