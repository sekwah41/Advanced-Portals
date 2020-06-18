package com.sekwah.advancedportals.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHelper {

    public static String CONFIG_VERSION = "ConfigVersion";

    private FileConfiguration config;

    public ConfigHelper(FileConfiguration config) {

        this.config = config;
    }

    /**
     * Recursively for each time there is a future update
     */
    public void update() {
        String configVersion = config.getString("ConfigVersion");
        // Added in 0.5.4
        if(configVersion == null || configVersion.equals("0.5.3")) {
            config.set(ConfigHelper.CONFIG_VERSION, "0.5.4");
        }
    }
}
