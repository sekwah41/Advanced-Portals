package com.sekwah.advancedportals.core.portal;

import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sekwah41
 */
public class AdvancedPortal implements TagTarget {

    @Inject
    transient TagRegistry tagRegistry;

    @SerializedName("max")
    private BlockLocation maxLoc;

    @SerializedName("min")
    private BlockLocation minLoc;

    @SerializedName("t")
    private String[] triggerBlocks = {"PORTAL"};

    @SerializedName("a")
    private HashMap<String, String[]> args = new HashMap<>();

    public AdvancedPortal(BlockLocation maxLoc, BlockLocation minLoc) {
        this.maxLoc = maxLoc;
        this.minLoc = minLoc;
    }

    public BlockLocation getMaxLoc() {
        return this.maxLoc;
    }

    public BlockLocation getMinLoc() {
        return this.minLoc;
    }

    @Override
    public String[] getArgValues(String argName) {
        return this.args.get(argName);
    }

    @Override
    public void setArgValues(String argName, String[] argValues) {
        this.args.put(argName, argValues);
    }

    @Override
    public void addArg(String argName, String argValues) {

    }

    @Override
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
        for(Map.Entry<String, String[]> entry : args.entrySet()) {
            portalTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }

        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.preActivated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.activated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.postActivated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        return true;
    }

    public void setArgValues(DataTag portalTag) {
        this.setArgValues(portalTag.NAME, portalTag.VALUES);
    }

    public ArrayList<DataTag> getArgs() {
        ArrayList<DataTag> tagList = new ArrayList<>();
        for(Map.Entry<String, String[]> entry : this.args.entrySet()){
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
