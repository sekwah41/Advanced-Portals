package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.util.InfoLogger;

public class SpigotInfoLogger extends InfoLogger {

    private final AdvancedPortalsPlugin plugin;

    public SpigotInfoLogger(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void logWarning(String s) {
        plugin.getLogger().warning(s);
    }

    @Override
    public void log(String s) {
        plugin.getLogger().info(s);
    }
}
