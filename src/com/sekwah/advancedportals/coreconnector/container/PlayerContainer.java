package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.data.PlayerLocation;

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

    public void teleport(PlayerLocation location) {}

    public boolean hasPermission(String permission) {
        return false;
    }

    public void giveItem(String material, String itemName, String... itemDescription) {}
}
