package com.sekwah.advancedportals.velocity.connector.container;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class VelocityPlayerContainer implements PlayerContainer {

    private final Player player;

    public VelocityPlayerContainer(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(Component.text(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public ServerContainer getServer() {
        return new VelocityServerContainer(null, player.getCurrentServer().orElse(null));
    }

    // Don't use in Velocity

    @Override
    public boolean isOp() {
        return false;
    }

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
}
