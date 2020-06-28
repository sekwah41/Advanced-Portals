package com.sekwah.advancedportals.services;

import com.google.inject.Inject;
import com.sekwah.advancedportals.PlayerLocation;
import com.sekwah.advancedportals.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import com.sekwah.advancedportals.repository.PortalRepository;

import java.util.UUID;

public class PortalServices {

    private final PortalRepository portalRepository;

    @Inject
    public PortalServices(PortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    public void addSelectedPortal(UUID selectedPlayer, String portal) {
        //portalRepository.save(selectedPlayer, portal);
    }

    public void removeSelectedPortal(UUID uuid) {
        selectedPortal.invalidate(uuid);
    }

    public void addSelectedPosition(UUID uuid, boolean isPos1, PortalLocation portalLocation) {
        selectedPositions.put(uuid, isPos1, portalLocation);
    }

    public void removeSelectedPosition(UUID uuid, boolean isPos1) {
        selectedPositions.remove(uuid, isPos1);
    }

    public void removeAllSelectedHand(UUID uuid) {
        selectedPositions.remove(uuid, true);
        selectedPositions.remove(uuid, false);
    }

    public void activateCooldown(PlayerContainer player) {

    }

    public void playerLeave(PlayerContainer player) {

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }
}
