package com.sekwah.advancedportals.core.api.managers;

import com.google.gson.reflect.TypeToken;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.data.PortalLocation;
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

    private final AdvancedPortalsCore portalsCore;

    /**
     * Store data of when the player last entered the portal
     */
    private HashMap<String, Long> lastAttempt = new HashMap();
    /**
     * Tracks what portal a player has selected
     */
    private HashMap<String, AdvancedPortal> selectedPortal = new HashMap();

    private HashMap<String, PortalLocation> portalSelectorLeftClick = new HashMap();
    private HashMap<String, PortalLocation> portalSelectorRightClick = new HashMap();

    /**
     * Contains all the data for the portals
     */
    private HashMap<String, AdvancedPortal> portalHashMap;

    private AdvancedPortal[] portals;

    public PortalManager(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }

    /**
     * A player has left the server
     *
     * @param player
     */
    public void playerLeave(PlayerContainer player) {
        this.lastAttempt.remove(player.getUUID().toString());
        this.selectedPortal.remove(player.getUUID().toString());
        this.portalSelectorLeftClick.remove(player.getUUID().toString());
        this.portalSelectorRightClick.remove(player.getUUID().toString());
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

    public void playerJoin(PlayerContainer player) {
        this.lastAttempt.put(player.getUUID().toString(), System.currentTimeMillis());
    }

    public void playerSelectorActivate(PlayerContainer player, PortalLocation blockLoc, boolean leftClick) {

    }

    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }
}
