package com.sekwah.advancedportals.core.services;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Singleton
public class PortalServices {

    @Inject
    InfoLogger infoLogger;

    public void loadPortals() {

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }

    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }

    public ImmutableList<? extends Map.Entry<String, AdvancedPortal>> getPortals() {
        return ImmutableList.copyOf(Collections.emptyList());
    }

    public boolean removePortal(String name, PlayerContainer player) {
        return false;
    }

    public AdvancedPortal createPortal(String name, PlayerContainer player, ArrayList<DataTag> portalTags) {
        if(name == null){
            infoLogger.logWarning("Attempted to make a portal with no name");
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.error.noname"));
            return null;
        }
        return null;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }
}
