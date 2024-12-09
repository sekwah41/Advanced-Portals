package com.sekwah.advancedportals.spigot.importer;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;

public class LegacyImporter {
    private LegacyImporter() {
    }

    public static int importPortals(PortalServices portalServices) {
        ConfigAccessor portalConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "portals.yml");
        var config = portalConfig.getConfig();
        Set<String> portalSet = config.getKeys(false);

        int count = 0;
        for (String portalName : portalSet) {
            BlockLocation pos1 =
                new BlockLocation(config.getString(portalName + ".world"),
                                  config.getInt(portalName + ".pos1.X"),
                                  config.getInt(portalName + ".pos1.Y"),
                                  config.getInt(portalName + ".pos1.Z"));
            BlockLocation pos2 =
                new BlockLocation(config.getString(portalName + ".world"),
                                  config.getInt(portalName + ".pos2.X"),
                                  config.getInt(portalName + ".pos2.Y"),
                                  config.getInt(portalName + ".pos2.Z"));
            List<DataTag> args = new ArrayList<>();
            args.add(new DataTag("name", portalName));
            var triggerblock = config.getString(portalName + ".triggerblock");
            if (triggerblock != null)
                args.add(new DataTag("triggerblock", triggerblock.split(",")));
            // It's called bungee as that's the implementation behind it

            var destination = config.getString(portalName + ".destination");
            if (destination != null)
                args.add(new DataTag("destination", destination.split(",")));

            var bungee = config.getString(portalName + ".bungee");
            if (bungee != null) {
                if (destination == null) {
                    args.add(new DataTag("bungee", bungee.split(",")));
                } else {
                    args.add(new DataTag("proxy", bungee.split(",")));
                }
            }

            ConfigurationSection portalConfigSection =
                config.getConfigurationSection(portalName);
            ConfigurationSection portalArgsConf =
                portalConfigSection.getConfigurationSection("portalArgs");

            if (portalArgsConf != null) {
                Set<String> argsSet = portalArgsConf.getKeys(true);
                for (Object argName : argsSet.toArray()) {
                    // skip if it argName starts with command.
                    if (portalArgsConf.isString(argName.toString())) {
                        args.add(new DataTag(
                            argName.toString(),
                            portalArgsConf.getString(argName.toString())));
                    }
                }
            }
            // Check if command.1 is set
            List<String> commands = new ArrayList<>();
            if (getArg(args, "command.1") != null) {
                int i = 1;
                while (getArg(args, "command." + i) != null) {
                    commands.add(getArg(args, "command." + i));
                    i++;
                }
            }
            if (!commands.isEmpty()) {
                args.add(
                    new DataTag("commands", commands.toArray(new String[0])));
            }
            args.stream()
                .filter(dataTag -> dataTag.NAME.startsWith("command."))
                .toList()
                .forEach(args::remove);

            // Find an arg called "delayed" and add a new one called portalEvent
            var delayed = getArg(args, "delayed");
            if (delayed != null) {
                args.add(new DataTag("portalEvent", delayed));
                args.removeIf(dataTag -> dataTag.NAME.equals("delayed"));
            }

            var portal = portalServices.createPortal(pos1, pos2, args);

            if (portal != null)
                count++;
        }

        return count;
    }

    public static int importDestinations(
        DestinationServices destinationServices) {
        ConfigAccessor destiConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "destinations.yml");
        var config = destiConfig.getConfig();
        Set<String> destiSet = config.getKeys(false);

        int count = 0;
        for (String destiName : destiSet) {
            var destiPos = destiName + ".pos";
            var desti = destinationServices.createDesti(
                new PlayerLocation(
                    config.getString(destiName + ".world"),
                    config.getDouble(destiPos + ".X"),
                    config.getDouble(destiPos + ".Y"),
                    config.getDouble(destiPos + ".Z"),
                    (float) config.getDouble(destiPos + ".yaw"),
                    (float) config.getDouble(destiPos + ".pitch")),
                List.of(new DataTag("name", destiName)));
            if (desti != null)
                count++;
        }
        return count;
    }

    public static String getArg(List<DataTag> tags, String arg) {
        for (DataTag portalArg : tags) {
            if (arg.equals(portalArg.NAME)) {
                return portalArg.VALUES[0];
            }
        }
        return null;
    }
}
