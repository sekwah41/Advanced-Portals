package com.sekwah.advancedportals.core.api.effect;

import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

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

    protected abstract void onWarp(PlayerContainer player, PortalLocation loc, Action action, Type type, AdvancedPortal portal);

    public enum Action {
        ENTER,
        EXIT;
    }

    public enum Type {
        SOUND,
        VISUAL;
    }

}
