package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.util.Config;
import com.sekwah.advancedportals.core.util.DataStorage;
import com.sekwah.advancedportals.core.util.InfoLogger;

public class AdvancedPortalsCore {

    private static AdvancedPortalsCore instance;
    private static DataStorage dataStorage;
    private static InfoLogger infoLogger;
    private static Config config;

    public AdvancedPortalsCore(DataStorage dataStorage, InfoLogger infoLogger) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.onEnable();
    }

    private void onEnable() {
        config = dataStorage.loadJson(Config.class, "config.json");
        infoLogger.log("\u00A7aAdvanced portals have been successfully enabled!");
    }

    public void onDisable() {
        infoLogger.log("\u00A7cAdvanced portals are being disabled!");
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
}
