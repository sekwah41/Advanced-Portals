package com.sekwah.advancedportals.core.repository;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

@Singleton
public class PortalRepositoryImpl implements PortalRepository {
    @Override
    public void loadPortals() {

    }

    @Override
    public void savePortals() {

    }

    @Override
    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }
}
