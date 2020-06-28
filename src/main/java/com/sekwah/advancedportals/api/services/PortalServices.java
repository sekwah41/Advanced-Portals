package com.sekwah.advancedportals.api.services;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.repository.PortalRepository;
import com.sekwah.advancedportals.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.DataTag;
import com.sekwah.advancedportals.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.Map;

/**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/PortalManager.java
 *
 * Based off the old manager with the data storage and handling moved to {@link PortalRepository}
 *
 * Excluding the temp data like selections
 */
public final class PortalServices {



    public void loadPortals() {

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }

    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }

    public ImmutableList<? extends Map.Entry<String, AdvancedPortal>> getPortals() {
        return null;
    }

    public boolean removePortal(String name, PlayerContainer player) {
        return false;
    }

    public AdvancedPortal createPortal(String name, PlayerContainer player, ArrayList<DataTag> portalTags) {
        return null;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }
}
