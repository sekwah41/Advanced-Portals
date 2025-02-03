package com.sekwah.advancedportals.legacyspigot.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.legacyspigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.legacyspigot.importer.ConfigAccessor;
import com.sekwah.advancedportals.legacyspigot.importer.LegacyImporter;
import com.google.inject.Inject;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class ImportPortalSubCommand implements SubCommand {
    @Inject
    private AdvancedPortalsCore portalsCore;

    @Inject
    DestinationServices destinationServices;

    @Inject
    PortalServices portalServices;

    @Inject
    ConfigRepository configRepo;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1 && "confirm".equals(args[1])) {
            sender.sendMessage(Lang.getPositivePrefix()
                               + Lang.translateInsertVariables(
                                   "command.portal.import.confirm"));
            int destinations =
                LegacyImporter.importDestinations(destinationServices);
            int portals = LegacyImporter.importPortals(portalServices);
            LegacyImporter.importConfig(this.configRepo);

            portalsCore.loadPortalConfig();
            portalServices.loadPortals();
            destinationServices.loadDestinations();

            sender.sendMessage(
                Lang.getPositivePrefix()
                + Lang.translateInsertVariables(
                    "command.portal.import.complete", portals, destinations));
            return;
        }
        sender.sendMessage(Lang.getPositivePrefix()
                           + Lang.translateInsertVariables(
                               "command.portal.import", getPortalCount(),
                               getDestinationCount()));
    }

    public int getDestinationCount() {
        ConfigAccessor destiConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "destinations.yaml");
        FileConfiguration config = destiConfig.getConfig();
        Set<String> destiSet = config.getKeys(false);

        return destiSet.size();
    }

    public int getPortalCount() {
        ConfigAccessor portalConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "portals.yaml");
        FileConfiguration config = portalConfig.getConfig();
        Set<String> portalSet = config.getKeys(false);

        return portalSet.size();
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.IMPORT.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.import.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.import.detailedhelp");
    }
}
