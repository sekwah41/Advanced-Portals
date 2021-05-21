package com.sekwah.advancedportals.repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.sekwah.advancedportals.core.api.destination.Destination;

import javax.inject.Singleton;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Singleton
public class DestinationRepository implements IDestinationRepository<Destination> {
    private final String fileLocation = "";


    private Map<String, Destination> destinationCache = new HashMap<String, Destination>();

    /*Is there any reason to load it into the array if it's not been used or connected?  Q for Sekwah*/
    public void AddDestination(String name, Destination destination) throws IOException {
        gson.toJson(destination, new FileWriter(fileLocation + name + ".json"));
    }

    private void test() {
        destinationCache.get("");
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

    public ImmutableMap<String, Destination> get(String s) {
        return null;
    }
}
