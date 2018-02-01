package com.sekwah.advancedportals.core.api.portal;

/**
 * Returns a message saying what went wrong with the portal tag.
 */
public class PortalException extends Exception {
    public PortalException(String reason) {
        super(reason);
    }
}
