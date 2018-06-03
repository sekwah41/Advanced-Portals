package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;

import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public class PlayerContainer {

    public UUID getUUID() {
        return null;
    }
    
    public void sendMessage(String message) {}

    public boolean isOp() {
        return false;
    }

    public PlayerLocation getLoc() {
        return null;
    }

    public double getEyeHeight() {
        return 0;
    }

    public void teleport(PlayerLocation location) {}

    public boolean hasPermission(String permission) {
        return false;
    }

    public WorldContainer getWorld() {return null;}

    /**
     * @param blockPos
     * @param material
     */
    public void sendFakeBlock(PortalLocation blockPos, String material) {

    }

    /**
     * Only 1.12 and below supported
     * @param blockPos
     * @param material
     * @param data
     */
    public void sendFakeBlockWithData(PortalLocation blockPos, String material, byte data) {

    }

    public void giveItem(String material, String itemName, String... itemDescription) {}

    public void giveWool(String dyeColor, String itemName, String... itemDescription) {}
}
