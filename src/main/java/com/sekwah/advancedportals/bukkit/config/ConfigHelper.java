package com.sekwah.advancedportals.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHelper {

    public static String CONFIG_VERSION = "ConfigVersion";

    public static String COMMAND_LOGS = "CommandLogs";

    public static String FORCE_ENABLE_PROXY_SUPPORT = "ForceEnableProxySupport";

    public static String DISABLE_GATEWAY_BEAM = "DisableGatewayBeam";

    private final FileConfiguration config;

    public ConfigHelper(FileConfiguration config) {
        this.config = config;
    }

    /**
     * Recursively for each time there is a future update
     */
    public void update() {
        String configVersion = config.getString("ConfigVersion");
        // Added in 0.5.4
        if(configVersion == null || configVersion.equals("true") || configVersion.equals("0.5.3")) {
            config.set(ConfigHelper.CONFIG_VERSION, "0.5.4");

            config.set(ConfigHelper.DISABLE_GATEWAY_BEAM, true);
            update();
        } else if(configVersion.equals("0.5.4")) {
            config.set(ConfigHelper.CONFIG_VERSION, "0.5.11");
            config.set(ConfigHelper.COMMAND_LOGS, true);
        } else if(configVersion.equals("0.5.10") || configVersion.equals("0.5.11")) {
            config.set(ConfigHelper.CONFIG_VERSION, "0.5.13");
            config.set(ConfigHelper.FORCE_ENABLE_PROXY_SUPPORT, false);
        }
    }
}
