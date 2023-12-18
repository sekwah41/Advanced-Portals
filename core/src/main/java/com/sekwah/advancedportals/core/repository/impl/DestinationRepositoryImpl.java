package com.sekwah.advancedportals.core.repository.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.tags.activation.NameTag;
import com.sekwah.advancedportals.core.util.InfoLogger;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DestinationRepositoryImpl implements IDestinationRepository {
    private final String fileLocation = "desti/";

    @Inject
    DataStorage dataStorage;

    @Override
    public boolean save(String name, Destination destination) {
        return dataStorage.storeJson(destination, fileLocation + name + ".json");
    }

    public boolean containsKey(String name) {
        return dataStorage.fileExists(fileLocation + name + ".json");
    }

    @Override
    public boolean delete(String name) {
        return dataStorage.deleteFile(fileLocation + name + ".json");
    }

    @Override
    public boolean update(String name, Destination destination) {
        return false;
    }

    public Destination get(String name) {
        return dataStorage.loadJson(Destination.class, fileLocation + name + ".json");
    }

    @Override
    public List<String> getAllNames() {
        return dataStorage.listAllFiles(fileLocation, true);
    }

    @Override
    public List<Destination> getAll() {
        List<Destination> destinations = new ArrayList<>();
        List<String> allFiles = dataStorage.listAllFiles(fileLocation, true);
        for (String fileName : allFiles) {
            Destination destination = this.get(fileName);
            // Forces the name tag to be up-to-date on load
            String[] name = destination.getArgValues(NameTag.TAG_NAME);
            if(name != null && name.length > 0) {
                destination.setArgValues(NameTag.TAG_NAME, new String[]{fileName});
            }
            destinations.add(destination);
        }
        return destinations;
    }
}
