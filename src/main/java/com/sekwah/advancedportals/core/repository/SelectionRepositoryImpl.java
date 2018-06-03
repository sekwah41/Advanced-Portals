package com.sekwah.advancedportals.core.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.core.enums.EnumHandSelection;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class SelectionRepositoryImpl implements SelectionRepository {
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
}
