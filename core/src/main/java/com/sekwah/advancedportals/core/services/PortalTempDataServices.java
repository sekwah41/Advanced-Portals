package com.sekwah.advancedportals.core.services;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerTempData;
import com.sekwah.advancedportals.core.util.Lang;

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

    @Inject
    private ConfigRepository configRepository;

    public PlayerTempData getPlayerTempData(PlayerContainer player) {
        return tempDataMap.computeIfAbsent(player.getUUID(), uuid -> new PlayerTempData());
    }

    public void activateCooldown(PlayerContainer player) {
        var tempData = getPlayerTempData(player);
        tempData.setGlobalCooldown(System.currentTimeMillis() + configRepository.getPortalCooldown());
    }

    public void playerLeave(PlayerContainer player) {
        tempDataMap.remove(player.getUUID());
    }

    public void playerSelectorActivate(PlayerContainer player, BlockLocation blockLoc, boolean leftClick) {
        var tempData = getPlayerTempData(player);
        if(leftClick) {
            tempData.setPos1(blockLoc);
        } else {
            tempData.setPos2(blockLoc);
        }
        player.sendMessage(Lang.translateInsertVariables("portal.selector.poschange", leftClick ? "1" : "2", blockLoc.posX, blockLoc.posY, blockLoc.posZ));
    }
}
