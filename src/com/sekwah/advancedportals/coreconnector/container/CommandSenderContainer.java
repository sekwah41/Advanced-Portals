package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.util.Lang;

public class CommandSenderContainer {
    public void sendMessage(String message) {
    }

    public boolean isOp() {
        return false;
    }

    /**
     * @return null if there isnt a player e.g. the console
     */
    public PlayerContainer getPlayerContainer() {
        return null;
    }

    public boolean hasPermission(String permission) {
        return false;
    }
}
