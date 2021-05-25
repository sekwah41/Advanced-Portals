package com.sekwah.advancedportals.core.api.portal;

import com.google.gson.annotations.SerializedName;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.api.warphandler.ActivationData;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.data.DataTag;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;
import com.sekwah.advancedportals.core.util.TagReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sekwah41
 */
public class AdvancedPortal {

    @SerializedName("max")
    private PortalLocation maxLoc;

    @SerializedName("min")
    private PortalLocation minLoc;

    @SerializedName("t")
    private String[] triggerBlocks = {"PORTAL"};

    @SerializedName("a")
    private HashMap<String, String> args = new HashMap<>();

    public AdvancedPortal(PortalLocation maxLoc, PortalLocation minLoc) {
        this.maxLoc = maxLoc;
        this.minLoc = minLoc;
    }

    public PortalLocation getMaxLoc() {
        return this.maxLoc;
    }

    public PortalLocation getMinLoc() {
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
        TagRegistry<AdvancedPortal> tagRegistry = AdvancedPortalsCore.getPortalTagRegistry();
        ActivationData data = new ActivationData();
        List<DataTag> portalTags = getArgs();
        
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

    public List<DataTag> getArgs() {
        return TagReader.getTagsFromArgsMap(args);
    }

    public void setTriggerBlocks(String[] triggerBlocks) {
        this.triggerBlocks = triggerBlocks;
    }

    public String[] getTriggerBlocks() {
        return triggerBlocks;
    }
}
