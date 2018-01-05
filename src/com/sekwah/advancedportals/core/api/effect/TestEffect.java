package com.sekwah.advancedportals.core.api.effect;

import com.sekwah.advancedportals.core.api.portal.Portal;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

/**
 * @author sekwah41
 */
public class TestEffect implements WarpEffect {

    @Override
    public void onWarp(PlayerContainer player, PortalLocation loc, Action action, Portal portal) {

    }

    @Override
    public Type getType() {
        return null;
    }
}
