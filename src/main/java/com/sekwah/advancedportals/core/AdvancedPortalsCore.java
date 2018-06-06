package com.sekwah.advancedportals.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.services.DestinationServices;
import com.sekwah.advancedportals.core.api.services.PortalServices;
import com.sekwah.advancedportals.core.api.services.PortalTempDataServices;
import com.sekwah.advancedportals.core.config.RepositoryModule;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.registry.TagRegistry;
import com.sekwah.advancedportals.core.api.registry.WarpEffectRegistry;
import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
import com.sekwah.advancedportals.core.commands.subcommands.desti.CreateDestiSubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.*;
import com.sekwah.advancedportals.core.data.DataStorage;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.ConnectorDataCollector;
import com.sekwah.advancedportals.coreconnector.command.CommandRegister;

import java.io.File;

public class AdvancedPortalsCore {

    private static AdvancedPortalsCore instance;

    private final CommandRegister commandRegister;
    private final InfoLogger infoLogger;
    private final int mcMinorVer;
    private final ConnectorDataCollector dataCollector;

    private Injector injector = Guice.createInjector(new RepositoryModule(this));

    private WarpEffectRegistry warpEffectRegistry = injector.getInstance(WarpEffectRegistry.class);
    private TagRegistry<AdvancedPortal> portalTagRegistry;
    private TagRegistry<Destination> destiTagRegistry;

    private CoreListeners coreListeners = injector.getInstance(CoreListeners.class);

    private final DataStorage dataStorage = injector.getInstance(DataStorage.class);

    private CommandWithSubCommands portalCommand;
    private CommandWithSubCommands destiCommand;

    private PortalServices portalServices = injector.getInstance(PortalServices.class);
    private DestinationServices destiServices = injector.getInstance(DestinationServices.class);
    private PortalTempDataServices portalTempDataServices = injector.getInstance(PortalTempDataServices.class);

    private ConfigRepository configRepository = injector.getInstance(ConfigRepository.class);

    public static final String version = "1.0.0";
    public static final String lastTranslationUpdate = "1.0.0";

    /**
     * @param dataStorageLoc - Where the files will be located
     * @param infoLogger - The implementation of the logger for the specific platform
     * @param commandRegister - Handles the command registry, different on each platform
     * @param mcVer Minecraft version e.g. 1.12.2
     */
    public AdvancedPortalsCore(File dataStorageLoc, InfoLogger infoLogger, CommandRegister commandRegister,
                               ConnectorDataCollector dataCollector, int[] mcVer) {
        this.dataStorage.setStorageLocation(dataStorageLoc);
        this.infoLogger = infoLogger;
        instance = this;
        this.commandRegister = commandRegister;
        this.dataCollector = dataCollector;
        this.mcMinorVer = this.checkMcVer(mcVer);

        this.onEnable();
    }

    private int checkMcVer(int[] mcVer) {
        int maxSupportedVer = 12;
        int minSupportedVer = 8;
        if(mcVer.length == 2 || mcVer.length == 3) {
            if(mcVer[0] == 1) {
                if(mcVer[1] < minSupportedVer) {
                    this.infoLogger.logWarning("Older version of mc detected than officially supported. This is very likely not to work.");
                    return minSupportedVer;
                }
                else if (mcVer[1] > maxSupportedVer) {
                    this.infoLogger.logWarning("Newer version of mc detected than currently supported by this version. The plugin may not work.");
                    return maxSupportedVer;
                }
                else {
                    return mcVer[1];
                }
            }
            else {
                this.infoLogger.logWarning("It seems you are using a very strange version of minecraft or something is " +
                        "seriously wrong with the plugin for getting the version of minecraft.");
                return maxSupportedVer;
            }
        }
        else {
            String version = String.valueOf(mcVer[0]);
            for (int i = 0; i < mcVer.length; i++) {
                version += "." + mcVer[i];
            }
            this.infoLogger.logWarning(version + " is definitely not a valid or currently supported mc version. " +
                    "Advanced Portals will try to use the newest available logic and see if it works though results " +
                    "may be unreliable. ");
            return maxSupportedVer;
        }
    }


    public static String getTranslationName() {
        return instance.configRepository.getTranslation();
    }

    private void onEnable() {
        this.portalTagRegistry = new TagRegistry<>(this);
        this.destiTagRegistry = new TagRegistry<>(this);

        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);

        this.loadPortalConfig();

        Lang.loadLanguage(configRepository.getTranslation());

        this.registerPortalCommand();
        this.registerDestinationCommand();

        this.portalServices.loadPortals(this);

        this.destiServices.loadDestinations(this);

        this.infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    private void registerPortalCommand() {
        this.portalCommand = new CommandWithSubCommands();

        this.portalCommand.registerSubCommand("version", new VersionSubCommand());
        this.portalCommand.registerSubCommand("transupdate", new TransUpdateSubCommand(this));
        this.portalCommand.registerSubCommand("reload", new ReloadSubCommand(this));
        this.portalCommand.registerSubCommand("selector", new SelectorSubCommand(this), "wand");
        this.portalCommand.registerSubCommand("portalblock", new PortalBlockSubCommand(this));
        this.portalCommand.registerSubCommand("endportalblock", new EndPortalBlockSubCommand(this));
        this.portalCommand.registerSubCommand("endgatewayblock", new EndGatewayBlockSubCommand(this));
        this.portalCommand.registerSubCommand("create", new CreatePortalSubCommand());
        this.portalCommand.registerSubCommand("remove", new RemoveSubCommand());

        this.commandRegister.registerCommand("portal", this.portalCommand);
    }

    private void registerDestinationCommand() {
        this.destiCommand = new CommandWithSubCommands();

        this.destiCommand.registerSubCommand("create", new CreateDestiSubCommand());

        this.commandRegister.registerCommand("destination", this.destiCommand);
    }

    public static boolean registerDestiSubCommand(String arg, SubCommand subCommand) {
        return instance.destiCommand.registerSubCommand(arg, subCommand);
    }

    public static boolean registerPortalSubCommand(String arg, SubCommand subCommand) {
        return instance.portalCommand.registerSubCommand(arg, subCommand);
    }

    /**
     * Loads the portal config into the memory and saves from the memory to check in case certain things have changed
     * (basically if values are missing or whatever)
     */
    public void loadPortalConfig() {
        this.configRepository.loadConfig(this.dataStorage);
        this.dataStorage.storeJson(this.configRepository, "config.json");
    }

    /**
     * This cannot be called to disable the plugin, it just performs any saves or anything needed after if they are required
     */
    public void onDisable() {
        this.infoLogger.log(Lang.translate("logger.plugindisable"));
    }

    public static AdvancedPortalsCore getInstance() {
        return instance;
    }

    public ConfigRepository getConfigRepo() {
        return this.configRepository;
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }

    public InfoLogger getInfoLogger() {
        return this.infoLogger;
    }

    public ConnectorDataCollector getDataCollector() {
        return this.dataCollector;
    }

    public CoreListeners getCoreListeners() {
        return this.coreListeners;
    }

    public static PortalServices getPortalServices() {
        return instance.portalServices;
    }

    public static DestinationServices getDestinationServices() {
        return instance.destiServices;
    }

    public PortalTempDataServices getPortalTempDataServices() {
        return instance.portalTempDataServices;
    }

    public static TagRegistry<AdvancedPortal> getPortalTagRegistry() {
        return instance.portalTagRegistry;
    }

    public static TagRegistry<Destination> getDestinationTagRegistry() {
        return instance.destiTagRegistry;
    }
}
