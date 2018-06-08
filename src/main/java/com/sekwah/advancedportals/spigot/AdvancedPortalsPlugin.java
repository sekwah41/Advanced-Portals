package com.sekwah.advancedportals.spigot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.util.DataStorage;
import com.sekwah.advancedportals.coreconnector.ConnectorDataCollector;
import com.sekwah.advancedportals.coreconnector.command.CommandRegister;
import com.sekwah.advancedportals.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;

    /**
     * Readd this when the injector is actually needed.
     */
    //private Injector injector;

    @Override
    public void onEnable() {

        Metrics metrics = new Metrics(this);

        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());
        System.out.println(Bukkit.getVersion());

        // TODO actually get the minecraft version
        this.portalsCore = new AdvancedPortalsCore(this.getDataFolder(),
                new SpigotInfoLogger(this), new CommandRegister(this), new ConnectorDataCollector(), new int[]{1,12,2});
        //injector = Guice.createInjector(new RepositoryModule(this.portalsCore));
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        this.portalsCore.onDisable();
    }

    public AdvancedPortalsCore getPortalsCore() {
        return portalsCore;
    }
}
