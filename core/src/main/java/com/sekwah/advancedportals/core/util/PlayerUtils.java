package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.Vector;

public class PlayerUtils {
    public static void throwPlayerBack(PlayerContainer player,
                                       double strength) {
        Vector playerLoc = player.getLoc().getDirection();
        player.setVelocity(
            playerLoc.setY(0).normalize().multiply(-1).setY(0.5).multiply(
                strength));
    }
}
