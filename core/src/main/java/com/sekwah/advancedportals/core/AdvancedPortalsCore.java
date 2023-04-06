package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.data.DataStorage;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;

import java.io.File;

public class AdvancedPortalsCore {


    private final InfoLogger infoLogger;
    private final DataStorage dataStorage;

    private final AdvancedPortalsModule module;

    private final ConfigRepository configRepository;

    public AdvancedPortalsCore(File dataStorageLoc, InfoLogger infoLogger) {
        this.dataStorage = new DataStorage(dataStorageLoc);
        this.infoLogger = infoLogger;
        this.module = new AdvancedPortalsModule(this);
        this.configRepository = module.getInjector().getInstance(ConfigRepository.class);
        module.getInjector().injectMembers(Lang.instance);
    }

    /**
     *  For some platforms we could do this on construction but this just allows for a bit more control
     */
    public void onEnable() {
        //AdvancedPortalsModule module = new AdvancedPortalsModule(this);
        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);
        this.loadPortalConfig();
        Lang.loadLanguage(configRepository.getTranslation());

        this.infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    /**
     * Loads the portal config into the memory and saves from the memory to check in case certain things have changed
     * (basically if values are missing or whatever)
     */
    public void loadPortalConfig() {
        this.configRepository.loadConfig(this.dataStorage);
        this.dataStorage.storeJson(this.configRepository, "config.json");
    }

    public void onDisable() {
        this.infoLogger.log(Lang.translate("logger.plugindisable"));
    }

    public InfoLogger getInfoLogger() {
        return this.infoLogger;
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }
}
