package com.sekwah.advancedportals.portals;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.destinations.Destination;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;

public class Portal {

    public static boolean portalsActive = false;
    public static AdvancedPortal[] Portals = new AdvancedPortal[0];
    private static AdvancedPortalsPlugin plugin;
    public static ConfigAccessor portalData = new ConfigAccessor(plugin, "portals.yml");
    private static boolean ShowBungeeMessage;

    public Portal(AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        ShowBungeeMessage = config.getConfig().getBoolean("ShowBungeeWarpMessage");

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
            Portals = new AdvancedPortal[PortalSet.toArray().length];

			/*for(int i = 0; i <= PortalSet.toArray().length - 1; i++){
                Portals[i] = new AdvancedPortal();
			}*/

            int portalId = 0;
            for (Object portal : PortalSet.toArray()) {

                ConfigurationSection portalConfigSection = portalData.getConfig().getConfigurationSection(portal.toString());

                Material blockType = Material.PORTAL;
                String BlockID = portalConfigSection.getString("triggerblock");

                try {
                    Integer.parseInt(BlockID);
                    System.out.println("Block names must be given not IDs");
                } catch (NumberFormatException e) {
                    blockType = Material.getMaterial(BlockID);
                }

                if (blockType == null) {
                    blockType = Material.PORTAL;
                }

                ConfigurationSection portalArgsConf = portalConfigSection.getConfigurationSection("portalArgs");

                ArrayList<PortalArg> extraData = new ArrayList<>();

                if (portalArgsConf != null) {
                    Set<String> argsSet = portalArgsConf.getKeys(true);

                    for (Object argName : argsSet.toArray()) {
                        if (portalArgsConf.isString(argName.toString())) {
                            extraData.add(new PortalArg(argName.toString(), portalArgsConf.getString(argName.toString())));
                        }
                    }
                }


                String worldName = portalData.getConfig().getString(portal.toString() + ".world");

                World world = Bukkit.getWorld(worldName);
                Location pos1 = new Location(world, portalData.getConfig().getInt(portal.toString() + ".pos1.X"), portalData.getConfig().getInt(portal.toString() + ".pos1.Y"), portalData.getConfig().getInt(portal.toString() + ".pos1.Z"));
                Location pos2 = new Location(world, portalData.getConfig().getInt(portal.toString() + ".pos2.X"), portalData.getConfig().getInt(portal.toString() + ".pos2.Y"), portalData.getConfig().getInt(portal.toString() + ".pos2.Z"));

                PortalArg[] portalArgs = new PortalArg[extraData.size()];
                extraData.toArray(portalArgs);

                Portals[portalId] = new AdvancedPortal(portal.toString(), blockType, pos1, pos2, worldName, portalArgs);

                Portals[portalId].bungee = portalConfigSection.getString("bungee");

                Portals[portalId].destiation = portalConfigSection.getString("destination");

                portalId++;
            }
            portalsActive = true;
        } else {
            portalsActive = false;
        }

    }

    public static String create(Location pos1, Location pos2, String name, String destination, Material triggerBlock, PortalArg... extraData) {
        return create(pos1, pos2, name, destination, triggerBlock, null, extraData);
    }

    public static String create(Location pos1, Location pos2, String name, String destination, Material triggerBlock, String serverName, PortalArg... portalArgs) {

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

        if (checkPortalOverlap(checkpos1, checkpos2)) {
            plugin.getLogger().log(Level.WARNING, "Portals must not overlap!");
            return "\u00A7cPortal creation error, portals must not overlap!";
        }

        portalData.getConfig().set(name + ".world", pos1.getWorld().getName());

        portalData.getConfig().set(name + ".triggerblock", checkMaterial(triggerBlock));

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

    // make this actually work!
    private static boolean checkPortalOverlap(Location pos1, Location pos2) {

        if (portalsActive) {
            int portalId = 0;
            for (@SuppressWarnings("unused") Object portal : Portal.Portals) {
                if (Portals[portalId].worldName.equals(pos2.getWorld().getName())) { // checks that the cubes arnt overlapping by seeing if all 4 corners are not in side another
                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos1.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos2.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos1.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos1.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos2.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos1.getBlockY(), Portals[portalId].pos2.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos1.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos2.getBlockZ())) {
                        return true;
                    }

                    if (checkOverLapPortal(pos1, pos2, Portals[portalId].pos2.getBlockX(), Portals[portalId].pos2.getBlockY(), Portals[portalId].pos1.getBlockZ())) {
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

    private static String checkMaterial(Material triggerBlock) {
        if (triggerBlock.equals(Material.WATER)) {
            return "STATIONARY_WATER";
        } else if (triggerBlock.equals(Material.LAVA)) {
            return "STATIONARY_LAVA";
        }
        return triggerBlock.toString();
    }

    @SuppressWarnings("deprecation")
    public static String create(Location pos1, Location pos2, String name, String destination, String serverName, PortalArg... extraData) { // add stuff for destination names or coordinates
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");

        Material triggerBlockType;
        String BlockID = config.getConfig().getString("DefaultPortalTriggerBlock");
        try {
            triggerBlockType = Material.getMaterial(Integer.parseInt(BlockID));
        } catch (Exception e) {
            triggerBlockType = Material.getMaterial(BlockID);
        }

        if (triggerBlockType == null) {
            triggerBlockType = Material.PORTAL;
        }

        // TODO add a for loop which scans through the addArgs and adds them to the portalData so that the application can use them

        String result = create(pos1, pos2, name, destination, triggerBlockType, serverName, extraData);

        return result;
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

        // TODO add code to check if people have the portal selected and notify if removed.

        /**Set<String> keys = portalData.getConfig().getKeys(true);
         for(String key: keys){
         if(key.startsWith(name)){
         portalData.getConfig().set(key, null);
         }
         }*/

        /**portalData.getConfig().set(name + ".world", null);
         portalData.getConfig().set(name + ".triggerblock", null);
         portalData.getConfig().set(name + ".destination", null);

         portalData.getConfig().set(name + ".pos1.X", null);
         portalData.getConfig().set(name + ".pos1.Y", null);
         portalData.getConfig().set(name + ".pos1.Z", null);

         portalData.getConfig().set(name + ".pos2.X", null);
         portalData.getConfig().set(name + ".pos2.Y", null);
         portalData.getConfig().set(name + ".pos2.Z", null);

         portalData.getConfig().set(name + ".pos1", null);
         portalData.getConfig().set(name + ".pos2", null);

         portalData.getConfig().set(name, null);*/

        portalData.saveConfig();

        loadPortals();
    }

    public static boolean portalExists(String portalName) {

        String posX = portalData.getConfig().getString(portalName + ".pos1.X");

        return posX != null;
    }

    public static boolean activate(Player player, String portalName) {
        for (AdvancedPortal portal : Portal.Portals) {
            if (portal.portalName.equals(portalName)) return activate(player, portal);
        }
        plugin.getLogger().log(Level.SEVERE, "Portal not found by name of: " + portalName);
        return false;
    }

    public static boolean activate(Player player, AdvancedPortal portal) {

        // add other variables or filter code here, or somehow have a way to register them

        // TODO on load and unload recode the permissions to try to register themselves
        // https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/plugin/PluginManager.html#addPermission(org.bukkit.permissions.Permission)
        // check they havent been registered before too and store a list of ones made by this plugin to remove when portals are unloaded.
        // When a portal is added or removed it reloads all portals(i think) so add code for unloading too.

        String permission = portal.getArg("permission");
		/*if((permission == null || (permission != null && player.hasPermission(permission)) || player.isOp())){*/
        // 3 checks, 1st is if it doesnt need perms. 2nd is if it does do they have it. And third is are they op.
        if (!(permission == null || (permission != null && player.hasPermission(permission)) || player.isOp())) {
            player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You do not have permission to use this portal!");
            return false;
        }

        if (portal.bungee != null) {
            if (ShowBungeeMessage) {
                player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Attempting to warp to \u00A7e" + portal.bungee + "\u00A7a.");
            }
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(portal.bungee);
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
            return false;

        } else {
            boolean showFailMessage = true;

            if (portal.getArg("command.1") != null) {
                showFailMessage = false;
                int commandLine = 1;
                String command = portal.getArg("command." + commandLine);//portalData.getConfig().getString(portal.portalName+ ".portalArgs.command." + commandLine);
                do {
                    // (?i) makes the search case insensitive
                    command = command.replaceAll("@player", player.getName());
                    plugin.getLogger().log(Level.SEVERE, "Portal command: " + command);
                    if (command.startsWith("#")) {
                        command = command.substring(1);
                        plugin.getLogger().log(Level.SEVERE, "Portal command: " + command);
                        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                    } else if (command.startsWith("!")) {
                        command = command.substring(1);
                        boolean wasOp = player.isOp();
                        try {
                            player.setOp(true);
                            player.performCommand(command);
                        } finally {
                            player.setOp(wasOp);
                        }
                    } else if (command.startsWith("^")) {
                        command = command.substring(1);
                        PermissionAttachment permissionAttachment = null;
                        try {
                            permissionAttachment = player.addAttachment(plugin, "*", true);
                            player.performCommand(command);
                        } finally {
                            player.removeAttachment(permissionAttachment);
                        }
                    } else {
                        player.performCommand(command);
                    }
                    command = portal.getArg("command." + ++commandLine);
                } while (command != null);
            }
            //plugin.getLogger().info(portal.portalName + ":" + portal.destiation);
            if (portal.destiation != null) {
                ConfigAccessor configDesti = new ConfigAccessor(plugin, "destinations.yml");
                if (configDesti.getConfig().getString(portal.destiation + ".world") != null) {
                    boolean warped = Destination.warp(player, portal.destiation);
                    return warped;
                } else {
                    player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The destination you are currently attempting to warp to doesnt exist!");
                    plugin.getLogger().log(Level.SEVERE, "The portal '" + portal.portalName + "' has just had a warp "
                            + "attempt and either the data is corrupt or that destination listed doesn't exist!");
                    return false;
                }
            } else {
                if (showFailMessage) {
                    player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal you are trying to use doesn't have a destination!");
                    plugin.getLogger().log(Level.SEVERE, "The portal '" + portal.portalName + "' has just had a warp "
                            + "attempt and either the data is corrupt or portal doesn't exist!");
                }
                return false;
            }

				/*if(configDesti.getConfig().getString(destiName  + ".world") != null){
                    String permission = portalData.getConfig().getString(portalName + ".portalArgs.permission");
                    if(permission == null || (permission != null && player.hasPermission(permission)) || player.isOp()){
                        boolean warped = Destination.warp(player, destiName);
                        return warped;
                    }
					else{
                        player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You do not have permission to use this portal!");
                        return false;
                    }
				}
				else{
					player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The destination you are currently attempting to warp to doesnt exist!");
					plugin.getLogger().log(Level.SEVERE, "The portal '" + portalName + "' has just had a warp "
							+ "attempt and either the data is corrupt or that destination listed doesn't exist!");
					return false;
				}*/

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
                        config.getConfig().set(key.replace(oldName + ".", newName + "."), config.getConfig().getString(key));
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
                ; //Loops increasing commandLine till 1 is null
            config.getConfig().set(portalName + ".portalArgs.command." + commandLine, portalCommand);
            config.saveConfig();
            loadPortals();
            return true;
        } else {
            return false;
        }
    }
}
