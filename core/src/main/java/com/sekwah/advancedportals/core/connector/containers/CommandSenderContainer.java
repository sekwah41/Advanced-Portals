package com.sekwah.advancedportals.core.connector.containers;

public interface CommandSenderContainer {
    void sendMessage(String message);

    boolean isOp();

    /**
     * @return null if there isnt a player e.g. the console
     */
    PlayerContainer getPlayerContainer();

    boolean hasPermission(String permission);
}
