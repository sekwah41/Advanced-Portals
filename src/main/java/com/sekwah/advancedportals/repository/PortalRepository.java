package com.sekwah.advancedportals.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.data.PortalLocation;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class PortalRepository implements IPortalRepository {
    Cache<UUID, String> selectedPortal = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterAccess(30, TimeUnit.DAYS)
            .build();

    public String getSelectedPortal(UUID uuid) {
        return selectedPortal.getIfPresent(uuid);
    }

    @Override
    public boolean save(String name, PortalLocation portalLocation) {
        return false;
    }

    @Override
    public boolean containsKey(String name) {
        return false;
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
