package com.sekwah.advancedportals.core.api.portal;

/**
 * Returns a message saying what went wrong with the portal tag.
 */
public class PortalTagExeption extends Exception {
    public PortalTagExeption(String reason) {
        super(reason);
    }
}
