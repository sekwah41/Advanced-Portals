package com.sekwah.advancedportals.core.services;

import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;

/**
 * Provides a way for platform specific implementations to fire events.
 */
public interface EventDispatcher {

    enum WarpStage {
        PRE,
        ACTIVATED,
        POST
    }

    /**
     * Dispatch a warp event for the given player and portal.
     *
     * @param player the player being warped
     * @param portal the portal that triggered the warp
     * @param data activation data for the warp
     * @param stage the stage of the warp activation
     * @return true if the event was not cancelled
     */
    boolean dispatchWarpEvent(PlayerContainer player, AdvancedPortal portal,
                              ActivationData data, WarpStage stage);
}
