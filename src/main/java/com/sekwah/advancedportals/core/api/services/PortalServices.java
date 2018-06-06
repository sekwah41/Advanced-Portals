package com.sekwah.advancedportals.core.api.services;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.entities.DataTag;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.Map;

/**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/PortalManager.java
 *
 * Based off the old manager with the data storage and handling moved to {@link com.sekwah.advancedportals.core.repository.PortalRepository}
 *
 * Excluding the temp data like selections
 */
public final class PortalServices {
    public void loadPortals(AdvancedPortalsCore advancedPortalsCore) {

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

    public void removePortal(String name, PlayerContainer player) throws PortalException {

    }

    public AdvancedPortal createPortal(String name, PlayerContainer player, ArrayList<DataTag> portalTags) throws PortalException {
        return null;
    }

    public void removePlayerSelection(PlayerContainer player) throws PortalException {

    }
}
