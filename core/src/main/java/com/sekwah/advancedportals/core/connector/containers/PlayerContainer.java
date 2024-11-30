package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;

import java.awt.*;
import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data
 * from a player
 */
public interface PlayerContainer extends EntityContainer, HasPermission {
    UUID getUUID();

    void sendMessage(String message);

    void sendActionBar(String message);

    /**
     * @param blockPos
     * @param material
     */
    void sendFakeBlock(BlockLocation blockPos, String material);

    void giveItem(String material, String itemName, String... itemDescription);

    /**
     * Send packets down any channel except the minecraft: one.
     * @param channel
     * @param bytes
     * @return
     */
    boolean sendPacket(String channel, byte[] bytes);

    void playSound(String sound, float volume, float pitch);

    ServerContainer getServer();

    GameMode getGameMode();

    void spawnColoredDust(Vector pos, double xSpread, double ySpread, double zSpread, int count, Color color);
}
