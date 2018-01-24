package com.sekwah.advancedportals.core.api.managers;

import com.google.gson.reflect.TypeToken;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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

    private static PortalManager instance;

    private final AdvancedPortalsCore portalsCore;

    /**
     * Store data of when the player last entered the portal
     */
    private HashMap<String, Long> lastAttempt = new HashMap();
    /**
     * Tracks what portal a player has selected
     */
    private HashMap<String, AdvancedPortal> selectedPortal = new HashMap();

    /**
     * Contains all the data for the portals
     */
    private HashMap<String, AdvancedPortal> portalHashMap;

    public PortalManager(AdvancedPortalsCore portalsCore) {
        this.instance = this;
        this.portalsCore = portalsCore;
    }

    /**
     * A player has left the server
     *
     * @param player
     */
    public void playerLeave(PlayerContainer player) {
        this.lastAttempt.remove(player.getUUID());
        this.selectedPortal.remove(player.getUUID());
    }

    /**
     * Load the default data into the portals.
     */
    public void loadPortals() {
        Type type = new TypeToken<Map<String, AdvancedPortal>>(){}.getType();
        this.portalHashMap = this.portalsCore.getDataStorage().loadJson(type ,"portals.json");
        if(this.portalHashMap == null) {
            this.portalHashMap = new HashMap<>();
        }
        this.portalsCore.getDataStorage().storeJson(this.portalHashMap, "config.json");
    }
}
