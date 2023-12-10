package com.sekwah.advancedportals.core.connector.containers;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;

import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public interface PlayerContainer {

    UUID getUUID();

    public void sendMessage(String message);

    boolean isOp();

    PlayerLocation getLoc();

    BlockLocation getBlockLoc();

    double getEyeHeight();

    void teleport(PlayerLocation location);

    boolean hasPermission(String permission);

    WorldContainer getWorld();

    /**
     * @param blockPos
     * @param material
     */
    void sendFakeBlock(BlockLocation blockPos, String material);

    /**
     * Only 1.12 and below supported
     * @param blockPos
     * @param material
     * @param data
     */
    void sendFakeBlockWithData(BlockLocation blockPos, String material, byte data);

    void giveItem(String material, String itemName, String... itemDescription);

    boolean sendPacket(String channel, byte[] bytes);
}
