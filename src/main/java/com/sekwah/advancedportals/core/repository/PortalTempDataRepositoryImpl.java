package com.sekwah.advancedportals.core.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.core.enums.EnumHandSelection;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class PortalTempDataRepositoryImpl implements PortalTempDataRepository {
    Cache<UUID, String> selectedPortal = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build();

    Table<UUID, EnumHandSelection, PortalLocation> selectedHand = HashBasedTable.create();

    @Override
    public void addSelectedPortal(UUID selectedPlayer, String portal) {
        selectedPortal.put(selectedPlayer, portal);
    }

    @Override
    public void removeSelectedPortal(UUID uuid) {
        selectedPortal.invalidate(uuid);
    }

    @Override
    public void addSelectedHand(UUID uuid, EnumHandSelection enumHandSelection, PortalLocation portalLocation) {
        selectedHand.put(uuid, enumHandSelection, portalLocation);
    }

    @Override
    public void removeSelectedHand(UUID uuid, EnumHandSelection enumHandSelection) {
        selectedHand.remove(uuid, enumHandSelection);
    }

    @Override
    public void removeAllSelectedHand(UUID uuid) {
        selectedHand.remove(uuid, EnumHandSelection.LEFTHAND);
        selectedHand.remove(uuid, EnumHandSelection.RIGHTHAND);
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
