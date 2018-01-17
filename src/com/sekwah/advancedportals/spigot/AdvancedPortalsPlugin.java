package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.util.DataStorage;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;

    public void onEnable() {
        this.portalsCore = new AdvancedPortalsCore(new DataStorage(this.getDataFolder()), new SpigotInfoLogger(this));
    }

    public void onDisable() {
        this.portalsCore.onDisable();
    }

}
