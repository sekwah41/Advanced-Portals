package com.sekwah.advancedportals.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
import com.sekwah.advancedportals.core.commands.subcommands.desti.CreateDestiSubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.*;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.tags.activation.DestiTag;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;

import java.io.File;

public class AdvancedPortalsCore {


    public static final String version = "1.0.0";

    public static final String lastTranslationUpdate = "1.0.0";

    private final InfoLogger infoLogger;
    private final DataStorage dataStorage;

    private final AdvancedPortalsModule module;

    @Inject
    private CommandRegister commandRegister;

    private CommandWithSubCommands portalCommand;
    private CommandWithSubCommands destiCommand;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private TagRegistry tagRegistry;

    public AdvancedPortalsCore(File dataStorageLoc, InfoLogger infoLogger) {
        this.dataStorage = new DataStorage(dataStorageLoc);
        this.infoLogger = infoLogger;
        this.module = new AdvancedPortalsModule(this);
        // Provide any items that need to be provided.
        //this.module.addInstanceBinding(DataCollector.class, this.infoLogger);

        // Don't do much crazy setup here, keep it to onEnable as that will be once the implementation is set up.
    }

    /**
     *  For some platforms we could do this on construction but this just allows for a bit more control
     */
    public void onEnable() {
        // Force values to get injected, either because the initial ones were created too early or to ensure they are not null.
        // Do it here to give implementations a chance to interact with the module.
        Injector injector = module.getInjector();
        injector.injectMembers(this);
        injector.injectMembers(Lang.instance);

        //AdvancedPortalsModule module = new AdvancedPortalsModule(this);
        this.dataStorage.copyDefaultFile("lang/en_GB.lang", false);
        this.loadPortalConfig();
        Lang.loadLanguage(configRepository.getTranslation());

        this.registerCommands();
        this.registerTags();

        this.infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    private void registerTags() {
        this.tagRegistry.registerTag(new DestiTag());
    }

    /**
     *
     */
    public void registerCommands() {
        this.registerPortalCommand(commandRegister);
        this.registerDestinationCommand(commandRegister);
    }

    private void registerPortalCommand(CommandRegister commandRegister) {
        this.portalCommand = new CommandWithSubCommands(this);

        this.portalCommand.registerSubCommand("version", new VersionSubCommand());
        this.portalCommand.registerSubCommand("langupdate", new LangUpdateSubCommand());
        this.portalCommand.registerSubCommand("reload", new ReloadSubCommand());
        this.portalCommand.registerSubCommand("selector", new SelectorSubCommand(), "wand");
        this.portalCommand.registerSubCommand("portalblock", new PortalBlockSubCommand());
        this.portalCommand.registerSubCommand("endportalblock", new EndPortalBlockSubCommand());
        this.portalCommand.registerSubCommand("endgatewayblock", new EndGatewayBlockSubCommand());
        this.portalCommand.registerSubCommand("create", new CreatePortalSubCommand());
        this.portalCommand.registerSubCommand("remove", new RemoveSubCommand());
        this.portalCommand.registerSubCommand("list", new ListSubCommand());

        commandRegister.registerCommand("portal", this.portalCommand);
    }

    private void registerDestinationCommand(CommandRegister commandRegister) {
        this.destiCommand = new CommandWithSubCommands(this);
        this.destiCommand.registerSubCommand("create", new CreateDestiSubCommand());

        commandRegister.registerCommand("destination", this.destiCommand);
    }

    /**
     * Loads the portal config into the memory and saves from the memory to check in case certain things have changed
     * (basically if values are missing or whatever)
     */
    public void loadPortalConfig() {
        this.configRepository.loadConfig(this.dataStorage);
        this.dataStorage.storeJson(this.configRepository, "config.json");
    }

    public void onDisable() {
        this.infoLogger.log(Lang.translate("logger.plugindisable"));
    }

    public InfoLogger getInfoLogger() {
        return this.infoLogger;
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }

    public AdvancedPortalsModule getModule() {
        return this.module;
    }

    public TagRegistry getTagRegistry() {
        return this.tagRegistry;
    }
}
