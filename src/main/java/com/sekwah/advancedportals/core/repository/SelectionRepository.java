package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.core.enums.EnumHandSelection;

import java.util.UUID;

public interface SelectionRepository {
    void addSelectedPortal(UUID selectedPlayer, String portal);

    void removeSelectedPortal(UUID uuid);

    void addSelectedHand(UUID uuid, EnumHandSelection enumHandSelection, PortalLocation portalLocation);

    void removeSelectedHand(UUID uuid, EnumHandSelection enumHandSelection);

    void removeAllSelectedHand(UUID uuid);
}
