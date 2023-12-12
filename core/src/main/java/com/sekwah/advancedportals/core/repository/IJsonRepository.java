package com.sekwah.advancedportals.core.repository;

import java.util.List;

public interface IJsonRepository<T> {

    boolean save(String name, T t);

    boolean containsKey(String name);

    boolean delete(String name);

    boolean update(String name, T t);

    T get(String name);

    List<String> getAllNames();

    List<T> getAll();
}
