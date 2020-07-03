package com.sekwah.advancedportals.core.api.destination;

import com.google.gson.annotations.SerializedName;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.api.warphandler.ActivationData;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.data.DataTag;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Possibly look at adding the ability to add some tags to destinations such as permissions. Would make it easier
 * to add permissions to block access to certain areas and such. Could be a different permission system or just
 * it takes the tags on the destination and automatically applies them when a portal wants to warp to there.
 * (Of course it would not work cross server unless the data was communicated and checked first however that
 * could effect performance and would definitely effect speed)
 *
 * @author sekwah41
 */
public class Destination {

    @SerializedName("l")
    private PlayerLocation loc;

    @SerializedName("a")
    private HashMap<String, String> args = new HashMap<>();

    private transient Set<String> argsCol;

    public Destination(PlayerLocation loc) {
        this.loc = loc;
    }

    public String getArg(String argName) {
        return this.args.get(argName);
    }

    public void setArg(String argName, String argValue) {
        this.args.put(argName, argValue);
    }

    public void setArg(DataTag portalTag) {
        this.setArg(portalTag.NAME, portalTag.VALUE);
    }

    public void removeArg(String arg) {
        this.args.remove(arg);
    }

    public boolean activate(PlayerContainer player) {
        ActivationData data = new ActivationData();
        this.portalActivate(player, data);
        this.postActivate(player, data);
        return true;
    }

    public boolean portalActivate(PlayerContainer player, ActivationData data) {
        TagRegistry<Destination> tagRegistry = AdvancedPortalsCore.getDestinationTagRegistry();
        DataTag[] destiTags = new DataTag[args.size()];
        int i = 0;
        for(Map.Entry<String, String> entry : args.entrySet()) {
            destiTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }
        for(DataTag destiTag : destiTags) {
            TagHandler.Activation<Destination> activationHandler = tagRegistry.getActivationHandler(destiTag.NAME);
            if(activationHandler != null) {
                activationHandler.preActivated(this, player, data, this.getArg(destiTag.NAME));
            }
        }
        for(DataTag destiTag : destiTags) {
            TagHandler.Activation<Destination> activationHandler = tagRegistry.getActivationHandler(destiTag.NAME);
            if(activationHandler != null) {
                activationHandler.activated(this, player, data, this.getArg(destiTag.NAME));
            }
        }
        return true;
    }

    public void postActivate(PlayerContainer player, ActivationData data) {
        TagRegistry<Destination> tagRegistry = AdvancedPortalsCore.getDestinationTagRegistry();
        DataTag[] destiTags = new DataTag[args.size()];
        int i = 0;
        for(Map.Entry<String, String> entry : args.entrySet()) {
            destiTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }
        for(DataTag destiTag : destiTags) {
            TagHandler.Activation<Destination> activationHandler = tagRegistry.getActivationHandler(destiTag.NAME);
            if(activationHandler != null) {
                activationHandler.postActivated(this, player, data, this.getArg(destiTag.NAME));
            }
        }
    }

    public ArrayList<DataTag> getArgs() {
        ArrayList<DataTag> tagList = new ArrayList<>();
        for(Map.Entry<String, String> entry : this.args.entrySet()){
            tagList.add(new DataTag(entry.getKey(), entry.getValue()));
        }
        return tagList;
    }
}
