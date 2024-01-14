package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;

import java.util.UUID;

public interface IPlayerDataRepository extends IJsonRepository<PlayerData>  {
    void addSelectedPortal(UUID selectedPlayer, String portal);

    void removeSelectedPortal(UUID uuid);

    void addSelectedPosition(UUID uuid, boolean isPos1, BlockLocation portalLocation);

    void removeSelectedPosition(UUID uuid, boolean isPos1);

    void removeAllSelectedHand(UUID uuid);

    void activateCooldown(PlayerContainer player);

    void playerLeave(PlayerContainer player);

    boolean inPortalRegion(PlayerLocation loc);
}
