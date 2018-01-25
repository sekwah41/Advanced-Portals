package com.sekwah.advancedportals.core.api.managers;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.destination.Destination;

import java.util.HashMap;

/**
 * @author sekwah41
 */
public class DestinationManager {

    private final AdvancedPortalsCore portalsCore;
    /**
     * Contains all the data for the destinations
     */
    private HashMap<String, Destination> destiHashMap = new HashMap<>();

    public DestinationManager(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }
}
