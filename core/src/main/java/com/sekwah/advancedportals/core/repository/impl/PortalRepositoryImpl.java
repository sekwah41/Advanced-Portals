package com.sekwah.advancedportals.core.repository.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.WorldLocation;
import com.sekwah.advancedportals.core.repository.IPortalRepository;

import java.util.*;

@Singleton
public class PortalRepositoryImpl implements IPortalRepository {

    /**
     * In memory copy of the portal files as they will be accessed every movement tick.
     *
     * If we need to get it by name we can just load it from the file, but this is good for looping fast for the player move events.
     */
    private List<AdvancedPortal> portals = new ArrayList<>();

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
    public List<String> listAll() {
        return null;
    }
}
