package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.config.ConfigHelper;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.destinations.DestinationCommand;
import com.sekwah.advancedportals.bukkit.effects.WarpEffects;
import com.sekwah.advancedportals.bukkit.listeners.*;
import com.sekwah.advancedportals.bukkit.metrics.Metrics;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private Settings settings;

    protected boolean isProxyPluginEnabled = false;

    protected boolean isVelocitySupported = false;

    protected boolean forceRegisterProxyChannels = false;
    protected boolean disableProxyWarning = false;

    private boolean worldEditActive = false;

    protected static final Map<String, String> PLAYER_DESTI_MAP = new HashMap<>();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        /*Metrics metrics = */
        new Metrics(this, 4814);

        ConfigAccessor config = new ConfigAccessor(this, "config.yml");

        ConfigHelper configHelper = new ConfigHelper(config.getConfig());

        configHelper.update();

        config.saveConfig();

        FileConfiguration pluginConfig = config.getConfig();
        forceRegisterProxyChannels = pluginConfig.getBoolean(ConfigHelper.FORCE_ENABLE_PROXY_SUPPORT, false);
        disableProxyWarning = pluginConfig.getBoolean(ConfigHelper.DISABLE_PROXY_WARNING, false);

        ConfigAccessor portalConfig = new ConfigAccessor(this, "portals.yml");
        portalConfig.saveDefaultConfig();


        ConfigAccessor destinationConfig = new ConfigAccessor(this, "destinations.yml");
        destinationConfig.saveDefaultConfig();

        this.settings = new Settings(this);

        // Loads the portal and destination editors
        Portal.init(this);
        Destination.init(this);


        this.registerCommands();

        new WarpEffects(this);

        this.addListeners();
        this.setupDataCollector();

        this.setupBungee();

        this.getServer().getConsoleSender().sendMessage("\u00A7aAdvanced portals have been successfully enabled!");

        for (Player player:
                this.getServer().getOnlinePlayers()) {
            player.removeMetadata(Listeners.HAS_WARPED, this);
            player.removeMetadata(Listeners.LAVA_WARPED, this);
        }

        if (settings.enabledWorldEditIntegration() && Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
            worldEditActive = true;
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
        if(forceRegisterProxyChannels || this.checkIfBungee()) {
            this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener(this));

            this.getServer().getMessenger().registerOutgoingPluginChannel(this, BungeeMessages.CHANNEL_NAME);
            this.getServer().getMessenger().registerIncomingPluginChannel(this, BungeeMessages.CHANNEL_NAME, new PluginMessageReceiver(this));
            isProxyPluginEnabled = true;
        }
        else {
            isProxyPluginEnabled = false;
        }
    }

    public Map<String, String> getPlayerDestiMap() {
        return PLAYER_DESTI_MAP;
    }

    public boolean isProxyPluginEnabled() {
        return isProxyPluginEnabled;
    }

    public boolean isVelocitySupported() {
        return isVelocitySupported;
    }

    private boolean checkIfBungee()
    {
        // we check if the server is Spigot/Paper (because of the spigot.yml file)
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            this.getServer().getConsoleSender().sendMessage( "\u00A7ePossibly unsupported version for bungee messages detected, channels won't be enabled." );
            getLogger().info("If you believe this shouldn't be the case please contact us on discord https://discord.sekwah.com/");
            return false;
        }

        try {
            ConfigurationSection configSelection = getServer().spigot().getConfig().getConfigurationSection("settings");
            if (configSelection != null && configSelection.getBoolean("bungeecord") ) {
                getLogger().info( "Bungee detected. Enabling proxy features." );
                return true;
            }
        } catch(NoSuchMethodError | NullPointerException e) {
            if(!disableProxyWarning) getLogger().info("BungeeCord config not detected, ignoring settings");
        }

        // Will be valid if paperspigot is being used. Otherwise catch.
        try {
            YamlConfiguration config = getServer().spigot().getPaperConfig();
            if(Objects.equals(config.get("velocity-support.enabled"),true)
                    || Objects.equals(config.get("proxies.velocity.enabled"),true)) {
                getLogger().info( "Modern forwarding detected. Enabling proxy features." );
                isVelocitySupported = true;
                return true;
            }
        } catch(NoSuchMethodError | NullPointerException e) {
            if(!disableProxyWarning) getLogger().info("Paper config not detected, ignoring paper settings");
        }

        if(!disableProxyWarning) getLogger().warning( "Proxy features disabled for Advanced Portals as bungee isn't enabled on the server (spigot.yml)." +
                "If you are using Paper proxies.velocity.enabled (config/paper-global.yml) or settings.velocity-support.enabled (paper.yml) may not be enabled " );
        return false;
    }


    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("\u00A7cAdvanced portals are being disabled!");
    }


    public Settings getSettings() {
        return settings;
    }

    public boolean isWorldEditActive() {
        return worldEditActive;
    }
}
