package com.sekwah.advancedportals.core.repository;

import com.google.common.collect.ImmutableMap;
import com.sekwah.advancedportals.core.api.destination.Destination;

public interface DestinationRepository {
    void create(String name, Destination destination);

    void delete(String name);

    ImmutableMap<String, Destination> getDestinations();
}
