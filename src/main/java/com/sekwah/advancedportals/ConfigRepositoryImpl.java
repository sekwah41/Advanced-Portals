package com.sekwah.advancedportals;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.config.Config;
import com.sekwah.advancedportals.core.data.DataStorage;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ConfigRepositoryImpl implements ConfigRepository {

    private Map<String, Config> configs;
    private Config config;

    public ConfigRepositoryImpl() {
        configs = new HashMap<>();
    }

    public boolean getUseOnlySpecialAxe() {
        return this.config.useOnlySpecialAxe;
    }

    public void setUseOnlySpecialAxe(boolean useOnlyServerMadeAxe) {
        this.config.useOnlySpecialAxe = useOnlyServerMadeAxe;
    }

    public String getTranslation() {
        return this.config.translationFile;
    }

    public String getSelectorMaterial() {
        return this.config.selectorMaterial;
    }

    @Override
    public void loadConfig(DataStorage dataStorage) {
        this.config = dataStorage.loadJson(Config.class, "config.json");
    }

}
