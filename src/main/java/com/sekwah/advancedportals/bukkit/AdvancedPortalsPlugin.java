package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.config.ConfigHelper;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.destinations.DestinationCommand;
import com.sekwah.advancedportals.bukkit.effects.WarpEffects;
import com.sekwah.advancedportals.bukkit.listeners.*;
import com.sekwah.advancedportals.bukkit.metrics.Metrics;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedPortalsPlugin extends JavaPlugin {

    //public CraftBukkit compat = null;
    private Settings settings;

    public String channelName = "mc:advancedportals";

    // public HashMap<OfflinePlayer, String> PlayerDestiMap = new HashMap<>();

    public void onEnable() {

        String packageName = getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        String version = packageSplit[packageSplit.length - 1];

        saveDefaultConfig();

        /*Metrics metrics = */
        new Metrics(this);


        //this.compat = new CraftBukkit(this, version);

        ConfigAccessor config = new ConfigAccessor(this, "config.yml");

        ConfigHelper configHelper = new ConfigHelper(config.getConfig());

        configHelper.update();

        config.saveConfig();

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

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, channelName);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, channelName, new PluginMessageReceiver(this));
    }


    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("\u00A7cAdvanced portals are being disabled!");
    }


    public Settings getSettings() {
        return settings;
    }
}
