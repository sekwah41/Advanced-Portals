package com.sekwah.advancedportals.services;


import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import com.sekwah.advancedportals.core.data.DataTag;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;
import com.sekwah.advancedportals.repository.DestinationRepository;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles logic for all destination, this is a transient layer so it should
 * not store any information.
 */
public class DestinationServices {

    DestinationRepository destinationRepository;

    @Inject
    private DestinationServices(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Response.Creation create(String name, Destination destination) {
        if (!destinationRepository.containsKey(name)) {
            destinationRepository.save(name, destination);
            return Response.Creation.SUCCESS;
        }
        return Response.Creation.NAME_IN_USE;
    }

    public Boolean delete(String name) {
        if (!destinationRepository.containsKey(name)) {
            destinationRepository.delete(name);
        }
        return false;
    }

    public ImmutableMap<String, Destination> getDestination() {
        return destinationRepository.get("");
    }

    public ImmutableMap<String, Destination> getDestinations() {
        return ImmutableMap.copyOf(destinationRepository.get(""));
    }




    public Destination createDesti(String name, PlayerContainer player, PlayerLocation playerLocation, ArrayList<DataTag> tags) {
        // TODO change to write messages
        if(name == null || name.equals("")) {
            player.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("desti.error.noname"));
            return null;
        }
        else if(this.destinationRepository.containsKey(name)) {
            player.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("desti.error.takenname"));
            return null;
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
        try {
            this.destinationRepository.AddDestination(name, desti);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.saveDestinations();
        return desti;
    }

    //TODO Change to repository

    public void loadDestinations() {
        Type type = new TypeToken<HashMap<String, Destination>>() {
        }.getType();
        //this.destiHashMap = this.portalsCore.getDataStorage().loadJson(type, "destinations.json");
        this.saveDestinations();
    }

    public void saveDestinations() {
        /*if (this.destiHashMap == null) {
            this.destiHashMap = new HashMap<>();
        }
        this.portalsCore.getDataStorage().storeJson(this.destiHashMap, "destinations.json");*/
    }
}
