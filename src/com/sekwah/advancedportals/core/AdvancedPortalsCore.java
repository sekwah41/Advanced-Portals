package com.sekwah.advancedportals.core;

import com.google.gson.Gson;
import com.sekwah.advancedportals.core.util.Config;
import com.sekwah.advancedportals.core.util.DataStorage;
import com.sekwah.advancedportals.core.util.InfoLogger;

public class AdvancedPortalsCore {

    private static AdvancedPortalsCore instance;
    private static CoreConnector coreConnector;
    private static DataStorage dataStorage;
    private static InfoLogger infoLogger;
    private static Config config;

    public AdvancedPortalsCore(CoreConnector coreConnector, DataStorage dataStorage, InfoLogger infoLogger) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.coreConnector = coreConnector;
        this.onEnable();
    }

    private void onEnable() {
        config = dataStorage.loadJson(Config.class, "config.json");
    }

    private static AdvancedPortalsCore getInstance() {
        return instance;
    }

    public static DataStorage getDataStorage() {
        return dataStorage;
    }

    public static InfoLogger getInfoLogger() {
        return infoLogger;
    }

    public static CoreConnector getCoreConnector() {
        return coreConnector;
    }

    public interface CoreConnector {
        DataStorage getDataStorage();
    }
}
