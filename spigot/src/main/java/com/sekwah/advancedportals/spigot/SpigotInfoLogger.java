package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.util.InfoLogger;
import java.util.logging.Level;

public class SpigotInfoLogger extends InfoLogger {
    private final AdvancedPortalsPlugin plugin;

    public SpigotInfoLogger(AdvancedPortalsPlugin plugin) {
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
