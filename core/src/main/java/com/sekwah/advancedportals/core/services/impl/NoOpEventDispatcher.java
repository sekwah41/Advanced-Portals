package com.sekwah.advancedportals.core.services.impl;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.EventDispatcher;
import com.sekwah.advancedportals.core.warphandler.ActivationData;

/**
 * Default implementation to use when the platform doesn't have an event dispatcher implemented or supported yet.
 */
public class NoOpEventDispatcher implements EventDispatcher {
    @Override
    public boolean dispatchWarpEvent(PlayerContainer player, AdvancedPortal portal,
                                     ActivationData data, WarpStage stage) {
        return true;
    }
}
