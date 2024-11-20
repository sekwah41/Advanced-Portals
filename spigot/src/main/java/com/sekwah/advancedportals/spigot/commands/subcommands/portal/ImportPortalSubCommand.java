package com.sekwah.advancedportals.spigot.commands.subcommands.portal;

import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.spigot.importer.ConfigAccessor;
import com.sekwah.advancedportals.spigot.importer.LegacyImporter;

import java.util.List;
import java.util.Set;

public class ImportPortalSubCommand implements SubCommand {

    @Inject
    DestinationServices destinationServices;

    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1 && "confirm".equals(args[1])) {
            sender.sendMessage(Lang.getPositivePrefix()
                               + Lang.translateInsertVariables(
                                   "command.portal.import.confirm"));
            int destinations = LegacyImporter.importDestinations(destinationServices);
            int portals = LegacyImporter.importPortals(portalServices);
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
        var config = destiConfig.getConfig();
        Set<String> destiSet = config.getKeys(false);

        return destiSet.size();
    }

    public int getPortalCount() {
        ConfigAccessor portalConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "portals.yaml");
        var config = portalConfig.getConfig();
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
