package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.tags.NameTag;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PortalRepositoryImpl implements IPortalRepository {
    private final String fileLocation = "portals/";

    @Inject
    DataStorage dataStorage;

    /**
     * In memory copy of the portal files as they will be accessed every
     * movement tick.
     *
     * <p>If we need to get it by name we can just load it from the file, but
     * this is good for looping fast for the player move events.
     */
    private List<AdvancedPortal> portals = new ArrayList<>();

    private boolean isValidName(String name) {
        return name != null && !name.contains("/") && !name.contains("\\");
    }

    @Override
    public boolean save(String name, AdvancedPortal portal) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        return dataStorage.storeFile(portal, fileLocation + name + ".yaml");
    }

    @Override
    public boolean containsKey(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        return dataStorage.fileExists(fileLocation + name + ".yaml");
    }

    @Override
    public boolean delete(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        return dataStorage.deleteFile(fileLocation + name + ".yaml");
    }

    @Override
    public AdvancedPortal get(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        AdvancedPortal portal = dataStorage.loadFile(
            AdvancedPortal.class, fileLocation + name + ".yaml");
        if (portal != null) {
            AdvancedPortalsCore.getInstance()
                .getModule()
                .getInjector()
                .injectMembers(portal);
        }
        return portal;
    }

    @Override
    public List<String> getAllNames() {
        return dataStorage.listAllFiles(fileLocation, true, "yaml");
    }

    @Override
    public List<AdvancedPortal> getAll() {
        List<AdvancedPortal> portals = new ArrayList<>();
        List<String> allFiles = dataStorage.listAllFiles(fileLocation, false);
        for (String fileName : allFiles) {
            AdvancedPortal portal = dataStorage.loadFile(
                AdvancedPortal.class, fileLocation + fileName);

            if (portal != null) {
                AdvancedPortalsCore.getInstance()
                    .getModule()
                    .getInjector()
                    .injectMembers(portal);
            }

            // Forces the name tag to be up-to-date on load
            String[] name = portal.getArgValues(NameTag.TAG_NAME);
            if (name != null && name.length > 0) {
                portal.setArgValues(
                    NameTag.TAG_NAME,
                    new String[] {fileName.replace(".yaml", "")});
            }
            portals.add(portal);
        }
        return portals;
    }
}
