package com.sekwah.advancedportals.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.PlayerLocation;
import com.sekwah.advancedportals.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class PortalRepositoryImpl implements PortalRepository {
    Cache<UUID, String> selectedPortal = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build();

    public String getSelectedPortal(UUID uuid) {
        return selectedPortal.getIfPresent(uuid);
    }
    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public boolean update(String name, PortalLocation portalLocation) {
        return false;
    }
}
