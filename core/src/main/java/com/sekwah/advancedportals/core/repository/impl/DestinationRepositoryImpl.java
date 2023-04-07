package com.sekwah.advancedportals.core.repository.impl;

import com.google.common.collect.ImmutableMap;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;

import javax.inject.Singleton;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DestinationRepositoryImpl implements IDestinationRepository {
    private final String fileLocation = "";


    private Map<String, Destination> destinationCache = new HashMap<String, Destination>();

    public void addDestination(String name, Destination destination) throws IOException {
        gson.toJson(destination, new FileWriter(fileLocation + name + ".json"));
    }

    @Override
    public boolean save(String name, Destination destination) {
        return false;
    }

    public boolean containsKey(String name) {
        return Files.exists(Paths.get(fileLocation + "\\" + name + ".json"));
    }

    @Override
    public boolean delete(String name) {
        try {
            Files.deleteIfExists(Paths.get(fileLocation + "\\" + name + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(String name, Destination destination) {
        return false;
    }

    public Destination get(String s) {
        return null;
    }

    public ImmutableMap<String, Destination> getAll() {
        return null;
    }
}
