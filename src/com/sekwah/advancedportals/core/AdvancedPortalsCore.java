package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.commands.DestiCommand;
import com.sekwah.advancedportals.core.commands.PortalCommand;
import com.sekwah.advancedportals.core.util.Config;
import com.sekwah.advancedportals.core.util.DataStorage;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.coreconnector.command.CommandRegister;

public class AdvancedPortalsCore {

    private static AdvancedPortalsCore instance;
    private final CommandRegister commandRegister;
    private final DataStorage dataStorage;
    private final InfoLogger infoLogger;
    private Config config;

    public AdvancedPortalsCore(DataStorage dataStorage, InfoLogger infoLogger, CommandRegister commandRegister) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.commandRegister = commandRegister;
        this.onEnable();
    }

    private void onEnable() {
        this.loadPortalData();
        infoLogger.log("Advanced portals have been successfully enabled!");

        this.commandRegister.registerCommand("portal", new PortalCommand());
        this.commandRegister.registerCommand("destination", new DestiCommand());
    }

    /**
     * Can be used for in /portal reload as well.
     */
    private void loadPortalData() {
        this.config = this.dataStorage.loadJson(Config.class, "config.json");
        this.dataStorage.storeJson(this.config, "config.json");
    }

    public void onDisable() {
        infoLogger.log("Advanced portals are being disabled!");
    }

    private static AdvancedPortalsCore getInstance() {
        return instance;
    }

    public static DataStorage getDataStorage() {
        return instance.dataStorage;
    }

    public static InfoLogger getInfoLogger() {
        return instance.infoLogger;
    }
}
