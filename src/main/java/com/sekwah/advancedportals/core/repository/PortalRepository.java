package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

public interface PortalRepository {

    void loadPortals();

    void savePortals();

    boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc);
}
