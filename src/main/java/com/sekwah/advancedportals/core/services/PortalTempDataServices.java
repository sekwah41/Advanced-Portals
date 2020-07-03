package com.sekwah.advancedportals.core.services;

import com.sekwah.advancedportals.core.data.PlayerTempData;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PortalTempDataServices {

    /**
     * Possibly change to the cache map Aztec was talking about
     */
    private Map<UUID, PlayerTempData> tempDataMap = new HashMap<>();
    
    public void activateCooldown(PlayerContainer player) {
    }

    public void playerLeave(PlayerContainer player) {
    }

    public void playerSelectorActivate(PlayerContainer player, PortalLocation blockLoc, boolean leftClick) {
    }
}
