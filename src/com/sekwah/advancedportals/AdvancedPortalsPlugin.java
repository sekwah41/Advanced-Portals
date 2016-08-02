package com.sekwah.advancedportals;

import com.sekwah.advancedportals.DataCollector.DataCollector;
import com.sekwah.advancedportals.compat.CraftBukkit;
import com.sekwah.advancedportals.destinations.*;
import com.sekwah.advancedportals.effects.WarpEffects;
import com.sekwah.advancedportals.listeners.*;
import com.sekwah.advancedportals.metrics.Metrics;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AdvancedPortalsPlugin extends JavaPlugin {

    public CraftBukkit compat = null;

    public void onEnable() {

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        String packageName = getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        String version = packageSplit[packageSplit.length - 1];

        try {

            this.compat = new CraftBukkit(version);

            ConfigAccessor portalConfig = new ConfigAccessor(this, "portals.yml");
            portalConfig.saveDefaultConfig();

            ConfigAccessor destinationConfig = new ConfigAccessor(this, "destinations.yml");
            destinationConfig.saveDefaultConfig();

            new Assets(this);

            // Opens a channel that messages bungeeCord
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            // Loads the portal and destination editors
            new Portal(this);
            new Destination(this);

            new DataCollector(this);


            // These register the commands
            new PluginMessages(this);
            new AdvancedPortalsCommand(this);
            new DestinationCommand(this);

            new WarpEffects(this);


            // These register the listeners
            new Listeners(this);

            new FlowStopper(this);
            new PortalProtect(this);
            new PortalPlacer(this);

            Selection.LoadData(this);

            DataCollector.setupMetrics();

            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener(this));

            this.getServer().getConsoleSender().sendMessage("\u00A7aAdvanced portals have been successfully enabled!");


        } catch (ClassNotFoundException e) {
            this.getLogger().warning("This version of craftbukkit is not yet supported, please notify sekwah and tell him about this version v:" + version);
            this.setEnabled(false);
        } catch (IllegalArgumentException |
                NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            this.getLogger().warning("Something went wrong, please notify sekwah and tell him about this version v:" + version);
            this.getLogger().warning("Along with the above stacktrace");
            this.setEnabled(false);
        }

        saveDefaultConfig();

        // thanks to the new config accessor code the config.saveDefaultConfig(); will now
        //  only copy the file if it doesnt exist!
    }


    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("\u00A7cAdvanced portals are being disabled!");
    }


}
