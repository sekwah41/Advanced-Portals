package com.sekwah.advancedportals.core.data;

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
     * Used for things like join cooldowns
     * TODO either store a hashmap of cool-downs on a portal, or a hashmap of cool-downs for portals on a player
     *          Can be switched at a later date
     */
    private long globalCooldown;

    private String selectedPortal;

}
