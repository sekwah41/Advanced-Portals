package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.spigot.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;

    @Override
    public void onEnable() {

        this.portalsCore = new AdvancedPortalsCore(this.getDataFolder(), new SpigotInfoLogger(this));

        this.portalsCore.onEnable();

        new Metrics(this);
    }


    @Override
    public void onDisable() {
        this.portalsCore.onDisable();
    }

}
