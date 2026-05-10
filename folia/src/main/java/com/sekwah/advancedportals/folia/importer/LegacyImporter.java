package com.sekwah.advancedportals.spigot.importer;

import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.config.CommandPortalConfig;
import com.sekwah.advancedportals.core.serializeddata.config.Config;
import com.sekwah.advancedportals.core.serializeddata.config.WarpEffectConfig;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import java.util.*;
import java.util.stream.Collectors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LegacyImporter {
    private LegacyImporter() {
    }

    public static int importPortals(PortalServices portalServices) {
        // Check if the file exists and skip if it doesn't
        ConfigAccessor portalConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "portals.yml");
        FileConfiguration config = portalConfig.getConfig();
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
            String triggerblock =
                config.getString(portalName + ".triggerblock");
            if (triggerblock != null)
                args.add(new DataTag("triggerblock", triggerblock.split(",")));
            // It's called bungee as that's the implementation behind it

            String destination = config.getString(portalName + ".destination");
            if (destination != null)
                args.add(new DataTag("destination", destination.split(",")));

            String bungee = config.getString(portalName + ".bungee");
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
                    new DataTag("command", commands.toArray(new String[0])));
            }
            args.stream()
                .filter(dataTag -> dataTag.NAME.startsWith("command."))
                .collect(Collectors.toList())
                .forEach(args::remove);

            // Find an arg called "delayed" and add a new one called portalEvent
            String delayed = getArg(args, "delayed");
            if (delayed != null) {
                args.add(new DataTag("portalEvent", delayed));
                args.removeIf(dataTag -> dataTag.NAME.equals("delayed"));
            }

            AdvancedPortal portal =
                portalServices.createPortal(null, pos1, pos2, args);

            if (portal != null)
                count++;
        }

        return count;
    }

    public static int importDestinations(
        DestinationServices destinationServices) {
        ConfigAccessor destiConfig = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "destinations.yml");
        FileConfiguration config = destiConfig.getConfig();
        Set<String> destiSet = config.getKeys(false);

        int count = 0;
        for (String destiName : destiSet) {
            String destiPos = destiName + ".pos";
            Destination desti = destinationServices.createDesti(
                new PlayerLocation(
                    config.getString(destiName + ".world"),
                    config.getDouble(destiPos + ".X"),
                    config.getDouble(destiPos + ".Y"),
                    config.getDouble(destiPos + ".Z"),
                    (float) config.getDouble(destiPos + ".yaw"),
                    (float) config.getDouble(destiPos + ".pitch")),
                Collections.singletonList(new DataTag("name", destiName)));
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

    public static void importConfig(ConfigRepository configRepo) {
        Config config = new Config();
        ConfigAccessor configOldAccessor = new ConfigAccessor(
            AdvancedPortalsPlugin.getInstance(), "config.yml");
        FileConfiguration configOld = configOldAccessor.getConfig();

        config.useOnlySpecialAxe = configOld.getBoolean(
            "UseOnlyServerMadeAxe", config.useOnlySpecialAxe);
        config.blockSpectatorMode = configOld.getBoolean(
            "BlockSpectatorMode", config.blockSpectatorMode);
        config.selectorMaterial =
            configOld.getString("AxeItemId", config.selectorMaterial);
        config.portalProtection =
            configOld.getBoolean("PortalProtection", config.portalProtection);
        config.portalProtectionRadius = configOld.getInt(
            "PortalProtectionArea", config.portalProtectionRadius);
        config.defaultTriggerBlock = configOld.getString(
            "DefaultPortalTriggerBlock", config.defaultTriggerBlock);
        if (config.defaultTriggerBlock.equals("PORTAL")) {
            config.defaultTriggerBlock = "NETHER_PORTAL";
        }
        config.stopWaterFlow =
            configOld.getBoolean("StopWaterFlow", config.stopWaterFlow);
        config.joinCooldown =
            configOld.getInt("PortalCooldown", config.joinCooldown);
        config.throwbackStrength =
            configOld.getDouble("ThrowbackAmount", config.throwbackStrength);
        config.playFailSound =
            configOld.getBoolean("PlayFailSound", config.playFailSound);
        config.warpMessageOnActionBar =
            configOld.getInt("WarpMessageDisplay", 2) == 2;
        config.warpMessageInChat =
            configOld.getInt("WarpMessageDisplay", 2) == 1;
        config.enableProxySupport = configOld.getBoolean(
            "ForceEnableProxySupport", config.enableProxySupport);
        config.disableGatewayBeam = configOld.getBoolean(
            "DisableGatewayBeam", config.disableGatewayBeam);

        CommandPortalConfig commandConfig = new CommandPortalConfig();
        String commandString = configOld.getString("CommandLevels", "opcb");
        commandConfig.enabled = !commandString.contains("n");
        commandConfig.op = commandString.contains("o");
        commandConfig.permsWildcard = commandString.contains("p");
        commandConfig.console = commandString.contains("c");
        commandConfig.proxy = commandString.contains("b");

        config.commandPortals = commandConfig;

        WarpEffectConfig warpEffectConfig = new WarpEffectConfig();
        warpEffectConfig.visualEffect =
            configOld.getInt("WarpParticles", 1) == 1 ? "ender" : null;
        warpEffectConfig.soundEffect =
            configOld.getInt("WarpSound", 1) == 1 ? "ender" : null;
        config.warpEffect = warpEffectConfig;

        configRepo.importConfig(config);
    }
}
