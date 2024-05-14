package com.sekwah.advancedportals.bungee;

import com.sekwah.advancedportals.core.util.InfoLogger;

public class BungeeInfoLogger extends InfoLogger {

    private final AdvancedPortalsPlugin plugin;

    public BungeeInfoLogger(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void warning(String s) {
        plugin.getLogger().warning(s);
    }

    @Override
    public void log(String s) {
        plugin.getLogger().info(s);
    }

    @Override
    public void error(Exception e) {
        plugin.getLogger().severe(e.getMessage());
    }
}
