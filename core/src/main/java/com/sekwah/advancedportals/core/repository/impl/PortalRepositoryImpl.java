package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.tags.activation.NameTag;

import java.util.*;

@Singleton
public class PortalRepositoryImpl implements IPortalRepository {

    private final String fileLocation = "portals/";

    @Inject
    DataStorage dataStorage;

    /**
     * In memory copy of the portal files as they will be accessed every movement tick.
     *
     * If we need to get it by name we can just load it from the file, but this is good for looping fast for the player move events.
     */
    private List<AdvancedPortal> portals = new ArrayList<>();

    @Override
    public boolean save(String name, AdvancedPortal portal) {
        return dataStorage.storeJson(portal, fileLocation + name + ".json");
    }

    @Override
    public boolean containsKey(String name) {
        return dataStorage.fileExists(fileLocation + name + ".json");
    }

    @Override
    public boolean delete(String name) {
        return dataStorage.deleteFile(fileLocation + name + ".json");
    }

    @Override
    public AdvancedPortal get(String name) {
        var portal = dataStorage.loadJson(AdvancedPortal.class, fileLocation + name + ".json");
        AdvancedPortalsCore.getInstance().getModule().getInjector().injectMembers(portal);
        return portal;
    }

    @Override
    public List<String> getAllNames() {
        return dataStorage.listAllFiles(fileLocation, true);
    }

    @Override
    public List<AdvancedPortal> getAll() {
        List<AdvancedPortal> portals = new ArrayList<>();
        List<String> allFiles = dataStorage.listAllFiles(fileLocation, false);
        for (String fileName : allFiles) {
            AdvancedPortal portal = dataStorage.loadJson(AdvancedPortal.class, fileLocation + fileName);
            // Forces the name tag to be up-to-date on load
            String[] name = portal.getArgValues(NameTag.TAG_NAME);
            if(name != null && name.length > 0) {
                portal.setArgValues(NameTag.TAG_NAME, new String[]{fileName.replace(".json", "")});
            }
            portals.add(portal);
        }
        return portals;
    }
}
