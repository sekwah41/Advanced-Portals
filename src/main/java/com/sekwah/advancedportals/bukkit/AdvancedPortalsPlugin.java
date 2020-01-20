package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.compat.CraftBukkit;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.destinations.DestinationCommand;
import com.sekwah.advancedportals.bukkit.effects.WarpEffects;
import com.sekwah.advancedportals.bukkit.listeners.*;
import com.sekwah.advancedportals.bukkit.metrics.Metrics;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class AdvancedPortalsPlugin extends JavaPlugin {

    public CraftBukkit compat = null;
    private Settings settings;

    public HashMap<OfflinePlayer, String> PlayerDestiMap = new HashMap<>();

    public void onEnable() {

        String packageName = getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        String version = packageSplit[packageSplit.length - 1];

        saveDefaultConfig();

        Metrics metrics = new Metrics(this);

        try {

            this.compat = new CraftBukkit(this, version);

            ConfigAccessor config = new ConfigAccessor(this, "config.yml");

            // TODO reenable and finish but probably focus on the recode first
            /*if(config.getConfig().getBoolean("DisableGatewayBeam", true)) {
                new PacketInjector(this, version);
            }*/

            ConfigAccessor portalConfig = new ConfigAccessor(this, "portals.yml");
            portalConfig.saveDefaultConfig();


            ConfigAccessor destinationConfig = new ConfigAccessor(this, "destinations.yml");
            destinationConfig.saveDefaultConfig();

            this.settings = new Settings(this);

            // Loads the portal and destination editors
            new Portal(this);
            new Destination(this);


            this.registerCommands();

            new WarpEffects(this);

            this.addListeners();
            this.setupDataCollector();

            this.setupBungee();

            this.getServer().getConsoleSender().sendMessage("\u00A7aAdvanced portals have been successfully enabled!");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            this.getLogger().warning("This version of craftbukkit is not yet supported, please notify sekwah and tell him about this version v:" + version);
            this.getLogger().warning("Along with the above stacktrace");
            this.setEnabled(false);
        } catch (IllegalArgumentException |
                NoSuchFieldException | SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
            this.getLogger().warning("Something went wrong, please notify sekwah and tell him about this version v:" + version);
            this.getLogger().warning("Along with the above stacktrace");
            this.setEnabled(false);
        }

        for (Player player:
             this.getServer().getOnlinePlayers()) {
            player.removeMetadata("hasWarped", this);
            player.removeMetadata("lavaWarped", this);
        }

        // thanks to the new config accessor code the config.saveDefaultConfig(); will now
        //  only copy the file if it doesnt exist!
    }

    private void registerCommands() {
        new PluginMessages(this);
        new AdvancedPortalsCommand(this);
        new DestinationCommand(this);
    }

    private void addListeners() {
        new Listeners(this);

        new FlowStopper(this);
        new PortalProtect(this);
        new PortalPlacer(this);
    }

    private void setupDataCollector() {
        Selection.loadData(this);
    }

    private void setupBungee() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener(this));
    }


    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("\u00A7cAdvanced portals are being disabled!");
    }


    public Settings getSettings() {
        return settings;
    }
}
