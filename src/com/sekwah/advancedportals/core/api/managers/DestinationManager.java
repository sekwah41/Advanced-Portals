package com.sekwah.advancedportals.core.api.managers;

import com.google.gson.reflect.TypeToken;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.portal.DataTag;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
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

    public void createDesti(String name, PlayerContainer player, PlayerLocation playerLocation, ArrayList<DataTag> tags) throws PortalException {
        Destination destination = new Destination(playerLocation);
        if(name == null || name.equals("")) {
            throw new PortalException("desti.error.noname");
        }
        else if(this.destiHashMap.containsKey(name)) {
            throw new PortalException("desti.error.takenname");
        }

        Destination desti = new Destination(playerLocation);
        for(DataTag portalTag : tags) {
            desti.setArg(portalTag);
        }
        for(DataTag destiTag : tags) {
            TagHandler.Creation<Destination> creation = AdvancedPortalsCore.getDestinationTagRegistry().getCreationHandler(destiTag.NAME);
            if(creation != null) {
                creation.created(desti, player, destiTag.VALUE);
            }
        }
        this.destiHashMap.put(name, desti);
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
