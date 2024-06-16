package com.sekwah.advancedportals.spigot;

import com.google.inject.Injector;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.spigot.commands.subcommands.portal.UpdatePortalSubCommand;
import com.sekwah.advancedportals.spigot.connector.command.SpigotCommandRegister;
import com.sekwah.advancedportals.spigot.connector.container.SpigotServerContainer;
import com.sekwah.advancedportals.spigot.metrics.Metrics;
import com.sekwah.advancedportals.spigot.warpeffects.SpigotWarpEffects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {
    private AdvancedPortalsCore portalsCore;

    private static AdvancedPortalsPlugin instance;

    public static AdvancedPortalsPlugin getInstance() {
        return instance;
    }

    public AdvancedPortalsPlugin() {
        instance = this;
    }

    @Override
    public void onEnable() {
        new Metrics(this);

        String mcVersion = this.getServer().getVersion();
        Pattern pattern = Pattern.compile("\\(MC: ([\\d.]+)\\)");
        Matcher matcher = pattern.matcher(mcVersion);
        this.portalsCore = new AdvancedPortalsCore(
            matcher.find() ? matcher.group(1) : "0.0.0", this.getDataFolder(),
            new SpigotInfoLogger(this),
            new SpigotServerContainer(this.getServer()));
        AdvancedPortalsModule module = this.portalsCore.getModule();

        module.addInstanceBinding(CommandRegister.class,
                                  new SpigotCommandRegister(this));

        Injector injector = module.getInjector();

        injector.injectMembers(this.portalsCore);

        Listeners listeners = injector.getInstance(Listeners.class);
        injector.injectMembers(listeners);
        this.getServer().getPluginManager().registerEvents(listeners, this);

        GameScheduler scheduler = injector.getInstance(GameScheduler.class);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(
            this, scheduler::tick, 1, 1);

        var warpEffects = new SpigotWarpEffects();
        injector.injectMembers(warpEffects);
        warpEffects.registerEffects();

        // Try to do this after setting up everything that would need to be
        // injected to.
        this.portalsCore.onEnable();

        this.portalsCore.registerPortalCommand("update",
                                               new UpdatePortalSubCommand());

        new Metrics(this);
    }

    @Override
    public void onDisable() {
        this.portalsCore.onDisable();
    }
}
