package com.sekwah.advancedportals.repository;

import com.google.common.collect.ImmutableMap;
import com.sekwah.advancedportals.api.destination.Destination;

@Deprecated
public interface DestinationRepositoryOld {
    void create(String name, Destination destination);

    void delete(String name);

    ImmutableMap<String, Destination> getDestinations();

    void loadDestinations();
}
