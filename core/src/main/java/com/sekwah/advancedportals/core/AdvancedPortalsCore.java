package com.sekwah.advancedportals.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.commands.CommandWithSubCommands;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.desti.*;
import com.sekwah.advancedportals.core.commands.subcommands.portal.*;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.tags.activation.*;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;

import java.io.File;
import java.util.Arrays;

public class AdvancedPortalsCore {


    public static final String version = "1.0.0";

    private final InfoLogger infoLogger;
    private final DataStorage dataStorage;

    private final AdvancedPortalsModule module;

    /**
     * Use this to enable or alter certain features for different versions.
     * If there is an issue parsing it for any reason it will be set to 0.0.0
     */
    private final int[] mcVersion;

    private final ServerContainer serverContainer;

    private static AdvancedPortalsCore instance;

    @Inject
    private CommandRegister commandRegister;

    private CommandWithSubCommands portalCommand;
    private CommandWithSubCommands destiCommand;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private TagRegistry tagRegistry;

    @Inject
    private PortalServices portalServices;

    @Inject
    private DestinationServices destinationServices;

    @Inject
    private PlayerDataServices playerDataRepository;

    @Inject
    private GameScheduler gameScheduler;

    public AdvancedPortalsCore(String mcVersion, File dataStorageLoc, InfoLogger infoLogger, ServerContainer serverContainer) {
        instance = this;
        this.serverContainer = serverContainer;
        this.dataStorage = new DataStorage(dataStorageLoc);
        this.infoLogger = infoLogger;

        int[] mcVersionTemp;
        infoLogger.log("Loading Advanced Portals Core v" + version + " for MC: " + mcVersion);
        try {
            mcVersionTemp = Arrays.stream(mcVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            infoLogger.log("Failed to parse MC version: " + mcVersion);
            e.printStackTrace();
            mcVersionTemp = new int[]{0, 0, 0};
        }
        if(mcVersionTemp.length == 2) {
            mcVersionTemp = new int[]{mcVersionTemp[0], mcVersionTemp[1], 0};
        }
        this.mcVersion = mcVersionTemp;

        this.module = new AdvancedPortalsModule(this);
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

        this.portalServices.loadPortals();
        this.destinationServices.loadDestinations();
        this.infoLogger.log(Lang.translate("logger.pluginenable"));
    }

    private void registerTags() {
        this.tagRegistry.registerTag(new NameTag());
        this.tagRegistry.registerTag(new DestiTag());
        this.tagRegistry.registerTag(new CooldownTag());
        this.tagRegistry.registerTag(new TriggerBlockTag());
        this.tagRegistry.registerTag(new PermissionTag());
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
        this.portalCommand.registerSubCommand("reload", new ReloadPortalSubCommand());
        this.portalCommand.registerSubCommand("selector", new SelectorSubCommand(), "wand");
        this.portalCommand.registerSubCommand("portalblock", new PortalBlockSubCommand());
        this.portalCommand.registerSubCommand("endportalblock", new EndPortalBlockSubCommand());
        this.portalCommand.registerSubCommand("endgatewayblock", new EndGatewayBlockSubCommand());
        this.portalCommand.registerSubCommand("create", new CreatePortalSubCommand());
        this.portalCommand.registerSubCommand("remove", new RemovePortalSubCommand());
        this.portalCommand.registerSubCommand("list", new ListPortalsSubCommand());
        this.portalCommand.registerSubCommand("show", new ShowPortalSubCommand());

        commandRegister.registerCommand("portal", this.portalCommand);
    }

    private void registerDestinationCommand(CommandRegister commandRegister) {
        this.destiCommand = new CommandWithSubCommands(this);
        this.destiCommand.registerSubCommand("create", new CreateDestiSubCommand());
        this.destiCommand.registerSubCommand("remove", new RemoveDestiSubCommand());
        this.destiCommand.registerSubCommand("teleport", new TeleportDestiSubCommand(), "tp");
        this.destiCommand.registerSubCommand("list", new ListDestiSubCommand());
        this.destiCommand.registerSubCommand("show", new ShowDestiSubCommand());

        commandRegister.registerCommand("destination", this.destiCommand);
    }

    public boolean registerPortalCommand(String arg, SubCommand subCommand, String... aliasArgs) {
        return this.portalCommand.registerSubCommand(arg, subCommand, aliasArgs);
    }

    public boolean registerDestiCommand(String arg, SubCommand subCommand, String... aliasArgs) {
        return this.destiCommand.registerSubCommand(arg, subCommand, aliasArgs);
    }

    /**
     * Loads the portal config into the memory and saves from the memory to check in case certain things have changed
     * (basically if values are missing or whatever)
     */
    public void loadPortalConfig() {
        this.configRepository.loadConfig(this.dataStorage);
        this.configRepository.storeConfig();
    }

    public void onDisable() {
        for(var playerContainer : this.serverContainer.getPlayers()) {
            playerDataRepository.playerLeave(playerContainer);
        }
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

    public GameScheduler getGameScheduler() {
        return gameScheduler;
    }

    public int[] getMcVersion() {
        return mcVersion;
    }

    public static AdvancedPortalsCore getInstance() {
        return instance;
    }

    public ServerContainer getServerContainer() {
        return serverContainer;
    }
}
