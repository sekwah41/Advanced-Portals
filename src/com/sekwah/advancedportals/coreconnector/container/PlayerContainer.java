package com.sekwah.advancedportals.coreconnector.container;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public class PlayerContainer {

    private final Player player;

    public PlayerContainer(Player player) {
        this.player = player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }
    
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public boolean isOp() {
        return player.isOp();
    }
}
