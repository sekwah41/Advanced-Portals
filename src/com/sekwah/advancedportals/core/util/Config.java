package com.sekwah.advancedportals.core.util;

/**
 * To store the data for config
 */
public class Config {

    private boolean useOnlySpecialAxe;

    private String selectorMaterial = "IRON_AXE";

    private boolean portalProtection = true;

    private int portalProtectionRaduis = 5;

    private String defaultTriggerBlock = "PORTAL";

    private boolean stopWaterFlow = true;

    private String selectionBlock = "STAINED_GLASS";

    private String translationFile = "en_GB";

    private int blockSubID_BELOW_1_13_ONLY = 14;

    public boolean getUseOnlySpecialAxe() {
        return useOnlySpecialAxe;
    }

    public void setUseOnlySpecialAxe(boolean useOnlyServerMadeAxe) {
        useOnlySpecialAxe = useOnlyServerMadeAxe;
    }

    public String getTranslation() {
        return translationFile;
    }
}
