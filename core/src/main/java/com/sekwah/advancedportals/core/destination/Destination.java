package com.sekwah.advancedportals.core.destination;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Possibly look at adding the ability to add some tags to destinations such as permissions. Would
 * make it easier to add permissions to block access to certain areas and such. Could be a different
 * permission system or just it takes the tags on the destination and automatically applies them
 * when a portal wants to warp to there. (Of course it would not work cross server unless the data
 * was communicated and checked first however that could affect performance and would definitely
 * affect speed)
 *
 * @author sekwah41
 */
public class Destination implements TagTarget {

    @Inject transient TagRegistry tagRegistry;

    private PlayerLocation loc;

    private HashMap<String, String[]> args = new HashMap<>();

    private transient Set<String> argsCol;

    public Destination() {
        this.loc = new PlayerLocation();
    }

    public Destination(PlayerLocation loc) {
        this.loc = loc;
    }

    @Override
    public String[] getArgValues(String argName) {
        return this.args.get(argName);
    }

    @Override
    public void setArgValues(String argName, String[] argValue) {
        this.args.put(argName, argValue);
    }

    @Override
    public void addArg(String argName, String argValues) {}

    public void setArgValues(DataTag portalTag) {
        this.setArgValues(portalTag.NAME, portalTag.VALUES);
    }

    public void removeArg(String arg) {
        this.args.remove(arg);
    }

    public boolean activate(PlayerContainer player) {
        ActivationData data = new ActivationData(false);
        this.portalActivate(player, data);
        this.postActivate(player, data);
        return true;
    }

    public boolean portalActivate(PlayerContainer player, ActivationData data) {
        DataTag[] destiTags = new DataTag[args.size()];
        int i = 0;
        for (Map.Entry<String, String[]> entry : args.entrySet()) {
            destiTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }
        for (DataTag destiTag : destiTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(destiTag.NAME);
            if (activationHandler != null) {
                activationHandler.preActivated(
                        this, player, data, this.getArgValues(destiTag.NAME));
            }
        }
        for (DataTag destiTag : destiTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(destiTag.NAME);
            if (activationHandler != null) {
                activationHandler.activated(this, player, data, this.getArgValues(destiTag.NAME));
            }
        }
        return true;
    }

    public void postActivate(PlayerContainer player, ActivationData data) {
        DataTag[] destiTags = new DataTag[args.size()];
        int i = 0;
        for (Map.Entry<String, String[]> entry : args.entrySet()) {
            destiTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }
        for (DataTag destiTag : destiTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(destiTag.NAME);
            if (activationHandler != null) {
                activationHandler.postActivated(
                        this, player, data, this.getArgValues(destiTag.NAME));
            }
        }
    }

    public ArrayList<DataTag> getArgs() {
        ArrayList<DataTag> tagList = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : this.args.entrySet()) {
            tagList.add(new DataTag(entry.getKey(), entry.getValue()));
        }
        return tagList;
    }

    public PlayerLocation getLoc() {
        return loc;
    }

    public void setLoc(PlayerLocation loc) {
        this.loc = loc;
    }
}
