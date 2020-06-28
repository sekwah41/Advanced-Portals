package com.sekwah.advancedportals.api.services;

import com.sekwah.advancedportals.api.destination.Destination;
import com.sekwah.advancedportals.DataTag;
import com.sekwah.advancedportals.PlayerLocation;
import com.sekwah.advancedportals.repository.DestinationRepositoryOld;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList; /**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/DestinationManager.java
 * Based off the old manager with the data storage and handling moved to {@link DestinationRepositoryOld}
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
