package com.sekwah.advancedportals.folia.warpeffects;

import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;
import com.sekwah.advancedportals.shadowed.inject.Inject;

public class FoliaWarpEffects {
    @Inject
    private WarpEffectRegistry warpEffectRegistry;

    public void registerEffects() {
        warpEffectRegistry.registerEffect("ender", new EnderWarpEffect());
    }
}
