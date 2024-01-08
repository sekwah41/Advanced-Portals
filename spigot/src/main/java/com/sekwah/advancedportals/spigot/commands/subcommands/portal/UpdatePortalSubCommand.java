package com.sekwah.advancedportals.spigot.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.spigot.commands.subcommands.portal.update.ConfigAccessor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UpdatePortalSubCommand implements SubCommand {

    @Inject
    PortalServices portalServices;

    @Inject
    DestinationServices destinationServices;

    @Inject
    PortalServices portalService;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1 && "confirm".equals(args[1])) {
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translateInsertVariables("command.portal.update.confirm"));
            int destinations = importDestinations();
            int portals = importPortals();
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translateInsertVariables("command.portal.update.complete", portals, destinations));
            return;
        }
        sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translateInsertVariables("command.portal.update", getPortalCount(), getDestinationCount()));
    }

    private int importPortals() {
        ConfigAccessor portalConfig = new ConfigAccessor(AdvancedPortalsPlugin.getInstance(), "portals.yml");
        var config = portalConfig.getConfig();
        Set<String> portalSet = config.getKeys(false);

        int count = 0;
        for(String portalName : portalSet) {
            BlockLocation pos1 = new BlockLocation(config.getString(portalName + ".world"),
                    config.getInt(portalName + ".pos1.X"),
                    config.getInt(portalName + ".pos1.Y"),
                    config.getInt(portalName + ".pos1.Z"));
            BlockLocation pos2 = new BlockLocation(config.getString(portalName + ".world"),
                    config.getInt(portalName + ".pos2.X"),
                    config.getInt(portalName + ".pos2.Y"),
                    config.getInt(portalName + ".pos2.Z"));
            List<DataTag> args = new ArrayList<>();
            args.add(new DataTag("name", portalName));
            args.add(new DataTag("triggerblock", config.getString(portalName + ".triggerblock")));
            // It's called bungee as that's the implementation behind it
            args.add(new DataTag("bungee", config.getString(portalName + ".bungee")));
            args.add(new DataTag("destination", config.getString(portalName + ".destination")));

            ConfigurationSection portalConfigSection = config.getConfigurationSection(portalName);
            ConfigurationSection portalArgsConf = portalConfigSection.getConfigurationSection("portalArgs");

            if (portalArgsConf != null) {
                Set<String> argsSet = portalArgsConf.getKeys(true);
                for (Object argName : argsSet.toArray()) {
                    // skip if it argName starts with command.
                    if (portalArgsConf.isString(argName.toString())) {
                        args.add(new DataTag(argName.toString(), portalArgsConf.getString(argName.toString())));
                    }
                }
            }
            // Check if command.1 is set
            List<String> commands = new ArrayList<>();
            if(getArg(args, "command.1") != null) {
                int i = 1;
                while(getArg(args, "command." + i) != null) {
                    commands.add(getArg(args, "command." + i));
                    i++;
                }
            }
            if(!commands.isEmpty()) {
                args.add(new DataTag("commands", commands.toArray(new String[0])));
            }
            args.stream().filter(dataTag -> dataTag.NAME.startsWith("command.")).toList().forEach(args::remove);

            var portal = portalService.createPortal(pos1, pos2, args);

            if(portal != null) count++;
        }

        return count;
    }

    public String getArg(List<DataTag> tags, String arg) {
        for (DataTag portalArg : tags) {
            if (arg.equals(portalArg.NAME)) {
                return portalArg.VALUES[0];
            }
        }
        return null;
    }

    public int importDestinations() {
        ConfigAccessor destiConfig = new ConfigAccessor(AdvancedPortalsPlugin.getInstance(), "destinations.yml");
        var config = destiConfig.getConfig();
        Set<String> destiSet = config.getKeys(false);

        int count = 0;
        for(String destiName : destiSet) {
            var desti = destinationServices.createDesti(new PlayerLocation(config.getString(destiName + ".world"),
                    config.getDouble(destiName + ".x"),
                    config.getDouble(destiName + ".y"),
                    config.getDouble(destiName + ".z"),
                    (float) config.getDouble(destiName + ".yaw"),
                    (float) config.getDouble(destiName + ".pitch")), List.of(new DataTag("name", destiName)));
            if(desti != null) count++;
        }
        return count;
    }

    public int getDestinationCount() {
        ConfigAccessor destiConfig = new ConfigAccessor(AdvancedPortalsPlugin.getInstance(), "destinations.yml");
        var config = destiConfig.getConfig();
        Set<String> destiSet = config.getKeys(false);

        return destiSet.size();
    }

    public int getPortalCount() {
        ConfigAccessor portalConfig = new ConfigAccessor(AdvancedPortalsPlugin.getInstance(), "portals.yml");
        var config = portalConfig.getConfig();
        Set<String> portalSet = config.getKeys(false);

        return portalSet.size();
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.update.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.update.detailedhelp");
    }
}
