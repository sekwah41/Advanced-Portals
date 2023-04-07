package com.sekwah.advancedportals.core.services;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.data.BlockLocation;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.repository.IPortalRepository;

import java.util.UUID;

public class PortalServices {

    private final IPortalRepository portalRepository;

    @Inject
    public PortalServices(IPortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    public void addSelectedPortal(UUID selectedPlayer, String portal) {
        //portalRepository.save(selectedPlayer, portal);
    }

    public void removeSelectedPortal(UUID uuid) {
        //selectedPortal.invalidate(uuid);
    }

    public void addSelectedPosition(UUID uuid, boolean isPos1, BlockLocation portalLocation) {
        //selectedPositions.put(uuid, isPos1, portalLocation);
    }

    public void removeSelectedPosition(UUID uuid, boolean isPos1) {
        //selectedPositions.remove(uuid, isPos1);
    }

    public void removeAllSelectedHand(UUID uuid) {
        //selectedPositions.remove(uuid, true);
        //selectedPositions.remove(uuid, false);
    }

    public void activateCooldown(PlayerContainer player) {

    }

    public void playerLeave(PlayerContainer player) {

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }
}
