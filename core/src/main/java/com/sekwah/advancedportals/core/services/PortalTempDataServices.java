package com.sekwah.advancedportals.core.services;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.data.BlockLocation;
import com.sekwah.advancedportals.core.data.PlayerTempData;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class PortalTempDataServices {

    /**
     * Possibly change to the cache map Aztec was talking about
     */
    private Map<UUID, PlayerTempData> tempDataMap = new HashMap<>();
    
    public void activateCooldown(PlayerContainer player) {
    }

    public void playerLeave(PlayerContainer player) {
    }

    public void playerSelectorActivate(PlayerContainer player, BlockLocation blockLoc, boolean leftClick) {
    }
}
