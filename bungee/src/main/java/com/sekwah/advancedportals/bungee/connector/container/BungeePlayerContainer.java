package com.sekwah.advancedportals.bungee.connector.container;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePlayerContainer implements PlayerContainer {
    private final ProxiedPlayer player;

    public BungeePlayerContainer(ProxiedPlayer player) {
        this.player = player;
    }

    // Do we need this? Let's find out
    @Override
    public ServerContainer getServer() {
        return null;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    // Don't use the following in Bungee

    @Override
    public void sendFakeBlock(BlockLocation blockPos, String material) {

    }

    @Override
    public void sendFakeBlockWithData(BlockLocation blockPos, String material, byte data) {

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
    public String getWorldName() {
        return null;
    }

    @Override
    public void setVelocity(Vector vector) {

    }

    @Override
    public boolean isOp() {
        return false;
    }
}
