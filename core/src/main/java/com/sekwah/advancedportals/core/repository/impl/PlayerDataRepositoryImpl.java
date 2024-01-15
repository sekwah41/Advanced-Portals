package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.IPlayerDataRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;

import java.util.List;
import java.util.UUID;

public class PlayerDataRepositoryImpl implements IPlayerDataRepository {

    private final String fileLocation = "playerData/";

    @Inject
    DataStorage dataStorage;

    @Override
    public boolean save(String name, PlayerData playerData) {
        return dataStorage.storeJson(playerData, fileLocation + name + ".json");
    }

    @Override
    public boolean containsKey(String name) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return dataStorage.deleteFile(fileLocation + name + ".json");
    }

    @Override
    public PlayerData get(String name) {
        return dataStorage.loadJson(PlayerData.class, fileLocation + name + ".json");
    }

    @Override
    public List<String> getAllNames() {
        return null;
    }

    @Override
    public List<PlayerData> getAll() {
        return null;
    }

    @Override
    public void addSelectedPortal(UUID selectedPlayer, String portal) {

    }

    @Override
    public void removeSelectedPortal(UUID uuid) {

    }

    @Override
    public void addSelectedPosition(UUID uuid, boolean isPos1, BlockLocation portalLocation) {

    }

    @Override
    public void removeSelectedPosition(UUID uuid, boolean isPos1) {

    }

    @Override
    public void removeAllSelectedHand(UUID uuid) {

    }

    @Override
    public void activateCooldown(PlayerContainer player) {

    }

    @Override
    public void playerLeave(PlayerContainer player) {

    }

    @Override
    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }
}
