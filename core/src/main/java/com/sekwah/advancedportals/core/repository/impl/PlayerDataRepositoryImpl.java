package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.repository.IPlayerDataRepository;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;

import java.util.List;

public class PlayerDataRepositoryImpl implements IPlayerDataRepository {

    private final String fileLocation = "playerData/";

    @Inject
    DataStorage dataStorage;

    @Override
    public boolean save(String name, PlayerData playerData) {
        return dataStorage.storeFile(playerData, fileLocation + name + ".yml");
    }

    @Override
    public boolean containsKey(String name) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return dataStorage.deleteFile(fileLocation + name + ".yml");
    }

    @Override
    public PlayerData get(String name) {
        return dataStorage.loadFile(PlayerData.class, fileLocation + name + ".yml");
    }

    @Override
    public List<String> getAllNames() {
        return null;
    }

    @Override
    public List<PlayerData> getAll() {
        return null;
    }
}
