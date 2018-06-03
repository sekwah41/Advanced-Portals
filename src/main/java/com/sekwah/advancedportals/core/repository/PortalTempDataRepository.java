package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.core.enums.EnumHandSelection;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.UUID;

public interface TempPlayerDataRepository {
    void addSelectedPortal(UUID selectedPlayer, String portal);

    void removeSelectedPortal(UUID uuid);

    void addSelectedHand(UUID uuid, EnumHandSelection enumHandSelection, PortalLocation portalLocation);

    void removeSelectedHand(UUID uuid, EnumHandSelection enumHandSelection);

    void removeAllSelectedHand(UUID uuid);

    void activateCooldown(PlayerContainer player);

    void playerLeave(PlayerContainer player);

    boolean inPortalRegion(PlayerLocation loc);
}
