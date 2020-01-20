package com.sekwah.advancedportals.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class AdvancedPortalsPlugin extends Plugin {
    @Override
    public void onEnable() {
        getProxy().registerChannel("AdvancedPortals");
        getLogger().info("\u00A7aAdvanced portals have been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("\\u00A7cAdvanced portals are being disabled!");
    }
}
