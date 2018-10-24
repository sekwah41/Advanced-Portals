package com.sekwah.advancedportals.core.api.services;

import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.entities.DataTag;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList; /**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/DestinationManager.java
 * Based off the old manager with the data storage and handling moved to {@link com.sekwah.advancedportals.core.repository.DestinationRepository}
 */
public final class DestinationServices {

    /**
     */
    public void loadDestinations() {

    }

    /**
     * @param name
     * @param player
     * @param loc
     * @param destiTags
     * @return null if not created
     */
    public Destination createDesti(String name, PlayerContainer player, PlayerLocation loc, ArrayList<DataTag> destiTags) {
        return null;
    }
}
