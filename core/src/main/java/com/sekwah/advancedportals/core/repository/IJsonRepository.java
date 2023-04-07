package com.sekwah.advancedportals.core.repository;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public interface IJsonRepository<T> {
    Gson gson = new Gson();
    boolean save(String name, T t);

    boolean containsKey(String name);

    boolean delete(String name);

    boolean update(String name, T t);

    T get(String name);

    ImmutableMap<String, T> getAll();
}
