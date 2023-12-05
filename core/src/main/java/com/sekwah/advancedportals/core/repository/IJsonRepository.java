package com.sekwah.advancedportals.core.repository;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.List;

public interface IJsonRepository<T> {

    boolean save(String name, T t);

    boolean containsKey(String name);

    boolean delete(String name);

    boolean update(String name, T t);

    T get(String name);

    List<String> listAll();
}
