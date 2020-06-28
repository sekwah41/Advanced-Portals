package com.sekwah.advancedportals.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Deprecated
public class PortalTempDataRepositoryImpl implements PortalTempDataRepository {
    Cache<UUID, String> selectedPortal = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build();

    Table<UUID, Boolean, PortalLocation> selectedPositions = HashBasedTable.create();

    @Override
    public void addSelectedPortal(UUID selectedPlayer, String portal) {
        selectedPortal.put(selectedPlayer, portal);
    }

    @Override
    public void removeSelectedPortal(UUID uuid) {
        selectedPortal.invalidate(uuid);
    }

    @Override
    public void addSelectedPosition(UUID uuid, boolean isPos1, PortalLocation portalLocation) {
        selectedPositions.put(uuid, isPos1, portalLocation);
    }

    @Override
    public void removeSelectedPosition(UUID uuid, boolean isPos1) {
        selectedPositions.remove(uuid, isPos1);
    }

    @Override
    public void removeAllSelectedHand(UUID uuid) {
        selectedPositions.remove(uuid, true);
        selectedPositions.remove(uuid, false);
    }

    @Override
    public void activateCooldown(PlayerContainer player) {

    }

    @Override
    public void playerLeave(PlayerContainer player) {

    }

    @Override
    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }
}
