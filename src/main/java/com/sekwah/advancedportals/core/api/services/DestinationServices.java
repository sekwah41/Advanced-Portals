package com.sekwah.advancedportals.core.api.services;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.entities.DataTag;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList; /**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/DestinationManager.java
 * Based off the old manager with the data storage and handling moved to {@link com.sekwah.advancedportals.core.repository.DestinationRepository}
 */
public class DestinationServices {

    /**
     * @param portalsCore
     */
    public void loadDestinations(AdvancedPortalsCore portalsCore) {

    }

    /**
     * @param name
     * @param player
     * @param loc
     * @param destiTags
     * @return
     */
    public Destination createDesti(String name, PlayerContainer player, PlayerLocation loc, ArrayList<DataTag> destiTags) throws PortalException {
        return null;
    }
}
