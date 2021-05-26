package com.sekwah.advancedportals.core.data;

/**
 * Possibly one of the only files in this package not designed to be serialised.
 *
 * Any temporary data about players will be stored here and cleaned up when the player leaves the server.
 *
 * This is not a place to store long term data e.g. if you want to make a player unable to use a portal over hours/days.
 */
public class PlayerTempData {

    private BlockLocation pos1;

    private BlockLocation pos2;

    private long lastAttempt;

    private String selectedPortal;

}
