package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.config.ConfigHelper;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.destinations.DestinationCommand;
import com.sekwah.advancedportals.bukkit.effects.WarpEffects;
import com.sekwah.advancedportals.bukkit.listeners.*;
import com.sekwah.advancedportals.bukkit.metrics.Metrics;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class AdvancedPortalsPlugin extends JavaPlugin {

    //public CraftBukkit compat = null;
    private Settings settings;

    public String channelName = "mc:advancedportals";

    public boolean registeredBungeeChannels = false;

    public HashMap<String, String> PlayerDestiMap = new HashMap<>();

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
        // Enables very basic bungee support if not setup right
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        if(this.checkIfBungee()) {
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener(this));

            this.getServer().getMessenger().registerOutgoingPluginChannel(this, channelName);
            this.getServer().getMessenger().registerIncomingPluginChannel(this, channelName, new PluginMessageReceiver(this));
            registeredBungeeChannels = true;
        }
        else {
            registeredBungeeChannels = false;
        }
    }

    private boolean checkIfBungee()
    {
        // we check if the server is Spigot/Paper (because of the spigot.yml file)
        if ( !getServer().getVersion().contains( "Spigot" ) && !getServer().getVersion().contains( "Paper" ) )
        {
            this.getServer().getConsoleSender().sendMessage( "\u00A7ePossibly unsupported version for bungee messages detected, channels won't be enabled." );
            getLogger().info("If you believe this shouldn't be the case please contact us on discord https://discord.gg/fAJ3xJg");
            return false;
        }
        if ( !getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean( "bungeecord" ) )
        {
            this.getServer().getConsoleSender().sendMessage( "\n\n\u00A7eThis server does not have BungeeCord enabled.\n" +
                    "If the server is already hooked to BungeeCord, please enable it into your spigot.yml as well.\n" +
                    "Yes this can all work without but there is a massive security vulnerability if not enabled.\n" +
                    "You cannot bypass this if you want bungee features enabled.\n" +
                    "If you don't want bungee features \u00A7rignore this message\u00A7e, it only shows on start.\n" );

            getLogger().warning( "Advanced bungee features disabled for Advanced Portals as bungee isn't enabled on the server (spigot.yml)" );
            return false;
        }
        return true;
    }


    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("\u00A7cAdvanced portals are being disabled!");
    }


    public Settings getSettings() {
        return settings;
    }
}
