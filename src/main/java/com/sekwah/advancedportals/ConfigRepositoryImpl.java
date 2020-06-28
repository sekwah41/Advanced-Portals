package com.sekwah.advancedportals;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.config.Config;
import com.sekwah.advancedportals.core.data.DataStorage;

import java.util.HashMap;

@Singleton
public class ConfigRepositoryImpl implements ConfigRepository {

    private HashMap<String, Config> configs;
    private Config config;

    public ConfigRepositoryImpl() {
        configs = new HashMap<String,Config>();
    }

    public <T> T getValue(String output) {

        try {
            return (T) configs.get(output);
        } catch (ClassCastException ignored) {

        }
        return null;
    }

    private void test() {
        this.<String>getValue("");
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
