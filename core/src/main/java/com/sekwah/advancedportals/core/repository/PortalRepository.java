package com.sekwah.advancedportals.core.repository;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.data.WorldLocation;

import java.util.UUID;

@Singleton
public class PortalRepository implements IPortalRepository {

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
}
