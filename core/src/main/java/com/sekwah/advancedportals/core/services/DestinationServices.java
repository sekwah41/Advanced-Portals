package com.sekwah.advancedportals.core.services;


import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DestinationServices {

    @Inject
    private IDestinationRepository destinationRepository;


    @Inject
    TagRegistry tagRegistry;

    private final Map<String, Destination> destinationCache = new HashMap<>();

    public List<String> getDestinationNames() {
        return destinationRepository.getAllNames();
    }

    public List<Destination> getDestinations() {
        return new ArrayList<>(destinationCache.values());
    }

    public void loadDestinations() {
        List<String> destinationNames = destinationRepository.getAllNames();
        destinationCache.clear();
        for (String name : destinationNames) {
            Destination destination = destinationRepository.get(name);
            destinationCache.put(name, destination);
        }
    }

    public Destination createDesti(PlayerContainer player, PlayerLocation playerLocation, ArrayList<DataTag> tags) {
        // Find the tag with the "name" NAME
        DataTag nameTag = tags.stream().filter(tag -> tag.NAME.equals("name")).findFirst().orElse(null);

        String name = nameTag == null ? null : nameTag.VALUES[0];

        // If the name is null, send an error saying that the name is required.
        if (nameTag == null) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("desti.error.noname"));
            return null;
        }

        if (name == null || name.equals("")) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.error.noname"));
            return null;
        } else if (this.destinationRepository.containsKey(name)) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translateInsertVariables("command.error.nametaken", name));
            return null;
        }

        Destination desti = new Destination(playerLocation);
        for (DataTag portalTag : tags) {
            desti.setArgValues(portalTag);
        }
        for (DataTag destiTag : tags) {
            Tag.Creation creation = tagRegistry.getCreationHandler(destiTag.NAME);
            if(creation != null) {
                if(!creation.created(desti, player, destiTag.VALUES)) {
                    return null;
                }
            }
        }
        try {
            if(this.destinationRepository.save(name, desti)) {
                this.destinationCache.put(name, desti);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("desti.error.save"));
        }
        return desti;
    }

    public boolean removeDestination(String name, PlayerContainer playerContainer) {
        this.destinationCache.remove(name);
        if(this.destinationRepository.containsKey(name)) {
            this.destinationRepository.delete(name);
            return true;
        }
        return false;
    }
}
