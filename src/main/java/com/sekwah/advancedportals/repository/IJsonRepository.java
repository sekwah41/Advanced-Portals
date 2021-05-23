package com.sekwah.advancedportals.repository;

import com.google.gson.Gson;

public interface IJsonRepository<T> {
    Gson gson = new Gson();
    boolean save(String name, T t);

    boolean containsKey(String name);

    boolean delete(String name);

    boolean update(String name, T t);
}
