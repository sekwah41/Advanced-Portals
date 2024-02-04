package com.sekwah.advancedportals.core.serializeddata;

import java.util.HashMap;

/**
 * Possibly one of the only files in this package not designed to be serialised.
 *
 * Any temporary data about players will be stored here and cleaned up when the player leaves the server.
 *
 * This is not a place to store long term data e.g. if you want to make a player unable to use a portal over hours/days.
 */
public class PlayerData {

    /**
     * Portal selection position 1
     */
    private BlockLocation pos1;

    /**
     * Portal selection position 2
     */
    private BlockLocation pos2;

    /**
     * If to show portals near the player
     */
    private boolean portalVisible = false;

    /**
     * If to show destination blocks near the player
     */
    private boolean destiVisible;

    /**
     * If the player is in a portal. Stops re-triggering.
     */
    private transient boolean isInPortal = false;

    /**
     * The next time System.currentTimeMillis() a player can use a portal.
     */
    private transient long globalCooldown;

    private transient long netherPortalCooldown;

    private HashMap<String, String> perPortalCooldowns = new HashMap<>();

    private String selectedPortal;

    public BlockLocation getPos1() {
        return pos1;
    }

    public void setPos1(BlockLocation pos1) {
        this.pos1 = pos1;
    }

    public BlockLocation getPos2() {
        return pos2;
    }

    public void setPos2(BlockLocation pos2) {
        this.pos2 = pos2;
    }

    public long getGlobalCooldown() {
        return globalCooldown;
    }

    public void setGlobalCooldown(long globalCooldown) {
        this.globalCooldown = System.currentTimeMillis() + globalCooldown;
    }

    public String getSelectedPortal() {
        return selectedPortal;
    }

    public void setSelectedPortal(String selectedPortal) {
        this.selectedPortal = selectedPortal;
    }

    public boolean isPortalVisible() {
        return portalVisible;
    }

    public void setPortalVisible(boolean showPortals) {
        this.portalVisible = showPortals;
    }

    public boolean isDestiVisible() {
        return destiVisible;
    }

    public void setDestiVisible(boolean destiVisible) {
        this.destiVisible = destiVisible;
    }

    public boolean isInPortal() {
        return isInPortal;
    }

    public void setInPortal(boolean inPortal) {
        isInPortal = inPortal;
    }

    public void setNetherPortalCooldown(long netherPortalCooldown) {
        this.netherPortalCooldown = System.currentTimeMillis() + netherPortalCooldown;
    }

    public boolean isGlobalCooldown() {
        return System.currentTimeMillis() < globalCooldown;
    }

    public boolean isNetherPortalCooldown() {
        return System.currentTimeMillis() < netherPortalCooldown;
    }
}
