package com.sekwah.advancedportals.core.registry;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.core.util.InfoLogger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sekwah41
 */
public class WarpEffectRegistry {
    private final Map<String, WarpEffect> warpEffects = new HashMap<>();

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Inject
    private InfoLogger infoLogger;

    /**
     * Register a new warp effect.
     *
     * @param name
     * @param effect
     * @return if the effect was registered
     */
    public void registerEffect(String name, WarpEffect effect) {
        if (name == null) {
            this.infoLogger.warning("Effect name cannot be null");
            return;
        }
        if (this.warpEffects.containsKey(name)) {
            this.infoLogger.warning("Effect with the name: " + name
                                    + " already exists");
            return;
        }
        this.warpEffects.put(name, effect);
    }

    public WarpEffect.Visual getVisualEffect(String name) {
        if (this.warpEffects.containsKey(name.toLowerCase())) {
            WarpEffect effect = this.warpEffects.get(name);
            if (effect instanceof WarpEffect.Visual) {
                return (WarpEffect.Visual) effect;
            } else {
                this.infoLogger.warning("Effect called " + name
                                        + " is not a visual effect");
                return null;
            }
        } else {
            this.infoLogger.warning("No effect called " + name
                                    + " is registered");
            return null;
        }
    }

    public WarpEffect.Sound getSoundEffect(String name) {
        if (this.warpEffects.containsKey(name)) {
            WarpEffect effect = this.warpEffects.get(name);
            if (effect instanceof WarpEffect.Sound) {
                return (WarpEffect.Sound) effect;
            } else {
                this.infoLogger.warning("Effect called " + name
                                        + " is not a sound effect");
                return null;
            }
        } else {
            this.infoLogger.warning("No effect called " + name
                                    + " is registered");
            return null;
        }
    }
}
