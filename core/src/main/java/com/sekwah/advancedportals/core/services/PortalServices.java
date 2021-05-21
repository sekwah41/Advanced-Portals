package com.sekwah.advancedportals.core.services;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.repository.IPortalRepository;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.data.DataTag;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.Map;

/**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/PortalManager.java
 *
 * Based off the old manager with the data storage and handling moved to {@link IPortalRepository}
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
