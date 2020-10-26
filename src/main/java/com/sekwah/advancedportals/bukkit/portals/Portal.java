package com.sekwah.advancedportals.bukkit.portals;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.PluginMessages;
import com.sekwah.advancedportals.bukkit.api.portaldata.PortalArg;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.effects.WarpEffects;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Portal {

    public static HashMap<String, Long> joinCooldown = new HashMap<String, Long>();
    public static HashMap<String, HashMap<String, Long>> cooldown = new HashMap<String, HashMap<String, Long>>();
    // Config values
    public static boolean portalsActive = false;
    public static AdvancedPortal[] portals = new AdvancedPortal[0];
    private static AdvancedPortalsPlugin plugin;
    public static ConfigAccessor portalData = new ConfigAccessor(plugin, "portals.yml");
    private static boolean showBungeeMessage;
    private static double throwback;
    private static Sound portalSound;
    private static int portalProtectionRadius;
    private static boolean blockSpectatorMode;
    private static int joinCooldownDelay;
    private static Random random = new Random();

    public Portal(AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.showBungeeMessage = config.getConfig().getBoolean("ShowBungeeWarpMessage", false);

        this.portalProtectionRadius = config.getConfig().getBoolean("PortalProtection") ?
                config.getConfig().getInt("PortalProtectionArea") : 0;

        this.throwback = config.getConfig().getDouble("ThrowbackAmount", 0.7);

        this.portalSound = WarpEffects.findSound(plugin, "BLOCK_PORTAL_TRAVEL", "PORTAL_TRAVEL");
        this.blockSpectatorMode = config.getConfig().getBoolean("BlockSpectatorMode", false);

        this.joinCooldownDelay = config.getConfig().getInt("PortalCooldown", 5);

        Portal.plugin = plugin;
        Portal.loadPortals();
    }

    /**
     * This can be used to move the get keys to different sections
     * <p>
     * ConfigurationSection section = portalData.getSection("sectionname");
     * <p>
     * section.getKeys(false);
     */

    public static void loadPortals() {
        portalData = new ConfigAccessor(plugin, "portals.yml");
        Set<String> PortalSet = portalData.getConfig().getKeys(false);
        if (PortalSet.size() > 0) {
            portals = new AdvancedPortal[PortalSet.toArray().length];

            /*
             * for(int i = 0; i <= PortalSet.toArray().length - 1; i++){ portals[i] = new
             * AdvancedPortal(); }
             */

            int portalId = 0;
            for (Object portal : PortalSet.toArray()) {

                ConfigurationSection portalConfigSection = portalData.getConfig()
                        .getConfigurationSection(portal.toString());

                String blockTypesRaw = portalConfigSection.getString("triggerblock");

                String[] blockTypesString = blockTypesRaw != null ? blockTypesRaw.split(",") : null;

                HashSet<Material> blockTypes = getMaterialSet(blockTypesString);

                if (blockTypes.isEmpty()) {
                    blockTypes.add(Material.NETHER_PORTAL);
                }

                ConfigurationSection portalArgsConf = portalConfigSection.getConfigurationSection("portalArgs");

                ArrayList<PortalArg> extraData = new ArrayList<>();

                if (portalArgsConf != null) {
                    Set<String> argsSet = portalArgsConf.getKeys(true);

                    for (Object argName : argsSet.toArray()) {
                        if (portalArgsConf.isString(argName.toString())) {
                            extraData.add(
                                    new PortalArg(argName.toString(), portalArgsConf.getString(argName.toString())));
                        }
                    }
                }

                String worldName = portalData.getConfig().getString(portal.toString() + ".world");
                if (worldName != null) {
                    World world = Bukkit.getWorld(worldName);
                    Location pos1 = new Location(world, portalData.getConfig().getInt(portal.toString() + ".pos1.X"),
                            portalData.getConfig().getInt(portal.toString() + ".pos1.Y"),
                            portalData.getConfig().getInt(portal.toString() + ".pos1.Z"));
                    Location pos2 = new Location(world, portalData.getConfig().getInt(portal.toString() + ".pos2.X"),
                            portalData.getConfig().getInt(portal.toString() + ".pos2.Y"),
                            portalData.getConfig().getInt(portal.toString() + ".pos2.Z"));

                    PortalArg[] portalArgs = new PortalArg[extraData.size()];
                    extraData.toArray(portalArgs);

                    portals[portalId] = new AdvancedPortal(portal.toString(), blockTypes, pos1, pos2, worldName,
                            portalArgs);

                    portals[portalId].setBungee(portalConfigSection.getString("bungee"));

                    portals[portalId].setDestiation(portalConfigSection.getString("destination"));

                    portalId++;
                } else {
                    AdvancedPortal[] tempPortals = portals;

                    portals = new AdvancedPortal[portals.length - 1];

                    System.arraycopy(tempPortals, 0, portals, 0, portalId);
                }
            }
            portalsActive = true;
        } else {
            portalsActive = false;
            portals = new AdvancedPortal[0];
        }
    }

    public static HashSet<Material> getMaterialSet(String[] blockTypesString) {
        HashSet<Material> blockTypes = new HashSet<>();

        if (blockTypesString != null) {
            for (String blockType : blockTypesString) {
                Material material = Material.getMaterial(blockType);
                if (material != null) {
                    blockTypes.add(material);
                }
            }
        }

        return blockTypes;
    }

    public static String create(Location pos1, Location pos2, String name, String destination,
            Set<Material> triggerBlocks, PortalArg... extraData) {
        return create(pos1, pos2, name, destination, triggerBlocks, null, extraData);
    }

    public static String create(Location pos1, Location pos2, String name, String destination,
            Set<Material> triggerBlocks, String serverName, PortalArg... portalArgs) {

        if (!pos1.getWorld().equals(pos2.getWorld())) {
            plugin.getLogger().log(Level.WARNING, "pos1 and pos2 must be in the same world!");
            return "\u00A7cPortal creation error, pos1 and pos2 must be in the same world!";
        }

        int LowX = 0;
        int LowY = 0;
        int LowZ = 0;

        int HighX = 0;
        int HighY = 0;
        int HighZ = 0;

        if (pos1.getX() > pos2.getX()) {
            LowX = (int) pos2.getX();
            HighX = (int) pos1.getX();
        } else {
            LowX = (int) pos1.getX();
            HighX = (int) pos2.getX();
        }
        if (pos1.getY() > pos2.getY()) {
            LowY = (int) pos2.getY();
            HighY = (int) pos1.getY();
        } else {
            LowY = (int) pos1.getY();
            HighY = (int) pos2.getY();
        }
        if (pos1.getZ() > pos2.getZ()) {
            LowZ = (int) pos2.getZ();
            HighZ = (int) pos1.getZ();
        } else {
            LowZ = (int) pos1.getZ();
            HighZ = (int) pos2.getZ();
        }

        Location checkpos1 = new Location(pos1.getWorld(), HighX, HighY, HighZ);
        Location checkpos2 = new Location(pos2.getWorld(), LowX, LowY, LowZ);

        /*
         * if (checkPortalOverlap(checkpos1, checkpos2)) {
         * plugin.getLogger().log(Level.WARNING, "portals must not overlap!"); return
         * "\u00A7cPortal creation error, portals must not overlap!"; }
         */

        portalData.getConfig().set(name + ".world", pos1.getWorld().getName());

        String store = triggerBlocks.stream().map(Enum::name).collect(Collectors.joining(","));
        portalData.getConfig().set(name + ".triggerblock", store);

        portalData.getConfig().set(name + ".destination", destination);

        portalData.getConfig().set(name + ".bungee", serverName);

        portalData.getConfig().set(name + ".pos1.X", HighX);
        portalData.getConfig().set(name + ".pos1.Y", HighY);
        portalData.getConfig().set(name + ".pos1.Z", HighZ);

        portalData.getConfig().set(name + ".pos2.X", LowX);
        portalData.getConfig().set(name + ".pos2.Y", LowY);
        portalData.getConfig().set(name + ".pos2.Z", LowZ);

        for (PortalArg arg : portalArgs) {
            portalData.getConfig().set(name + ".portalArgs." + arg.argName, arg.value);
        }

        portalData.saveConfig();

        loadPortals();

        return "\u00A7aPortal creation successful!";
    }

    private static boolean checkPortalOverlap(Location pos1, Location pos2) {

        if (portalsActive) {
            int portalId = 0;
            for (@SuppressWarnings("unused")
            Object portal : Portal.portals) {
                if (portals[portalId].getWorldName().equals(pos2.getWorld().getName())) { // checks that the cubes arnt
                                                                                          // overlapping by seeing if
                                                                                          // all 4 corners are not in
                                                                                          // side another
                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos1().getBlockX(),
                            portals[portalId].getPos1().getBlockY(), portals[portalId].getPos1().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos1().getBlockX(),
                            portals[portalId].getPos1().getBlockY(), portals[portalId].getPos2().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos1().getBlockX(),
                            portals[portalId].getPos2().getBlockY(), portals[portalId].getPos1().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos2().getBlockX(),
                            portals[portalId].getPos1().getBlockY(), portals[portalId].getPos1().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos2().getBlockX(),
                            portals[portalId].getPos2().getBlockY(), portals[portalId].getPos2().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos2().getBlockX(),
                            portals[portalId].getPos1().getBlockY(), portals[portalId].getPos2().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos1().getBlockX(),
                            portals[portalId].getPos2().getBlockY(), portals[portalId].getPos2().getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, portals[portalId].getPos2().getBlockX(),
                            portals[portalId].getPos2().getBlockY(), portals[portalId].getPos1().getBlockZ())) {
                        return true;
                    }
                }
                portalId++;
            }
        }
        return false;
    }

    private static boolean checkOverLapPortal(Location pos1, Location pos2, int posX, int posY, int posZ) {
        if (pos1.getX() >= posX && pos1.getY() >= posX && pos1.getZ() >= posZ) {
            if ((pos2.getX()) <= posX && pos2.getY() <= posY && pos2.getZ() <= posZ) {
                return true;
            }
        }
        return false;
    }

    public static String create(Location pos1, Location pos2, String name, String destination, String serverName,
            PortalArg... extraData) { // add stuff for destination names or coordinates
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");

        Material triggerBlockType;
        String BlockID = config.getConfig().getString("DefaultPortalTriggerBlock");
        triggerBlockType = Material.getMaterial(BlockID);

        if (triggerBlockType == null) {
            triggerBlockType = Material.NETHER_PORTAL;
        }

        return create(pos1, pos2, name, destination, new HashSet<>(Collections.singletonList(triggerBlockType)),
                serverName, extraData);
    }

    public static void redefine(Location pos1, Location pos2, String name) {

        portalData.getConfig().set(name + ".pos1.X", pos1.getX());
        portalData.getConfig().set(name + ".pos1.Y", pos1.getY());
        portalData.getConfig().set(name + ".pos1.Z", pos1.getZ());

        portalData.getConfig().set(name + ".pos2.X", pos2.getX());
        portalData.getConfig().set(name + ".pos2.Y", pos2.getY());
        portalData.getConfig().set(name + ".pos2.Z", pos2.getZ());

        portalData.saveConfig();

        loadPortals();

    }

    public static String getDestination(String portalName) {
        return portalData.getConfig().getString(portalName + ".destination");
    }

    public static void remove(String name) {

        Object[] keys = portalData.getConfig().getKeys(true).toArray();
        for (int i = keys.length - 1; i >= 0; i--) {
            String key = keys[i].toString();
            if (key.startsWith(name + ".")) {
                portalData.getConfig().set(key, null);
            }
        }
        portalData.getConfig().set(name, null);

        // TODO add code to check if people have the portal selected and notify if
        // removed.

        /**
         * Set<String> keys = portalData.getConfig().getKeys(true); for(String key:
         * keys){ if(key.startsWith(name)){ portalData.getConfig().set(key, null); } }
         */

        /**
         * portalData.getConfig().set(name + ".world", null);
         * portalData.getConfig().set(name + ".triggerblock", null);
         * portalData.getConfig().set(name + ".destination", null);
         *
         * portalData.getConfig().set(name + ".pos1.X", null);
         * portalData.getConfig().set(name + ".pos1.Y", null);
         * portalData.getConfig().set(name + ".pos1.Z", null);
         *
         * portalData.getConfig().set(name + ".pos2.X", null);
         * portalData.getConfig().set(name + ".pos2.Y", null);
         * portalData.getConfig().set(name + ".pos2.Z", null);
         *
         * portalData.getConfig().set(name + ".pos1", null);
         * portalData.getConfig().set(name + ".getPos2()", null);
         *
         * portalData.getConfig().set(name, null);
         */

        portalData.saveConfig();

        loadPortals();
    }

    public static AdvancedPortal getPortal(String portalName) {
        for (AdvancedPortal portalElement : Portal.portals) {
            if (portalElement.getName().equals(portalName)) {
                return portalElement;
            }
        }
        return null;
    }

    public static boolean portalExists(String portalName) {

        String posX = portalData.getConfig().getString(portalName + ".pos1.X");

        return posX != null;
    }

    public static boolean activate(Player player, String portalName) {
        for (AdvancedPortal portal : Portal.portals) {
            if (portal.getName().equals(portalName))
                return activate(player, portal);
        }
        plugin.getLogger().log(Level.SEVERE, "Portal not found by name of: " + portalName);
        return false;
    }

    public static boolean activate(Player player, AdvancedPortal portal, boolean doKnockback) {

        if (blockSpectatorMode && player.getGameMode() == GameMode.SPECTATOR) {
            player.sendMessage(
                    PluginMessages.customPrefixFail + "\u00A7c You cannot enter a portal in spectator mode!");
            return false;
        }

        String permission = portal.getArg("permission");

        boolean noMessage = permission != null && permission.startsWith("nomsg.");
        if(noMessage) {
            permission.substring(6);
        }

        if (!(permission == null || player.hasPermission(permission) || player.isOp())) {
            if(!noMessage) {
                player.sendMessage(
                        PluginMessages.customPrefixFail + "\u00A7c You do not have permission to use this portal!");
                failSound(player, portal);
                if(doKnockback)
                    throwPlayerBack(player);
            }
            return false;
        }

        Long joinCD = joinCooldown.get(player.getName());
        if (joinCD != null) {
            int diff = (int) ((System.currentTimeMillis() - joinCD) / 1000);
            if (diff < joinCooldownDelay) {
                int time = (joinCooldownDelay - diff);
                player.sendMessage(ChatColor.RED + "There is " + ChatColor.YELLOW + time + ChatColor.RED + (time == 1 ? " second" : " seconds") + " join cooldown protection left.");
                failSound(player, portal);
                if(doKnockback)
                    throwPlayerBack(player);
                return false;
            }
            joinCooldown.remove(player.getName());
        }

        HashMap<String, Long> cds = cooldown.get(player.getName());
        if (cds != null) {
            if (cds.get(portal.getName()) != null) {
                long portalCD = cds.get(portal.getName());
                int diff = (int) ((System.currentTimeMillis() - portalCD) / 1000);
                int portalCooldown = 0; // default cooldowndelay when cooldowndelay is not specified
                try {
                    portalCooldown = Integer.parseInt(portal.getArg("cooldowndelay"));
                } catch (Exception ignored) {
                }
                if (diff < portalCooldown) {
                    int time = (portalCooldown - diff);
                    player.sendMessage(ChatColor.RED + "Please wait " + ChatColor.YELLOW + time + ChatColor.RED
                            + (time == 1 ? " second" : " seconds") + " until attempting to enter this portal again.");
                    failSound(player, portal);
                    if(doKnockback)
                        throwPlayerBack(player);
                    return false;
                }
            }
        }
        if (cds == null) {
            cds = new HashMap<String, Long>();
        }
        cds.put(portal.getName(), System.currentTimeMillis());
        cooldown.put(player.getName(), cds);

        boolean showFailMessage = !portal.hasArg("command.1");

        boolean hasMessage = portal.getArg("message") != null;

        // plugin.getLogger().info(portal.getName() + ":" + portal.getDestiation());
        boolean warped = false;
        if (portal.getBungee() != null) {
            String[] bungeeServers = portal.getBungee().split(",");
            String bungeeServer = bungeeServers[random.nextInt(bungeeServers.length)];
            if (showBungeeMessage) {
                player.sendMessage(PluginMessages.customPrefix + "\u00A7a Attempting to warp to \u00A7e" + bungeeServer
                        + "\u00A7a.");
            }

            if(portal.hasArg("leavedesti")) {
                player.setMetadata("leaveDesti", new FixedMetadataValue(plugin, portal.getArg("leavedesti")));
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.removeMetadata("leaveDesti", plugin);
                }, 20 * 10);
            }

            if (portal.getDestiation() != null) {
                if(plugin.registeredBungeeChannels) {
                    ByteArrayDataOutput outForList = ByteStreams.newDataOutput();
                    outForList.writeUTF(BungeeMessages.ENTER_PORTAL);
                    outForList.writeUTF(bungeeServer);
                    outForList.writeUTF(portal.getDestiation());
                    outForList.writeUTF(player.getUniqueId().toString());

                    player.sendPluginMessage(plugin, plugin.channelName, outForList.toByteArray());
                }
                else {
                    plugin.getLogger().log(Level.WARNING, "You do not have bungee setup correctly. Cross server destinations won't work.");
                }

            }

            ByteArrayDataOutput outForSend = ByteStreams.newDataOutput();
            outForSend.writeUTF("Connect");
            outForSend.writeUTF(bungeeServer);



            portal.inPortal.add(player.getUniqueId());
            player.sendPluginMessage(plugin, "BungeeCord", outForSend.toByteArray());
            // Down to bungee to sort out the teleporting but yea theoretically they should
            // warp.
        } else if (portal.getDestiation() != null) {
            ConfigAccessor configDesti = new ConfigAccessor(plugin, "destinations.yml");
            if (configDesti.getConfig().getString(portal.getDestiation() + ".world") != null) {
                warped = Destination.warp(player, portal.getDestiation(), hasMessage);
                if (!warped) {
                    if(doKnockback)
                        throwPlayerBack(player);
                }
            }
        } else {
            if (showFailMessage) {
                player.sendMessage(PluginMessages.customPrefixFail
                        + "\u00A7c The portal you are trying to use doesn't have a destination!");
                plugin.getLogger().log(Level.SEVERE, "The portal '" + portal.getName() + "' has just had a warp "
                        + "attempt and either the data is corrupt or portal doesn't exist!");
                if(doKnockback)
                    throwPlayerBack(player);
                failSound(player, portal);
            }
        }

        if (portal.hasArg("command.1")) {
            warped = true;
            int commandLine = 1;
            String command = portal.getArg("command." + commandLine);// portalData.getConfig().getString(portal.getName()+
                                                                     // ".portalArgs.command." + commandLine);
            do {
                // (?i) makes the search case insensitive
                command = command.replaceAll("@player", player.getName());
                plugin.getLogger().log(Level.INFO, "Portal command: " + command);
                if (command.startsWith("#") && plugin.getSettings().enabledCommandLevel("c")) {
                    command = command.substring(1);
                    plugin.getLogger().log(Level.INFO, "Portal command: " + command);
                    try {
                        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                    } catch (Exception e) {
                        plugin.getLogger().warning("Error while executing: " + command);
                    }
                } else if (command.startsWith("!") && plugin.getSettings().enabledCommandLevel("o")) {
                    command = command.substring(1);
                    boolean wasOp = player.isOp();
                    try {
                        player.setOp(true);
                        player.chat("/" + command);
                        // player.performCommand(command);
                    } finally {
                        player.setOp(wasOp);
                    }
                } else if (command.startsWith("^") && plugin.getSettings().enabledCommandLevel("p")) {
                    command = command.substring(1);
                    PermissionAttachment permissionAttachment = null;
                    try {
                        permissionAttachment = player.addAttachment(plugin, "*", true);
                        player.chat("/" + command);
                        // player.performCommand(command);
                    } finally {
                        player.removeAttachment(permissionAttachment);
                    }
                } else if (command.startsWith("%") && plugin.getSettings().enabledCommandLevel("b")) {
                    if(plugin.registeredBungeeChannels) {
                        command = command.substring(1);
                        ByteArrayDataOutput outForList = ByteStreams.newDataOutput();
                        outForList.writeUTF(BungeeMessages.BUNGEE_COMMAND);
                        outForList.writeUTF(command);
                        player.sendPluginMessage(plugin, plugin.channelName, outForList.toByteArray());
                    }
                    else {
                        plugin.getLogger().log(Level.WARNING, "You do not have bungee setup correctly. For security advanced bungee features won't work.");
                    }

                } else {
                        player.chat("/" + command);
                        // player.performCommand(command);
                }
                command = portal.getArg("command." + ++commandLine);
            } while (command != null);
        }

        if (warped) {
            if (hasMessage) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(portal.getArg("message").replaceAll("&(?=[0-9a-fk-or])", "\u00A7")));
            }
        }

        return warped;
    }

    private static void failSound(Player player, AdvancedPortal portal) {
        if (!(portal.getTriggers().contains(Material.NETHER_PORTAL) && player.getGameMode() == GameMode.CREATIVE)) {
            player.playSound(player.getLocation(), portalSound, 0.2f, new Random().nextFloat() * 0.4F + 0.8F);
        }
    }

    public static void rename(String oldName, String newName) {

        // set it so it gets all data from one and puts it into another place

        ConfigAccessor config = new ConfigAccessor(plugin, "portals.yml");

        Set<String> keys = config.getConfig().getKeys(true);
        for (String key : keys) {
            if (key.startsWith(oldName + ".")) {
                if (config.getConfig().getString(key) != null) {
                    try {
                        int intData = Integer.parseInt(config.getConfig().getString(key));
                        config.getConfig().set(key.replace(oldName + ".", newName + "."), intData);
                    } catch (Exception e) {
                        config.getConfig().set(key.replace(oldName + ".", newName + "."),
                                config.getConfig().getString(key));
                    }

                }
            }
        }

        config.saveConfig();

        remove(oldName);

    }

    public static boolean addCommand(String portalName, String portalCommand) {
        ConfigAccessor config = new ConfigAccessor(plugin, "portals.yml");
        if (portalExists(portalName)) {
            int commandLine = 0;
            while (config.getConfig().getString(portalName + ".portalArgs.command." + ++commandLine) != null)
                ; // Loops increasing commandLine till 1 is null
            config.getConfig().set(portalName + ".portalArgs.command." + commandLine, portalCommand);
            config.saveConfig();
            loadPortals();
            return true;
        } else {
            return false;
        }
    }

    public static boolean inPortalTriggerRegion(Location loc) {
        for (AdvancedPortal portal : Portal.portals)
            if (Portal.locationInPortalTrigger(portal, loc))
                return true;
        return false;
    }

    public static boolean locationInPortalTrigger(AdvancedPortal portal, Location loc, int additionalArea) {
        return portal.getTriggers().contains(loc.getBlock().getType()) && locationInPortal(portal, loc, additionalArea);
    }

    public static boolean locationInPortalTrigger(AdvancedPortal portal, Location loc) {
        return locationInPortalTrigger(portal, loc, 0);
    }

    public static boolean inPortalRegion(Location loc, int additionalArea) {
        for (AdvancedPortal portal : Portal.portals)
            if (Portal.locationInPortal(portal, loc, additionalArea))
                return true;
        return false;
    }

    public static boolean locationInPortal(Location loc, int additionalArea) {
        for (AdvancedPortal portal : Portal.portals)
            if (Portal.locationInPortal(portal, loc, additionalArea))
                return true;
        return false;
    }

    public static boolean locationInPortal(AdvancedPortal portal, Location loc) {
        return locationInPortal(portal, loc);
    }

    public static boolean locationInPortal(AdvancedPortal portal, Location loc, int additionalArea) {
        if (!portalsActive)
            return false;
        if (loc.getWorld() != null && portal.getWorldName().equals(loc.getWorld().getName()))
            if ((portal.getPos1().getX() + 1 + additionalArea) >= loc.getX()
                    && (portal.getPos1().getY() + 1 + additionalArea) > loc.getY()
                    && (portal.getPos1().getZ() + 1 + additionalArea) >= loc.getZ())
                if (portal.getPos2().getX() - additionalArea <= loc.getX()
                        && portal.getPos2().getY() - additionalArea <= loc.getY()
                        && portal.getPos2().getZ() - additionalArea <= loc.getZ())
                    return true;
        return false;
    }

    public static void throwPlayerBack(Player player) {
        // Not ensured to remove them out of the portal but it makes it feel nicer for
        // the player.
        if (throwback > 0) {
            Vector velocity = player.getLocation().getDirection();
            player.setVelocity(velocity.setY(0).normalize().multiply(-1).setY(throwback));
        }
    }

    public static int getPortalProtectionRadius() {
        return portalProtectionRadius;
    }

    public static boolean activate(Player player, AdvancedPortal portal) {
        return activate(player, portal, true);
    }
}
