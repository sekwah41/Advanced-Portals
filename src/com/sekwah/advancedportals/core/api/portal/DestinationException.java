package com.sekwah.advancedportals.core.api.portal;

/**
 * Returns a message saying what went wrong with the portal tag.
 */
public class DestinationException extends Exception {
    public DestinationException(String reason) {
        super(reason);
    }
}
