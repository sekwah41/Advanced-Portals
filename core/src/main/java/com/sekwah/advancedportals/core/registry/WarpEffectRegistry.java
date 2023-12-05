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


    private Map<String, WarpEffect> visualEffects = new HashMap();

    private Map<String, WarpEffect> soundEffects = new HashMap();

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
    public boolean registerEffect(String name, WarpEffect effect, WarpEffect.Type type) {
        if(name == null){
            return false;
        }
        Map<String, WarpEffect> list = null;
        switch (type){
            case SOUND:
                list = this.soundEffects;
                break;
            case VISUAL:
                list = this.visualEffects;
                break;
            default:
                this.portalsCore.getInfoLogger().warning(type.toString()
                        + " effect type not recognised");
                return false;
        }
        if(list.containsKey(name)){
            return false;
        }
        list.put(name, effect);
        return true;
    }

    public WarpEffect getEffect(String name, WarpEffect.Type type){
        Map<String, WarpEffect> list = null;
        switch (type){
            case SOUND:
                list = this.soundEffects;
                break;
            case VISUAL:
                list = this.visualEffects;
                break;
            default:
                this.infoLogger.warning(type.toString()
                        + " effect type not recognised");
                return null;
        }
        if(list.containsKey(name)) {
            return list.get(name);
        }
        else{
            this.infoLogger.warning("No effect of type:"
                    + type.toString() + " was registered with the name: " + name);
            return null;
        }
    }

}
