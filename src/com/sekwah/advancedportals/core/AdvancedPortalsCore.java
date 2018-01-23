package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
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

    public static final String version = "1.0.0";
    public static final String lastTranslationUpdate = "1.0.0";

    public AdvancedPortalsCore(DataStorage dataStorage, InfoLogger infoLogger, CommandRegister commandRegister) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.commandRegister = commandRegister;
        this.coreListeners = new CoreListeners();
        this.onEnable();
    }

    private void onEnable() {
        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);

        this.loadPortalConfig();
        Lang.loadLanguage(config.getTranslation());

        this.registerPortalCommand();
        this.registerDestinationCommand();

        infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    private void registerPortalCommand() {
        this.portalCommand = new CommandWithSubCommands();

        this.portalCommand.registerSubCommand("version", new VersionSubCommand());

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
    private void loadPortalConfig() {
        this.config = this.dataStorage.loadJson(Config.class, "config.json");
        this.dataStorage.storeJson(this.config, "config.json");
    }

    public void onDisable() {
        infoLogger.log(Lang.translate("logger.plugindisable"));
    }

    private static AdvancedPortalsCore getInstance() {
        return instance;
    }

    public static DataStorage getDataStorage() {
        return instance.dataStorage;
    }

    public static InfoLogger getInfoLogger() {
        return instance.infoLogger;
    }

    public CoreListeners getCoreListeners() {
        return this.coreListeners;
    }
}
