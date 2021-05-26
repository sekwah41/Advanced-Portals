package com.sekwah.advancedportals.core.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class DataStorage {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private File dataFolder;
}
