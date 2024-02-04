package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;

import java.util.UUID;

public interface IPlayerDataRepository extends IJsonRepository<PlayerData>  {
}
