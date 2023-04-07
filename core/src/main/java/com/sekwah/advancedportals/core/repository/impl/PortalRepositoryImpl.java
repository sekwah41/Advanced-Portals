package com.sekwah.advancedportals.core.repository.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.data.WorldLocation;
import com.sekwah.advancedportals.core.repository.IPortalRepository;

import java.util.UUID;

@Singleton
public class PortalRepositoryImpl implements IPortalRepository {

    public String getSelectedPortal(UUID uuid) {
        return null;
    }

    @Override
    public boolean save(String name, WorldLocation portalLocation) {
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
    public boolean update(String name, WorldLocation portalLocation) {
        return false;
    }

    @Override
    public WorldLocation get(String name) {
        return null;
    }

    @Override
    public ImmutableMap<String, WorldLocation> getAll() {
        return null;
    }
}
