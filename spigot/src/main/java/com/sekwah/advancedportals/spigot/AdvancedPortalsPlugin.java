package com.sekwah.advancedportals.spigot;

import com.google.inject.Injector;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.spigot.connector.command.SpigotCommandRegister;
import com.sekwah.advancedportals.spigot.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;

    @Override
    public void onEnable() {

        this.portalsCore = new AdvancedPortalsCore(this.getDataFolder(), new SpigotInfoLogger(this));
        AdvancedPortalsModule module = this.portalsCore.getModule();

        module.addInstanceBinding(CommandRegister.class, new SpigotCommandRegister(this));

        Injector injector = module.getInjector();

        injector.injectMembers(this.portalsCore);

        Listeners listeners = injector.getInstance(Listeners.class);
        injector.injectMembers(listeners);
        this.getServer().getPluginManager().registerEvents(listeners, this);

        // Try to do this after setting up everything that would need to be injected to.
        this.portalsCore.onEnable();

        new Metrics(this);
    }


    @Override
    public void onDisable() {
        this.portalsCore.onDisable();
    }

}
