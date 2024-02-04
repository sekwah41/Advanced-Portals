package com.sekwah.advancedportals.core.effect;


import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;

public interface WarpEffect {

    enum Action {
        ENTER,
        EXIT;
    }

    interface Sound extends WarpEffect {

        void onWarpSound(PlayerContainer player, Action action);

    }

    interface Visual extends WarpEffect {

        void onWarpVisual(PlayerContainer player, Action action);

    }
}
