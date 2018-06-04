package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.data.DataStorage;
import com.sekwah.advancedportals.coreconnector.ConnectorDataCollector;
import com.sekwah.advancedportals.coreconnector.command.CommandRegister;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;

    public void onEnable() {
        // TODO actually get the minecraft version
        this.portalsCore = new AdvancedPortalsCore(new DataStorage(this.getDataFolder()),
                new SpigotInfoLogger(this), new CommandRegister(this), new ConnectorDataCollector(), new int[]{1,12,2});
        this.getServer().getPluginManager().registerEvents(new Listeners(this), this);
    }

    public void onDisable() {
        this.portalsCore.onDisable();
    }

    public AdvancedPortalsCore getPortalsCore() {
        return portalsCore;
    }
}
