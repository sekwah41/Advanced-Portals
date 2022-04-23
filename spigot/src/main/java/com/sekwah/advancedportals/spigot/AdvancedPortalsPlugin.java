package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.spigot.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;

    @Override
    public void onEnable() {

        this.portalsCore = new AdvancedPortalsCore();

        this.portalsCore.onEnable();

        new Metrics(this);

        this.getServer().getConsoleSender().sendMessage("\u00A7aAdvanced portals have been successfully enabled!");
    }


    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("\u00A7cAdvanced portals are being disabled!");
    }

}
