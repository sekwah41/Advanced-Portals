package com.sekwah.advancedportals.repository;

import com.google.gson.Gson;

public interface IJsonRepository<T> {
    Gson gson = new Gson();
    public  boolean save(String name, T t);

    public boolean containsKey(String name);

    public  boolean delete(String name);

    public boolean update(String name, T t);
}
