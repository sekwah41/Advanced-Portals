package com.sekwah.advancedportals.repository;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.DataTag;
import com.sekwah.advancedportals.PlayerLocation;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.api.destination.Destination;
import com.sekwah.advancedportals.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Deprecated
public class DestinationRepositoryImpl2 implements DestinationRepositoryOld {


    @Inject
    private AdvancedPortalsCore portalsCore;


    public void create(String name, Destination destination) {
        destiHashMap.put(name, destination);
    }

    @Override
    public void delete(String name) {
        destiHashMap.remove(name);
    }

    @Override
    public ImmutableMap<String, Destination> getDestinations() {
        return ImmutableMap.copyOf(destiHashMap);
    }


    public Destination createDesti(String name, PlayerContainer player, PlayerLocation playerLocation, ArrayList<DataTag> tags) {
        // TODO change to write messages
        if(name == null || name.equals("")) {
            player.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("desti.error.noname"));
            return null;
        }
        else if(this.destiHashMap.containsKey(name)) {
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
        this.destiHashMap.put(name, desti);
        this.saveDestinations();
        return desti;
    }

    public void loadDestinations() {
        Type type = new TypeToken<HashMap<String, Destination>>() {
        }.getType();
        this.destiHashMap = this.portalsCore.getDataStorage().loadJson(type, "destinations.json");
        this.saveDestinations();
    }

    public void saveDestinations() {
        if (this.destiHashMap == null) {
            this.destiHashMap = new HashMap<>();
        }
        this.portalsCore.getDataStorage().storeJson(this.destiHashMap, "destinations.json");
    }
}
