package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import com.sekwah.advancedportals.shadowed.inject.Injector;
import com.sekwah.advancedportals.spigot.commands.subcommands.portal.ImportPortalSubCommand;
import com.sekwah.advancedportals.spigot.connector.command.SpigotCommandRegister;
import com.sekwah.advancedportals.spigot.connector.container.SpigotServerContainer;
import com.sekwah.advancedportals.spigot.importer.LegacyImporter;
import com.sekwah.advancedportals.spigot.metrics.Metrics;
import com.sekwah.advancedportals.spigot.reflection.MinecraftCustomPayload;
import com.sekwah.advancedportals.spigot.warpeffects.SpigotWarpEffects;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {
    private AdvancedPortalsCore portalsCore;

    @Inject
    DestinationServices destinationServices;

    @Inject
    PortalServices portalServices;

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

        Permissions.hasPermissionManager = true;

        String mcVersion = this.getServer().getVersion();
        Pattern pattern = Pattern.compile("\\(MC: ([\\d.]+)\\)");
        Matcher matcher = pattern.matcher(mcVersion);
        var serverContainer = new SpigotServerContainer(this.getServer());
        this.portalsCore = new AdvancedPortalsCore(
            matcher.find() ? matcher.group(1) : "0.0.0", this.getDataFolder(),
            new SpigotInfoLogger(this), serverContainer);
        AdvancedPortalsModule module = this.portalsCore.getModule();

        module.addInstanceBinding(CommandRegister.class,
                                  new SpigotCommandRegister(this));

        Injector injector = module.getInjector();

        MinecraftCustomPayload.attemptFindData();

        injector.injectMembers(this);
        injector.injectMembers(this.portalsCore);
        injector.injectMembers(serverContainer);

        Listeners listeners = injector.getInstance(Listeners.class);
        injector.injectMembers(listeners);
        this.getServer().getPluginManager().registerEvents(listeners, this);

        GameScheduler scheduler = injector.getInstance(GameScheduler.class);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(
            this, scheduler::tick, 1, 1);

        var warpEffects = new SpigotWarpEffects();
        injector.injectMembers(warpEffects);
        warpEffects.registerEffects();

        checkAndCreateConfig();

        // Try to do this after setting up everything that would need to be
        // injected to.
        this.portalsCore.onEnable();

        this.portalsCore.registerPortalCommand("import",
                                               new ImportPortalSubCommand());

        new Metrics(this);
    }

    private void checkAndCreateConfig() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        File destiFile = new File(this.getDataFolder(), "destinations.yml");
        File destiFolder = new File(this.getDataFolder(), "desti");
        if (destiFile.exists() && !destiFolder.exists()) {
            destiFolder.mkdirs();
            getLogger().info(
                "Importing old destinations from destinations.yaml");
            LegacyImporter.importDestinations(this.destinationServices);
        }

        File portalFile = new File(this.getDataFolder(), "portals.yml");
        File portalFolder = new File(this.getDataFolder(), "portals");
        if (portalFile.exists() && !portalFolder.exists()) {
            portalFolder.mkdirs();
            getLogger().info("Importing old portals from portals.yaml");
            LegacyImporter.importPortals(this.portalServices);
        }
    }

    @Override
    public void onDisable() {
        this.portalsCore.onDisable();
    }
}
