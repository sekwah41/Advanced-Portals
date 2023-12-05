package com.sekwah.advancedportals.core.repository.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.util.InfoLogger;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Singleton
public class DestinationRepositoryImpl implements IDestinationRepository {
    private final String fileLocation = "desti/";

    @Inject
    DataStorage dataStorage;

    @Inject
    InfoLogger infoLogger;

    public void addDestination(String name, Destination destination) {
        infoLogger.log("Adding destination: " + fileLocation + name + ".json");
        dataStorage.storeJson(destination, fileLocation + name + ".json");
    }

    @Override
    public boolean save(String name, Destination destination) {
        return false;
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

    public Destination get(String desti) {
        return dataStorage.loadJson(Destination.class, fileLocation + desti + ".json");
    }

    @Override
    public List<String> listAll() {
        return dataStorage.listAllFiles(fileLocation, true);
    }
}
