package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.managers.DestinationManager;
import com.sekwah.advancedportals.core.api.managers.PortalManager;
import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
import com.sekwah.advancedportals.core.commands.subcommands.portal.ReloadSubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.TransUpdateSubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.VersionSubCommand;
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

    private final CoreListeners coreListeners;

    private Config config;

    private CommandWithSubCommands portalCommand;
    private CommandWithSubCommands destiCommand;

    private PortalManager portalManager;
    private DestinationManager destiManager;

    public static final String version = "1.0.0";
    public static final String lastTranslationUpdate = "1.0.0";

    public AdvancedPortalsCore(DataStorage dataStorage, InfoLogger infoLogger, CommandRegister commandRegister) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.commandRegister = commandRegister;
        this.coreListeners = new CoreListeners(this);
        this.portalManager = new PortalManager(this);
        this.destiManager = new DestinationManager(this);
        this.onEnable();
    }

    public static String getTranslationName() {
        return instance.config.getTranslation();
    }

    private void onEnable() {
        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);

        this.loadPortalConfig();
        Lang.loadLanguage(config.getTranslation());

        this.registerPortalCommand();
        this.registerDestinationCommand();

        this.portalManager.loadPortals();

        this.infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    private void registerPortalCommand() {
        this.portalCommand = new CommandWithSubCommands();

        this.portalCommand.registerSubCommand("version", new VersionSubCommand());
        this.portalCommand.registerSubCommand("transupdate", new TransUpdateSubCommand());
        this.portalCommand.registerSubCommand("reload", new ReloadSubCommand());

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
}
