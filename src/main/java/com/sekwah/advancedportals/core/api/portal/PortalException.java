package com.sekwah.advancedportals.core.api.portal;

/**
 * Returns a message saying what went wrong with the portal or the action was blocked for a reason.
 */
public class PortalException extends Exception {
    public PortalException(String reason) {
        super(reason);
    }
}
