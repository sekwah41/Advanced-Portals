package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.config.Config;
import com.sekwah.advancedportals.core.data.DataStorage;

import javax.inject.Singleton;

@Singleton
public class ConfigRepositoryImpl implements ConfigRepository {

    private Config config;

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
