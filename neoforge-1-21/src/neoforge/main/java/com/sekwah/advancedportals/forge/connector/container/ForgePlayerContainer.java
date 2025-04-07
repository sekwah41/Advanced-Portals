package com.sekwah.advancedportals.forge.connector.container;

import com.sekwah.advancedportals.core.connector.containers.GameMode;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;

import java.awt.*;
import java.util.UUID;

public class ForgePlayerContainer implements PlayerContainer {
    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendActionBar(String message) {

    }

    @Override
    public void giveItem(String material, String itemName, String... itemDescription) {

    }

    @Override
    public boolean sendPacket(String channel, byte[] bytes) {
        return false;
    }

    @Override
    public void playSound(String sound, float volume, float pitch) {

    }

    @Override
    public ServerContainer getServer() {
        return null;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public void spawnColoredDust(Vector pos, double xSpread, double ySpread, double zSpread, int count, Color color) {

    }

    @Override
    public PlayerLocation getLoc() {
        return null;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public BlockLocation getBlockLoc() {
        return null;
    }

    @Override
    public boolean teleport(PlayerLocation location) {
        return false;
    }

    @Override
    public WorldContainer getWorld() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getWorldName() {
        return "";
    }

    @Override
    public void setVelocity(Vector vector) {

    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }
}
