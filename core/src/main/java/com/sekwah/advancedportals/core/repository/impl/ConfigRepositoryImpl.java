package com.sekwah.advancedportals.core.repository.impl;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.serializeddata.config.CommandPortalConfig;
import com.sekwah.advancedportals.core.serializeddata.config.Config;
import java.util.HashMap;

@Singleton
public class ConfigRepositoryImpl implements ConfigRepository {
    private HashMap<String, Config> configs;
    private Config config;
    private DataStorage dataStorage;

    public ConfigRepositoryImpl() {
        configs = new HashMap<String, Config>();
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
    public int getShowVisibleRange() {
        return this.config.showVisibleRange;
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
    public boolean isProtectionActive() {
        return this.config.portalProtection;
    }

    @Override
    public int getProtectionRadius() {
        return this.config.portalProtectionRaduis;
    }

    @Override
    public boolean getStopWaterFlow() {
        return this.config.stopWaterFlow;
    }

    @Override
    public boolean getPortalProtection() {
        return this.config.portalProtection;
    }

    @Override
    public long getPortalCooldown() {
        return this.config.joinCooldown;
    }

    @Override
    public double getThrowbackStrength() {
        return this.config.throwbackStrength;
    }

    @Override
    public String getWarpSound() {
        return this.config.warpEffect.soundEffect;
    }

    @Override
    public String getWarpVisual() {
        return this.config.warpEffect.visualEffect;
    }

    @Override
    public boolean getWarpEffectEnabled() {
        return this.config.warpEffect.enabled;
    }

    @Override
    public boolean getEnableProxySupport() {
        return this.config.enableProxySupport;
    }

    @Override
    public boolean getDisableGatewayBeam() {
        return this.config.disableGatewayBeam;
    }

    @Override
    public void loadConfig(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.config = dataStorage.loadFile(Config.class, "config.yaml");
    }

    @Override
    public boolean playFailSound() {
        return this.config.playFailSound;
    }

    @Override
    public boolean getDisablePhysicsEvents() {
        return this.config.disablePhysicsEvents;
    }

    @Override
    public CommandPortalConfig getCommandPortals() {
        return this.config.commandPortals;
    }

    @Override
    public boolean warpMessageOnActionBar() {
        return this.config.warpMessageOnActionBar;
    }

    @Override
    public boolean warpMessageInChat() {
        return this.config.warpMessageInChat;
    }

    @Override
    public void storeConfig() {
        this.dataStorage.storeFile(this.config, "config.yaml");
    }
}
