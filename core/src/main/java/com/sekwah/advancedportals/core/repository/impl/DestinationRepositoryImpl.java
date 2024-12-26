package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.tags.NameTag;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DestinationRepositoryImpl implements IDestinationRepository {
    private final String fileLocation = "desti/";

    @Inject
    DataStorage dataStorage;

    @Override
    public boolean save(String name, Destination destination) {
        return dataStorage.storeFile(destination,
                                     fileLocation + name + ".yaml");
    }

    public boolean containsKey(String name) {
        return dataStorage.fileExists(fileLocation + name + ".yaml");
    }

    @Override
    public boolean delete(String name) {
        return dataStorage.deleteFile(fileLocation + name + ".yaml");
    }

    public Destination get(String name) {
        return dataStorage.loadFile(Destination.class,
                                    fileLocation + name + ".yaml");
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
            if (name != null && name.length > 0) {
                destination.setArgValues(null, NameTag.TAG_NAME,
                                         new String[] {fileName});
            }
            destinations.add(destination);
        }
        return destinations;
    }
}
