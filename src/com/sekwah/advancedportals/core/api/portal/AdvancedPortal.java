package com.sekwah.advancedportals.core.api.portal;

import com.google.gson.annotations.SerializedName;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.registry.TagRegistry;
import com.sekwah.advancedportals.core.api.warphandler.ActivationData;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sekwah41
 */
public class AdvancedPortal {

    @SerializedName("max")
    private PortalLocation maxLoc;

    @SerializedName("min")
    private PortalLocation minLoc;

    @SerializedName("t")
    private String triggerBlock;

    @SerializedName("a")
    private HashMap<String, String> args = new HashMap<>();

    private transient Set<String> argsCol;

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
        this.updateArgsCol();
    }

    private void updateArgsCol() {
        this.argsCol = this.args.keySet();
    }

    public void removeArg(String arg) {
        this.args.remove(arg);
        this.updateArgsCol();
    }

    public boolean activatePortal(PlayerContainer player) {
        TagRegistry tagRegistry = AdvancedPortalsCore.getPortalTagRegistry();
        ActivationData data = new ActivationData();
        DataTag[] portalTags = new DataTag[argsCol.size()];
        String[] argNames = argsCol.toArray(new String[argsCol.size()]);
        for (int i = 0; i < argNames.length; i++) {
            portalTags[i] = new DataTag(argNames[i], this.getArg(argNames[i]));
        }
        try {
            for(DataTag portalTag : portalTags) {
                TagHandler.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
                activationHandler.preActivated(this, player, data, this.getArg(portalTag.NAME));
            }
            for(DataTag portalTag : portalTags) {
                TagHandler.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
                activationHandler.activated(this, player, data, this.getArg(portalTag.NAME));
            }
        }
        catch(PortalException e) {
            // TODO add portal error message
        }
        for(DataTag portalTag : portalTags) {
            TagHandler.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            activationHandler.postActivated(this, player, data, this.getArg(portalTag.NAME));
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
        return tagList ;
    }
}
