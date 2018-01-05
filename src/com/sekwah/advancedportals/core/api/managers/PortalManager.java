package com.sekwah.advancedportals.core.api.managers;

import com.sekwah.advancedportals.core.api.portal.Portal;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.HashMap;

/**
 * When a player leaves the server any data stored on them is removed to free memory.
 *
 * @author sekwah41
 */
public class PortalManager {

    /**
     * Cooldown time for players to try entering portals.
     */
    private static final int COOLDOWN = 0;

    private static PortalManager instance = new PortalManager();
    /**
     * Store data of when the player last entered the portal
     */
    private HashMap<String, Long> lastAttempt = new HashMap();
    /**
     * Tracks what portal a player has selected
     */
    private HashMap<String, Portal> selectedPortal = new HashMap();

    public PortalManager() {
        this.loadPortals();
    }

    /**
     * A player has left the server
     *
     * @param player
     */
    public static void playerLeave(PlayerContainer player) {
        instance.lastAttempt.remove(player.getUUID());
        instance.selectedPortal.remove(player.getUUID());
    }

    /**
     * Load the default data into the portals.
     */
    private void loadPortals() {

    }
}
