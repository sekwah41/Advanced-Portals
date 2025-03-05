package com.sekwah.advancedportals.forge.connector.container;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.tags.CommandTag;

import java.util.List;
import java.util.UUID;

public class ForgeServerContainer implements ServerContainer {
    @Override
    public WorldContainer getWorld(String name) {
        return null;
    }

    @Override
    public PlayerContainer getPlayer(String name) {
        return null;
    }

    @Override
    public PlayerContainer getPlayer(UUID name) {
        return null;
    }

    @Override
    public List<String> getAllTriggerBlocks() {
        return List.of();
    }

    @Override
    public List<String> getCommonTriggerBlocks() {
        return List.of();
    }

    @Override
    public PlayerContainer[] getPlayers() {
        return new PlayerContainer[0];
    }

    @Override
    public void registerOutgoingChannel(String channel) {

    }

    @Override
    public void registerIncomingChannel(String channel) {

    }

    @Override
    public void dispatchCommand(UUID uuid, String command, CommandTag.CommandLevel commandLevel) {

    }

    @Override
    public String matchMaterialName(String materialName) {
        return "";
    }
}
