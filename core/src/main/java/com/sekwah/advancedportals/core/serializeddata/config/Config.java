package com.sekwah.advancedportals.core.serializeddata.config;

/**
 * To store the data for config
 */
public class Config {
    public boolean useOnlySpecialAxe = true;

    public String selectorMaterial = "IRON_AXE";

    public boolean portalProtection = true;

    public int portalProtectionRadius = 5;

    public String defaultTriggerBlock = "NETHER_PORTAL";

    public boolean stopWaterFlow = true;

    public int joinCooldown = 5;

    public final String translationFile = "en_GB";

    public final int showVisibleRange = 50;

    public final boolean disablePhysicsEvents = true;

    public boolean blockSpectatorMode = true;

    public final int maxPortalVisualisationSize = 1000;

    public final int maxSelectionVisualisationSize = 9000;

    public double throwbackStrength = 0.7;

    public boolean playFailSound = true;

    public boolean warpMessageOnActionBar = true;

    public boolean warpMessageInChat = false;

    public CommandPortalConfig commandPortals = new CommandPortalConfig();

    public boolean enableProxySupport = false;

    public WarpEffectConfig warpEffect = new WarpEffectConfig();

    public boolean disableGatewayBeam = true;
}
