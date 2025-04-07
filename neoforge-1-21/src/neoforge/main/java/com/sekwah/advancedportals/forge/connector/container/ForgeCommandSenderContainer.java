package com.sekwah.advancedportals.forge.connector.container;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;

public class ForgeCommandSenderContainer implements CommandSenderContainer {
    @Override
    public void sendMessage(String message) {

    }

    @Override
    public PlayerContainer getPlayerContainer() {
        return null;
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
