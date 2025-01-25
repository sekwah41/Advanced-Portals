package com.sekwah.advancedportals.legacyspigot;

import com.sekwah.advancedportals.core.util.InfoLogger;

import java.util.logging.Level;

public class LegacySpigotInfoLogger extends InfoLogger {
    private final AdvancedPortalsPlugin plugin;

    public LegacySpigotInfoLogger(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void warning(String s) {
        plugin.getLogger().warning(s);
    }

    @Override
    public void info(String s) {
        plugin.getLogger().info(s);
    }

    @Override
    public void error(Exception e) {
        plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
    }
}
