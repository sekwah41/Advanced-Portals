package com.sekwah.advancedportals.core.api.managers;

import com.google.gson.reflect.TypeToken;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.portal.DataTag;
import com.sekwah.advancedportals.core.api.portal.DestinationException;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public void createDesti(PlayerContainer player, PlayerLocation playerLocation, ArrayList<DataTag> dataTags) throws DestinationException {

    }

    public void loadDestinations() {
        Type type = new TypeToken<HashMap<String, Destination>>() {
        }.getType();
        this.destiHashMap = this.portalsCore.getDataStorage().loadJson(type, "destinations.json");
        if (this.destiHashMap == null) {
            this.destiHashMap = new HashMap<>();
        }
        this.portalsCore.getDataStorage().storeJson(this.destiHashMap, "destinations.json");
    }
}
