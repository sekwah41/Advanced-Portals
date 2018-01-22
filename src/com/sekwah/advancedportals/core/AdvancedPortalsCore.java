package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
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

    private Config config;

    private CommandWithSubCommands portalCommand;
    private CommandWithSubCommands destiCommand;

    public AdvancedPortalsCore(DataStorage dataStorage, InfoLogger infoLogger, CommandRegister commandRegister) {
        this.dataStorage = dataStorage;
        this.infoLogger = infoLogger;
        this.instance = this;
        this.commandRegister = commandRegister;
        this.onEnable();
    }

    private void onEnable() {
        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);
        Lang.loadLanguage("en_GB");
        this.loadPortalConfig();

        this.portalCommand = new CommandWithSubCommands();
        this.destiCommand = new CommandWithSubCommands();
        this.commandRegister.registerCommand("portal", this.portalCommand);
        this.commandRegister.registerCommand("destination", this.destiCommand);

        infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    /**
     * Loads the portal config into the memory and saves from the memory to check in case certain things have changed
     * (basically if values are missing or whatever)
     */
    private void loadPortalConfig() {
        this.config = this.dataStorage.loadJson(Config.class, "config.json", true);
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
}
