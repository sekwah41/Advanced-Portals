package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.managers.DestinationManager;
import com.sekwah.advancedportals.core.api.managers.PortalManager;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.registry.TagRegistry;
import com.sekwah.advancedportals.core.api.registry.WarpEffectRegistry;
import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
import com.sekwah.advancedportals.core.commands.subcommands.portal.*;
import com.sekwah.advancedportals.core.util.Config;
import com.sekwah.advancedportals.core.util.DataStorage;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.command.CommandRegister;

public class AdvancedPortalsCore {

    private static AdvancedPortalsCore instance;
    private final CommandRegister commandRegister;
    private final DataStorage dataStorage;
    private final InfoLogger infoLogger;
    private final int mcMinorVer;

    private WarpEffectRegistry warpEffectRegistry;
    private TagRegistry<AdvancedPortal> portalTagRegistry;
    private TagRegistry<Destination> destiTagRegistry;

    private CoreListeners coreListeners;

    private Config config;

    private CommandWithSubCommands portalCommand;
    private CommandWithSubCommands destiCommand;

    private PortalManager portalManager;
    private DestinationManager destiManager;

    public static final String version = "1.0.0";
    public static final String lastTranslationUpdate = "1.0.0";

    /**
     * @param dataStorage - The implementation of data storage for the specific platform
     * @param infoLogger - The implementation of the logger for the specific platform
     * @param commandRegister - Handles the command registry, different on each platform
     * @param mcVer Minecraft version e.g. 1.12.2
     */
    public AdvancedPortalsCore(DataStorage dataStorage, InfoLogger infoLogger, CommandRegister commandRegister, int[] mcVer) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.commandRegister = commandRegister;
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
        return instance.config.getTranslation();
    }

    private void onEnable() {
        this.coreListeners = new CoreListeners(this);
        this.portalManager = new PortalManager(this);
        this.destiManager = new DestinationManager(this);
        this.warpEffectRegistry = new WarpEffectRegistry();
        this.portalTagRegistry = new TagRegistry<>();
        this.destiTagRegistry = new TagRegistry<>();

        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);

        this.loadPortalConfig();
        Lang.loadLanguage(config.getTranslation());

        this.registerPortalCommand();
        this.registerDestinationCommand();

        this.portalManager.loadPortals();

        this.destiManager.loadDestinations();

        this.infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    private void registerPortalCommand() {
        this.portalCommand = new CommandWithSubCommands();

        this.portalCommand.registerSubCommand("version", new VersionSubCommand());
        this.portalCommand.registerSubCommand("transupdate", new TransUpdateSubCommand(this));
        this.portalCommand.registerSubCommand("reload", new ReloadSubCommand(this));
        this.portalCommand.registerSubCommand("selector", new SelectorSubCommand(this), "wand");
        this.portalCommand.registerSubCommand("create", new CreateSubCommand());
        this.portalCommand.registerSubCommand("remove", new RemoveSubCommand());

        this.commandRegister.registerCommand("portal", this.portalCommand);
    }

    private void registerDestinationCommand() {
        this.destiCommand = new CommandWithSubCommands();
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
        this.config = this.dataStorage.loadJson(Config.class, "config.json");
        this.dataStorage.storeJson(this.config, "config.json");
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

    public Config getConfig() {
        return this.config;
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }

    public static InfoLogger getInfoLogger() {
        return instance.infoLogger;
    }

    public static CoreListeners getCoreListeners() {
        return instance.coreListeners;
    }

    public static PortalManager getPortalManager() {
        return instance.portalManager;
    }

    public static DestinationManager getDestiManager() {
        return instance.destiManager;
    }

    public static TagRegistry<AdvancedPortal> getPortalTagRegistry() {
        return instance.portalTagRegistry;
    }

    public static TagRegistry<Destination> getDestinationTagRegistry() {
        return instance.destiTagRegistry;
    }
}
