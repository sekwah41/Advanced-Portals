package com.sekwah.advancedportals.core.services;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.util.Lang;

import javax.inject.Singleton;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handles logic for all destination, this is a transient layer so it should
 * not store any information.
 */
@Singleton
public class DestinationServices {

    @Inject
    private IDestinationRepository destinationRepository;

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

    public List<String> getDestinations() {
        return destinationRepository.listAll();
    }

    public Destination createDesti(PlayerContainer player, PlayerLocation playerLocation, ArrayList<DataTag> tags) {
        // Find the tag with the "name" NAME
        DataTag nameTag = tags.stream().filter(tag -> tag.NAME.equals("name")).findFirst().orElse(null);

        String name = nameTag == null ? null : nameTag.VALUES[0];

        // If the name is null, send an error saying that the name is required.
        if(nameTag == null) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("desti.error.noname"));
            return null;
        }

        if(name == null || name.equals("")) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.error.noname"));
            return null;
        }
        else if(this.destinationRepository.containsKey(name)) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translateInsertVariables("command.error.nametaken", name));
            return null;
        }

        Destination desti = new Destination(playerLocation);
        for(DataTag portalTag : tags) {
            desti.setArgValues(portalTag);
        }
        for(DataTag destiTag : tags) {
            // TODO sort tag handle registry
            /*TagHandler.Creation<Destination> creation = AdvancedPortalsCore.getDestinationTagRegistry().getCreationHandler(destiTag.NAME);
            if(creation != null) {
                creation.created(desti, player, destiTag.VALUE);
            }*/
        }
        try {
            this.destinationRepository.addDestination(name, desti);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("desti.error.save"));
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

    public boolean removeDesti(String name, PlayerContainer playerContainer) {
        if(this.destinationRepository.containsKey(name)) {
            this.destinationRepository.delete(name);
            return true;
        }
        return false;
    }
}
