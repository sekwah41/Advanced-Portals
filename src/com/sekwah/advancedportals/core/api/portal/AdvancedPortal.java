package com.sekwah.advancedportals.core.api.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.registry.TagRegistry;
import com.sekwah.advancedportals.core.api.warphandler.ActivationData;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sekwah41
 */
public class AdvancedPortal {
    private PortalLocation maxLoc;

    private PortalLocation minLoc;

    private String triggerBlock;

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
        TagRegistry tagRegistry = AdvancedPortalsCore.getTagRegistry();
        ActivationData data = new ActivationData();
        PortalTag[] portalTags = new PortalTag[argsCol.size()];
        String[] argNames = argsCol.toArray(new String[argsCol.size()]);
        for (int i = 0; i < argNames.length; i++) {
            portalTags[i] = new PortalTag(argNames[i], this.getArg(argNames[i]));
        }
        for(PortalTag portalTag : portalTags) {
            TagHandler.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            activationHandler.portalPreActivated(this, player, data, this.getArg(portalTag.NAME));
        }
        for(PortalTag portalTag : portalTags) {
            TagHandler.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            activationHandler.portalActivated(this, player, data, this.getArg(portalTag.NAME));
        }
        for(PortalTag portalTag : portalTags) {
            TagHandler.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            activationHandler.portalPostActivated(this, player, data, this.getArg(portalTag.NAME));
        }
        return true;
    }

    public void setArg(PortalTag portalTag) {
        this.setArg(portalTag.NAME, portalTag.VALUE);
    }
}
