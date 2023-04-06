package com.sekwah.advancedportals.core.effect;


import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.data.WorldLocation;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;

/**
 * Effects to be registered to the list.
 * <p>
 * Fires once at each end.
 * <p>
 * Can be a Visual effect or a Sound. Just register to the correct one
 *
 * @author sekwah41
 */
public abstract class WarpEffect {

    protected abstract void onWarp(PlayerContainer player, WorldLocation loc, Action action, Type type, AdvancedPortal portal);

    public enum Action {
        ENTER,
        EXIT;
    }

    public enum Type {
        SOUND,
        VISUAL;
    }

}
