package com.sekwah.advancedportals.velocity;

import com.sekwah.advancedportals.core.util.InfoLogger;

public class VelocityInfoLogger extends InfoLogger {

    private final AdvancedPortalsPlugin plugin;

    public VelocityInfoLogger(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void warning(String s) {
        plugin.getLogger().warn(s);
    }

    @Override
    public void log(String s) {
        plugin.getLogger().info(s);
    }

    @Override
    public void error(Exception e) {
        plugin.getLogger().error(e.getMessage(), e);
    }
}
