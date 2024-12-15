package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.serializeddata.config.CommandPortalConfig;
import com.sekwah.advancedportals.core.serializeddata.config.Config;

public interface ConfigRepository {
    boolean getUseOnlySpecialAxe();

    String getTranslation();

    String getSelectorMaterial();

    void loadConfig(DataStorage dataStorage);

    int getShowVisibleRange();

    int maxPortalVisualisationSize();
    int maxSelectionVisualisationSize();

    String getDefaultTriggerBlock();

    boolean isProtectionActive();

    int getProtectionRadius();

    boolean blockSpectatorMode();

    boolean getStopWaterFlow();

    boolean getPortalProtection();

    long getPortalCooldown();

    double getThrowbackStrength();

    boolean playFailSound();

    void storeConfig();

    boolean getDisablePhysicsEvents();

    CommandPortalConfig getCommandPortals();

    String getWarpSound();

    boolean warpMessageOnActionBar();

    boolean warpMessageInChat();

    String getWarpVisual();

    boolean getWarpEffectEnabled();

    boolean getEnableProxySupport();

    boolean getDisableGatewayBeam();

    void importConfig(Config config);
}
