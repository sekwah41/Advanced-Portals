package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;

public class PlayerUtils {
    public static void throwPlayerBack(PlayerContainer player,
                                       double strength) {
        com.sekwah.advancedportals.core.serializeddata.Vector playerLoc = player.getLoc().getDirection();
        player.setVelocity(
            playerLoc.setY(0).normalize().multiply(-1).setY(0.5).multiply(
                strength));
    }
}
