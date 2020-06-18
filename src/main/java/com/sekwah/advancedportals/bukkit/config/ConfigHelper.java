package com.sekwah.advancedportals.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHelper {

    public static String CONFIG_VERSION = "ConfigVersion";

    private FileConfiguration config;

    public ConfigHelper(FileConfiguration config) {

        this.config = config;
    }

    public void update() {
        String configVersion = config.getString("ConfigVersion");
        // Added in 0.5.3 so update to 0.5.3
        if(configVersion == null) {
            config.set(ConfigHelper.CONFIG_VERSION, "0.5.3");
        }
    }
}
