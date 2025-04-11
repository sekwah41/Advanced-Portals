package com.sekwah.advancedportals.core.connector.containers;

public interface CommandSenderContainer extends HasPermission {
    void sendMessage(String message);
    
    ServerContainer getServer();

    /**
     * @return null if there isn't a player e.g. the console
     */
    PlayerContainer getPlayerContainer();
}
