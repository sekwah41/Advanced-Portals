package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.destination.Destination;

import java.io.IOException;

public interface IDestinationRepository extends IJsonRepository<Destination> {
    void addDestination(String name, Destination desti) throws IOException;
}
