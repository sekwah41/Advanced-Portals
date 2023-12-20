package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.serializeddata.config.Config;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.repository.ConfigRepository;

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

    @Override
    public boolean getUseOnlySpecialAxe() {
        return this.config.useOnlySpecialAxe;
    }

    @Override
    public String getTranslation() {
        return this.config.translationFile;
    }

    @Override
    public String getSelectorMaterial() {
        return this.config.selectorMaterial;
    }

    @Override
    public int getVisibleRange() {
        return this.config.visibleRange;
    }

    @Override
    public int getMaxTriggerVisualisationSize() {
        return this.config.maxTriggerVisualisationSize;
    }

    @Override
    public String getDefaultTriggerBlock() {
        return this.config.defaultTriggerBlock;
    }

    @Override
    public void loadConfig(DataStorage dataStorage) {
        this.config = dataStorage.loadJson(Config.class, "config.json");
    }

}
