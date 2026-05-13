package com.sekwah.advancedportals.core.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.IPlayerDataRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Singleton
public final class PlayerDataServices {
    /**
     * Possibly change to the cache map Aztec was talking about
     */
    private final Map<UUID, PlayerData> tempDataMap = new HashMap<>();
    public static final ReentrantReadWriteLock tempDataMapLock = new ReentrantReadWriteLock();

    @Inject
    private IPlayerDataRepository tempDataRepository;

    @Inject
    private ConfigRepository configRepository;

    public PlayerData getPlayerData(PlayerContainer player) {

        tempDataMapLock.writeLock().lock();
        var playerData = tempDataMap.computeIfAbsent(player.getUUID(), uuid -> {
            PlayerData tempData =
                    tempDataRepository.get(player.getUUID().toString());

            if (tempData == null) {
                tempData = new PlayerData();
            }
            return tempData;
        });
        tempDataMapLock.writeLock().unlock();
        return playerData;
    }

    public void setJoinCooldown(PlayerContainer player) {
        PlayerData tempData = getPlayerData(player);
        tempData.setJoinCooldown(configRepository.getPortalCooldown() * 1000);
    }

    public void playerLeave(PlayerContainer player) {
        if(!configRepository.disablePlayerDataSaving()) {
            tempDataRepository.save(player.getUUID().toString(),
                    getPlayerData(player));
        }
        tempDataMapLock.writeLock().lock();
        tempDataMap.remove(player.getUUID());
        tempDataMapLock.writeLock().unlock();
    }

    public void playerSelectorActivate(PlayerContainer player,
                                       BlockLocation blockLoc,
                                       boolean leftClick) {
        PlayerData tempData = getPlayerData(player);
        if (leftClick) {
            tempData.setPos1(blockLoc);
        } else {
            tempData.setPos2(blockLoc);
        }
        player.sendMessage(Lang.translateInsertVariables(
            "portal.selector.poschange", leftClick ? "1" : "2",
            blockLoc.getPosX(), blockLoc.getPosY(), blockLoc.getPosZ()));
    }
}
