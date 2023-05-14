package com.sekwah.advancedportals.core.portal;

import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.WorldLocation;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.TagHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sekwah41
 */
public class AdvancedPortal {

    @Inject
    TagRegistry<AdvancedPortal> tagRegistry;

    @SerializedName("max")
    private WorldLocation maxLoc;

    @SerializedName("min")
    private WorldLocation minLoc;

    @SerializedName("t")
    private String[] triggerBlocks = {"PORTAL"};

    @SerializedName("a")
    private HashMap<String, String> args = new HashMap<>();

    public AdvancedPortal(WorldLocation maxLoc, WorldLocation minLoc) {
        this.maxLoc = maxLoc;
        this.minLoc = minLoc;
    }

    public WorldLocation getMaxLoc() {
        return this.maxLoc;
    }

    public WorldLocation getMinLoc() {
        return this.minLoc;
    }

    public String getArg(String argName) {
        return this.args.get(argName);
    }

    public void setArg(String argName, String argValue) {
        this.args.put(argName, argValue);
    }

    public void removeArg(String arg) {
        this.args.remove(arg);
    }

    public boolean hasTriggerBlock(String blockMaterial) {
        for(String triggerBlock : triggerBlocks) {
            if(blockMaterial.equals(triggerBlock)) {
                return true;
            }
        }
        return false;
    }

    public boolean activate(PlayerContainer player) {
        ActivationData data = new ActivationData();
        DataTag[] portalTags = new DataTag[args.size()];
        int i = 0;
        for(Map.Entry<String, String> entry : args.entrySet()) {
            portalTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }

        for(DataTag portalTag : portalTags) {
            TagHandler.Activation<AdvancedPortal> activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.preActivated(this, player, data, this.getArg(portalTag.NAME));
            }
        }
        for(DataTag portalTag : portalTags) {
            TagHandler.Activation<AdvancedPortal> activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.activated(this, player, data, this.getArg(portalTag.NAME));
            }
        }
        for(DataTag portalTag : portalTags) {
            TagHandler.Activation<AdvancedPortal> activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.postActivated(this, player, data, this.getArg(portalTag.NAME));
            }
        }
        return true;
    }

    public void setArg(DataTag portalTag) {
        this.setArg(portalTag.NAME, portalTag.VALUE);
    }

    public ArrayList<DataTag> getArgs() {
        ArrayList<DataTag> tagList = new ArrayList<>();
        for(Map.Entry<String, String> entry : this.args.entrySet()){
            tagList.add(new DataTag(entry.getKey(), entry.getValue()));
        }
        return tagList;
    }

    public void setTriggerBlocks(String[] triggerBlocks) {
        this.triggerBlocks = triggerBlocks;
    }

    public String[] getTriggerBlocks() {
        return triggerBlocks;
    }
}
