package com.sekwah.advancedportals.core.serializeddata;

/**
 * Possibly one of the only files in this package not designed to be serialised.
 *
 * Any temporary data about players will be stored here and cleaned up when the player leaves the server.
 *
 * This is not a place to store long term data e.g. if you want to make a player unable to use a portal over hours/days.
 */
public class PlayerTempData {

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
    private boolean portalVisible;

    /**
     * If to show destination blocks near the player
     */
    private boolean destiVisible;

    /**
     * Used for things like join cooldowns
     * TODO either store a hashmap of cool-downs on a portal, or a hashmap of cool-downs for portals on a player
     *          Can be switched at a later date
     */
    private long globalCooldown;

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
        this.globalCooldown = globalCooldown;
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
}
