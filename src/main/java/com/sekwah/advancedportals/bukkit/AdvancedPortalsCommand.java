package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.api.events.WarpEvent;
import com.sekwah.advancedportals.bukkit.api.portaldata.PortalArg;
import com.sekwah.advancedportals.bukkit.listeners.Listeners;
import com.sekwah.advancedportals.bukkit.portals.AdvancedPortal;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;
import java.util.stream.Collectors;

public class AdvancedPortalsCommand implements CommandExecutor, TabCompleter {

    private final List<String> blockMaterialList;
    private AdvancedPortalsPlugin plugin;

    private int portalArgsStringLength = 0;

    private HashSet<String> ignoreExtras = new HashSet<>(Arrays.asList("command.1", "permission"));

    // TODO recode the portal args to be put into a hashmap and use a string array
    // to store all possible portal arguments. Makes code shorter and possibly more
    // efficient.
    // private HashMap<String, String> portalArgs = new HashMap<>();

    public AdvancedPortalsCommand(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        this.blockMaterialList = Arrays.stream(Material.values()).filter(Material::isBlock).map(Enum::name)
                .collect(Collectors.toList());

        plugin.getCommand("advancedportals").setExecutor(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        // System.out.printf("%s %s %s %s%n", sender, cmd, command, args );
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        ConfigAccessor portalConfig = new ConfigAccessor(plugin, "portals.yml");
        if (!(sender instanceof Player)) {
            sender.sendMessage(PluginMessages.customPrefixFail + " You cannot use commands with the console.");
            return true;
        }
        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();

        if (sender.hasPermission("advancedportals.portal")) {
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                case "warp":
                    if (args.length == 2 && player.hasPermission("advancedportals.portal.warp")) {
                        for (AdvancedPortal portal : Portal.portals) {
                            if (args[1].equalsIgnoreCase(portal.getName())) {

                                if (portal.inPortal.contains(player.getUniqueId()))
                                    return true;
                                WarpEvent warpEvent = new WarpEvent(player, portal);
                                plugin.getServer().getPluginManager().callEvent(warpEvent);

                                if (!warpEvent.isCancelled())
                                    Portal.activate(player, portal);
                                break;
                            }
                        }
                    }
                    sendMenu(player, "Help Menu: Warp",
                            "\u00A76/" + command + " warp <name> \u00A7a- teleport to warp name");
                    break;
                case "wand":
                case "selector":
                    String ItemID = config.getConfig().getString("AxeItemId");

                    Material WandMaterial = Material.getMaterial(ItemID);

                    if (WandMaterial == null) {
                        WandMaterial = Material.IRON_AXE;
                    }

                    ItemStack regionselector = new ItemStack(WandMaterial);
                    ItemMeta selectorname = regionselector.getItemMeta();
                    selectorname.setDisplayName("\u00A7ePortal Region Selector");
                    selectorname.setLore(Arrays.asList("\u00A7rThis wand with has the power to help",
                            "\u00A7r create portals bestowed upon it!"));
                    regionselector.setItemMeta(selectorname);

                    inventory.addItem(regionselector);
                    sender.sendMessage(PluginMessages.customPrefix
                            + " You have been given a \u00A7ePortal Region Selector\u00A7a!");
                    break;
                case "portalblock":
                    // TODO change this to a purple wool block and give it a name with a color. Then
                    // listen to when its placed.
                    // Also do this for other blocks such as gateways and end portals just in case
                    // they want it.
                    ItemStack portalBlock = new Wool(DyeColor.PURPLE).toItemStack(1);
                    ItemMeta blockName = portalBlock.getItemMeta();
                    blockName.setDisplayName("\u00A75Portal Block Placer");
                    blockName.setLore(Arrays.asList("\u00A7rThis wool is made of a magical substance",
                            "\u00A7rRight Click: Place portal block", "\u00A7rLeft Click: Rotate portal block"));
                    portalBlock.setItemMeta(blockName);
                    inventory.addItem(portalBlock);

                    sender.sendMessage(
                            PluginMessages.customPrefix + " You have been given a \u00A7ePortal Block\u00A7a!");
                    break;
                case "endportalblock":
                    // TODO change this to a purple wool block and give it a name with a color. Then
                    // listen to when its placed.
                    // Also do this for other blocks such as gateways and end portals just in case
                    // they want it.
                    portalBlock = new Wool(DyeColor.BLACK).toItemStack(1);
                    blockName = portalBlock.getItemMeta();
                    blockName.setDisplayName("\u00A78End Portal Block Placer");
                    blockName.setLore(Arrays.asList("\u00A7rThis wool is made of a magical substance",
                            "\u00A7rRight Click: Place portal block"));
                    portalBlock.setItemMeta(blockName);
                    inventory.addItem(portalBlock);
                    sender.sendMessage(
                            PluginMessages.customPrefix + " You have been given a \u00A7ePortal Block\u00A7a!");
                    break;
                case "gatewayblock":
                    // TODO change this to a purple wool block and give it a name with a color. Then
                    // listen to when its placed.
                    // Also do this for other blocks such as gateways and end portals just in case
                    // they want it.
                    portalBlock = new Wool(DyeColor.BLACK).toItemStack(1);
                    blockName = portalBlock.getItemMeta();
                    blockName.setDisplayName("\u00A78Gateway Block Placer");
                    blockName.setLore(Arrays.asList("\u00A7rThis wool is made of a magical substance",
                            "\u00A7rRight Click: Place portal block"));
                    portalBlock.setItemMeta(blockName);
                    inventory.addItem(portalBlock);

                    sender.sendMessage(
                            PluginMessages.customPrefix + " You have been given a \u00A7ePortal Block\u00A7a!");
                    break;
                case "create":
                    if (player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")) {
                        if (player.getMetadata("Pos1World").get(0).asString()
                                .equals(player.getMetadata("Pos2World").get(0).asString())
                                && player.getMetadata("Pos1World").get(0).asString()
                                        .equals(player.getLocation().getWorld().getName())) {
                            if (args.length >= 2) { // may make this next piece of code more efficient, maybe check
                                                    // against a list of available variables or something
                                // TODO change system to use arrays and hashmaps
                                boolean hasName = false;
                                boolean hasTriggerBlock = false;
                                boolean hasDestination = false;
                                boolean isBungeePortal = false;
                                boolean needsPermission = false;
                                boolean executesCommand = false;
                                String destination = null;
                                String portalName = null;
                                String triggerBlock = null;
                                String serverName = null;
                                String permission = null;
                                String portalCommand = null;

                                ArrayList<PortalArg> extraData = new ArrayList<>();

                                // Is completely changed in the recode but for now im leaving it as this
                                // horrible mess...
                                for (int i = 1; i < args.length; i++) {
                                    if (startsWithPortalArg("name:", args[i])) {
                                        portalName = args[i].replaceFirst("name:", "");
                                        if (portalName.equals("")) {
                                            player.sendMessage(PluginMessages.customPrefixFail
                                                    + " You must include a name for the portal that isnt nothing!");
                                            return true;
                                        }
                                        hasName = true;
                                        portalName = args[i].replaceFirst("name:", "");
                                    } else if (startsWithPortalArg("destination:", args[i])) {
                                        hasDestination = true;
                                        destination = args[i].toLowerCase().replaceFirst("destination:", "");
                                    } else if (startsWithPortalArg("desti:", args[i])) {
                                        hasDestination = true;
                                        destination = args[i].toLowerCase().replaceFirst("desti:", "");
                                    } else if (startsWithPortalArg("triggerblock:", args[i])) {
                                        hasTriggerBlock = true;
                                        triggerBlock = args[i].toLowerCase().replaceFirst("triggerblock:", "");
                                    } else if (startsWithPortalArg("triggerblock:", args[i])) {
                                        hasTriggerBlock = true;
                                        triggerBlock = args[i].toLowerCase().replaceFirst("triggerblock:", "");
                                    } else if (this.startsWithPortalArg("bungee:", args[i])) {
                                        isBungeePortal = true;
                                        serverName = args[i].substring("bungee:".length());
                                    } else if (startsWithPortalArg("permission:", args[i])) {
                                        needsPermission = true;
                                        permission = args[i].toLowerCase().replaceFirst("permission:", "");
                                        extraData.add(new PortalArg("permission", permission));
                                    } else if (startsWithPortalArg("delayed:", args[i])) {
                                        boolean delayed = Boolean
                                                .parseBoolean(args[i].toLowerCase().replaceFirst("delayed:", ""));
                                        extraData.add(new PortalArg("delayed", Boolean.toString(delayed)));
                                    } else if (startsWithPortalArg("message:", args[i])) {
                                        String message = parseArgVariable(args, i, "message:");
                                        if (message == null) {
                                            player.sendMessage(
                                                    PluginMessages.customPrefixFail + " Message quotes not closed!");
                                            return true;
                                        }
                                        extraData.add(new PortalArg("message", message));
                                    } else if (startsWithPortalArg("command:", args[i])) {
                                        executesCommand = true;
                                        portalCommand = parseArgVariable(args, i, "command:");
                                        if (portalCommand == null) {
                                            player.sendMessage(
                                                    PluginMessages.customPrefixFail + " Command quotes not closed!");
                                            return true;
                                        }
                                        i += this.portalArgsStringLength - 1;
                                        if (portalCommand.startsWith("#")
                                                && !(this.plugin.getSettings().hasCommandLevel("c")
                                                        && (sender.hasPermission(
                                                                "advancedportals.createportal.commandlevel.console"))
                                                        || (this.plugin.getSettings().hasCommandLevel("k")
                                                                && sender.isOp()))) {
                                            player.sendMessage(PluginMessages.customPrefixFail
                                                    + " You need permission to make a console command portal!");
                                            return true;
                                        } else if (portalCommand.startsWith("!")
                                                && !(this.plugin.getSettings().hasCommandLevel("o")
                                                        && (sender.hasPermission(
                                                                "advancedportals.createportal.commandlevel.op"))
                                                        || (this.plugin.getSettings().hasCommandLevel("p")
                                                                && sender.isOp()))) {
                                            player.sendMessage(PluginMessages.customPrefixFail
                                                    + " You need permission to make a op command portal!");
                                            return true;
                                        } else if (portalCommand.startsWith("^")
                                                && !(this.plugin.getSettings().hasCommandLevel("p")
                                                        && (sender.hasPermission(
                                                                "advancedportals.createportal.commandlevel.perms"))
                                                        || (this.plugin.getSettings().hasCommandLevel("e")
                                                                && sender.isOp()))) {
                                            player.sendMessage(PluginMessages.customPrefixFail
                                                    + " You need permission to make a all perms command portal!");
                                            return true;
                                        }
                                        extraData.add(new PortalArg("command.1", portalCommand));
                                    } else if (startsWithPortalArg("cooldowndelay:", args[i])) {
                                        String cooldownDelay = parseArgVariable(args, i, "cooldowndelay:");
                                        extraData.add(new PortalArg("cooldowndelay", cooldownDelay));
                                    }
                                }
                                if (!hasName) {
                                    player.sendMessage(PluginMessages.customPrefixFail
                                            + " You must include a name for the portal that you are creating in the variables!");
                                    return true;
                                }

                                World world = org.bukkit.Bukkit
                                        .getWorld(player.getMetadata("Pos1World").get(0).asString());
                                Location pos1 = new Location(world, player.getMetadata("Pos1X").get(0).asInt(),
                                        player.getMetadata("Pos1Y").get(0).asInt(),
                                        player.getMetadata("Pos1Z").get(0).asInt());
                                Location pos2 = new Location(world, player.getMetadata("Pos2X").get(0).asInt(),
                                        player.getMetadata("Pos2Y").get(0).asInt(),
                                        player.getMetadata("Pos2Z").get(0).asInt());

                                ConfigAccessor desticonfig = new ConfigAccessor(plugin, "destinations.yml");
                                String destiPosX = desticonfig.getConfig().getString(destination + ".pos.X");

                                if (!Portal.portalExists(portalName)) {

                                    player.sendMessage("");
                                    player.sendMessage(PluginMessages.customPrefix
                                            + "\u00A7e You have created a new portal with the following details:");
                                    player.sendMessage("\u00A7aname: \u00A7e" + portalName);
                                    if (hasDestination) {
                                        if (!isBungeePortal && destiPosX == null) {
                                            player.sendMessage("\u00A7cdestination: \u00A7e" + destination
                                                    + " (destination does not exist)");
                                            return true;
                                        } else {
                                            player.sendMessage("\u00A7adestination: \u00A7e" + destination);
                                        }

                                    } else {
                                        player.sendMessage(
                                                "\u00A7cdestination: \u00A7eN/A (will not teleport to a location)");
                                    }

                                    if (isBungeePortal) {
                                        player.sendMessage("\u00A7abungee: \u00A7e" + serverName);
                                    }

                                    if (needsPermission) {
                                        player.sendMessage("\u00A7apermission: \u00A7e" + permission);
                                    } else {
                                        player.sendMessage("\u00A7apermission: \u00A7e(none needed)");
                                    }

                                    for (PortalArg portalArg : extraData) {
                                        if (!ignoreExtras.contains(portalArg.argName)) {
                                            player.sendMessage(
                                                    "\u00A7a" + portalArg.argName + ": \u00A7e" + portalArg.value);
                                        }
                                    }

                                    if (executesCommand) {
                                        player.sendMessage("\u00A7acommand: \u00A7e" + portalCommand);
                                    }

                                    if (hasTriggerBlock) {
                                        Set<Material> materialSet = Portal
                                                .getMaterialSet(triggerBlock.toUpperCase().split(","));
                                        if (materialSet.size() != 0) {
                                            player.sendMessage(
                                                    "\u00A7atriggerBlock: \u00A7e" + triggerBlock.toUpperCase());
                                            PortalArg[] portalArgs = new PortalArg[extraData.size()];
                                            portalArgs = extraData.toArray(portalArgs);
                                            player.sendMessage(Portal.create(pos1, pos2, portalName, destination,
                                                    materialSet, serverName, portalArgs));
                                        } else {
                                            ConfigAccessor Config = new ConfigAccessor(plugin, "config.yml");
                                            player.sendMessage("\u00A7ctriggerBlock: \u00A7edefault("
                                                    + Config.getConfig().getString("DefaultPortalTriggerBlock") + ")");

                                            player.sendMessage("\u00A7c" + triggerBlock.toUpperCase()
                                                    + " no valid blocks were listed so the default has been set.");
                                            PortalArg[] portalArgs = new PortalArg[extraData.size()];
                                            portalArgs = extraData.toArray(portalArgs);
                                            player.sendMessage(Portal.create(pos1, pos2, portalName, destination,
                                                    serverName, portalArgs));
                                        }
                                    } else {
                                        ConfigAccessor Config = new ConfigAccessor(plugin, "config.yml");
                                        player.sendMessage("\u00A7atriggerBlock: \u00A7edefault("
                                                + Config.getConfig().getString("DefaultPortalTriggerBlock") + ")");
                                        PortalArg[] portalArgs = new PortalArg[extraData.size()];
                                        portalArgs = extraData.toArray(portalArgs);
                                        player.sendMessage(Portal.create(pos1, pos2, portalName, destination,
                                                serverName, portalArgs));
                                    }
                                } else {
                                    sender.sendMessage(
                                            PluginMessages.customPrefixFail + " A portal by that name already exists!");
                                }

                                // add code to save the portal to the portal config and reload the portals

                                player.sendMessage("");
                            } else {
                                player.sendMessage(PluginMessages.customPrefixFail
                                        + " You need to at least add the name of the portal as a variable, \u00A7cType \u00A7e/portal variables\u00A7c"
                                        + " for a full list of currently available variables and an example command!");
                            }
                        } else {
                            player.sendMessage(PluginMessages.customPrefixFail
                                    + " The points you have selected need to be in the same world!");
                        }
                    } else {
                        player.sendMessage(PluginMessages.customPrefixFail
                                + " You need to have two points selected to make a portal!");
                    }
                    break;
                case "variables":
                    sender.sendMessage(PluginMessages.customPrefix
                            + " \u00A77Variables \u00A7c: \u00A7aname, triggerBlock, desti, destination, bungee, permission, command, cooldowndelay");
                    sender.sendMessage("");
                    sender.sendMessage("\u00A7aExample command: \u00A7e/portal create name:test triggerId:portal");
                    break;
                case "select":
                    // TODO finish the select command and the hit block to replace!
                    if (!player.hasMetadata("selectingPortal")) {
                        if (args.length > 1) {
                            if (Portal.portalExists(args[1])) {
                                player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, args[1]));
                                player.sendMessage(PluginMessages.customPrefix + " You have selected\u00A7e " + args[1]
                                        + "\u00A7a.");
                            } else {
                                player.sendMessage(PluginMessages.customPrefixFail + " No portal by the name \u00A7e"
                                        + args[1]
                                        + "\u00A7c exists (maybe you got the caps wrong)\n Try typing \u00A7e/portal select\u00A7c and hit inside the apropriate portals area!");
                            }
                        } else {
                            player.sendMessage(PluginMessages.customPrefix
                                    + " Hit a block inside the portal region to select the portal!");
                            player.setMetadata("selectingPortal", new FixedMetadataValue(plugin, true));
                        }

                    } else {
                        player.removeMetadata("selectingPortal", plugin);
                        player.sendMessage(PluginMessages.customPrefixFail + " Portal selection cancelled!");
                    }
                    break;
                case "unselect":
                    if (player.getMetadata("selectedPortal").size() != 0) {
                        player.sendMessage(PluginMessages.customPrefix + " You have unselected\u00A7e"
                                + player.getMetadata("selectedPortal").get(0).asString() + "\u00A7a.");
                    } else {
                        player.sendMessage(PluginMessages.customPrefixFail + " You had no portal selected!");
                    }
                case "gui":
                    if (args.length > 1) {
                        if (args[1].toLowerCase().equals("remove") && args.length > 2) {
                            sender.sendMessage("");
                            sender.sendMessage(PluginMessages.customPrefixFail
                                    + " Are you sure you would like to remove the portal \u00A7e" + args[2]
                                    + "\u00A7c?");
                            sender.sendMessage("");
                            plugin.compat.sendRawMessage(
                                    "{\"text\":\"    \",\"extra\":[{\"text\":\"\u00A7e[Yes]\",\"hoverEvent\":{\"action\":\"show_text\","
                                            + "\"value\":\"Confirm removing this portal\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/portal remove "
                                            + args[2] + "\"}}, "
                                            + "{\"text\":\"     \"},{\"text\":\"\u00A7e[No]\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Cancel removing this portal\"}"
                                            + ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/portal edit "
                                            + args[2] + "\"}}]}",
                                    player);
                            sender.sendMessage("");
                        }
                    }
                    break;
                case "edit":
                    if (args.length > 1) {
                        if (Portal.portalExists(args[1])) {
                            player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, args[1]));
                            portalEditMenu(sender, portalConfig, args[1]);
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail + " No portal by the name \u00A7e"
                                    + args[1] + "\u00A7c exists!");
                        }
                    } else {
                        if (player.getMetadata("selectedPortal").size() != 0) {
                            String portalName = player.getMetadata("selectedPortal").get(0).asString();
                            String posX = portalConfig.getConfig().getString(portalName + ".pos1.X");
                            if (posX != null) {
                                portalEditMenu(sender, portalConfig, portalName);
                            } else {
                                sender.sendMessage(PluginMessages.customPrefixFail
                                        + " The portal you had selected no longer seems to exist!");
                                player.removeMetadata("selectedPortal", plugin);
                            }
                        } else {
                            sender.sendMessage(
                                    PluginMessages.customPrefixFail + " No portal has been defined or selected!");
                        }
                    }
                    break;
                case "rename":

                    // not finished yet /
                    if (args.length > 1) {
                        if (player.getMetadata("selectedPortal").size() != 0) {
                            String portalName = player.getMetadata("selectedPortal").get(0).asString();
                            if (portalName.toLowerCase() != args[1].toLowerCase()) {
                                String posX = portalConfig.getConfig().getString(portalName + ".pos1.X");

                                String newPortalPosX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
                                if (posX != null && newPortalPosX == null) {
                                    Portal.rename(portalName, args[1]);
                                    sender.sendMessage(PluginMessages.customPrefix + " The portal \u00A7e" + portalName
                                            + "\u00A7a has been renamed to \u00A7e" + args[1] + "\u00A7a.");
                                    player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, args[1]));
                                } else if (newPortalPosX != null) {
                                    sender.sendMessage(PluginMessages.customPrefixFail
                                            + " There is already a portal with the name \u00A7e" + args[1]
                                            + "\u00A7c!");
                                } else {
                                    sender.sendMessage(PluginMessages.customPrefixFail
                                            + " The portal you had selected no longer seems to exist!");
                                    player.removeMetadata("selectedPortal", plugin);
                                }
                            } else {
                                sender.sendMessage(PluginMessages.customPrefixFail
                                        + " The portal you have selected is already called that!");
                            }
                        } else {
                            sender.sendMessage(
                                    PluginMessages.customPrefixFail + " No portal has been defined or selected!");
                        }
                    } else {
                        sender.sendMessage(PluginMessages.customPrefixFail
                                + " You must select a portal first and then type \u00A7e/portal rename (new name)\u00A7c!");
                    }
                    break;
                case "command":
                    if (player.getMetadata("selectedPortal").size() != 0) {
                        String portalName = player.getMetadata("selectedPortal").get(0).asString();
                        if (args.length > 1) {
                            // TODO add the command autocompletes, add, remove and show
                            if (args[1].toLowerCase().equals("add")) {
                                if (args.length > 2) {
                                    String portalCommand = args[2];
                                    for (int i = 3; i < args.length; i++) {
                                        portalCommand += args[i];
                                    }
                                    if (Portal.addCommand(portalName, portalCommand)) {
                                        sender.sendMessage(
                                                PluginMessages.customPrefixFail + " Command added to portal!");
                                    } else {
                                        sender.sendMessage(
                                                PluginMessages.customPrefixFail + " Failed to add command to portal!");
                                    }

                                } else {
                                    sender.sendMessage(PluginMessages.customPrefixFail
                                            + " You must actually specify a command to execute!");
                                }

                            } else if (args[1].toLowerCase().equals("remove")) {
                                // Specify what line to remove
                            } else if (args[1].toLowerCase().equals("show")) {
                            } else {
                                sender.sendMessage(PluginMessages.customPrefixFail
                                        + " You must specify to \u00A7eadd\u00A7c or \u00A7eremove a command!");
                            }
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail
                                    + " You must specify to \u00A7eadd\u00A7c or \u00A7eremove\u00A7c a command!");
                        }
                    } else {

                    }
                    break;
                case "remove":
                    if (args.length > 1) {
                        String posX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
                        if (posX != null) {
                            Portal.remove(args[1]);
                            sender.sendMessage(PluginMessages.customPrefixFail + " The portal \u00A7e" + args[1]
                                    + "\u00A7c has been removed!");
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail + " No portal by that name exists!");
                        }
                    } else {
                        if (player.getMetadata("selectedPortal").size() != 0) {
                            String portalName = player.getMetadata("selectedPortal").get(0).asString();
                            String posX = portalConfig.getConfig().getString(portalName + ".pos1.X");
                            if (posX != null) {
                                Portal.remove(portalName);
                                sender.sendMessage(PluginMessages.customPrefixFail + " The portal \u00A77" + portalName
                                        + "\u00A7c has been removed!");
                            } else {
                                sender.sendMessage(PluginMessages.customPrefixFail
                                        + " The portal you had selected no longer seems to exist!");
                                player.removeMetadata("selectedPortal", plugin);
                            }
                        } else {
                            sender.sendMessage(
                                    PluginMessages.customPrefixFail + " No portal has been defined or selected!");
                        }
                    }
                    break;
                case "help":
                    helpCommand(sender, command, args);
                    break;
                case "show":
                    if (args.length > 1) {
                        String posX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
                        if (posX != null) {
                            Selection.show(player, this.plugin, args[1]);
                        } else {
                            sender.sendMessage(PluginMessages.customPrefixFail + " No portal by that name exists!");
                        }
                    } else {
                        if (player.getMetadata("selectedPortal").size() != 0) {
                            player.sendMessage(PluginMessages.customPrefix
                                    + " Your currently selected portal has been shown, it will dissapear shortly!");
                            Selection.show(player, this.plugin, player.getMetadata("selectedPortal").get(0).asString());
                        } else if (player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")) {
                            if (player.getMetadata("Pos1World").get(0).asString()
                                    .equals(player.getMetadata("Pos2World").get(0).asString())
                                    && player.getMetadata("Pos1World").get(0).asString()
                                            .equals(player.getLocation().getWorld().getName())) {
                                player.sendMessage(PluginMessages.customPrefix
                                        + " Your currently selected area has been shown, it will dissapear shortly!");
                                Selection.show(player, this.plugin, null);
                            }
                        } else {
                            player.sendMessage(PluginMessages.customPrefixFail + " No regions selected!");
                        }
                    }
                    break;
                case "reload":
                    sender.sendMessage(PluginMessages.customPrefix + " Reloaded values!");
                    Listeners.reloadValues(plugin);
                    Portal.loadPortals();
                    break;
                case "list":
                    String message = PluginMessages.customPrefix + " \u00A77Portals \u00A7c:\u00A7a";
                    LinkedList<String> portals = new LinkedList<>();
                    for (AdvancedPortal portal : Portal.portals) {
                        portals.add(portal.getName());
                    }
                    Collections.sort(portals);
                    for (Object portalName : portals.toArray()) {
                        message = message + " " + portalName;
                    }
                    player.sendMessage(message);
                    break;
                default:
                    PluginMessages.UnknownCommand(sender, command);
                }
            } else {
                PluginMessages.UnknownCommand(sender, command);
            }
        } else {
            PluginMessages.NoPermission(sender, command);
        }

        return true;
    }

    private boolean startsWithPortalArg(String portalArg, String arg) {
        return arg.toLowerCase().startsWith(portalArg) && arg.length() > portalArg.length();
    }

    private void helpCommand(CommandSender sender, String command, String[] args) {
        // Add pages if there starts to become too many
        if (args.length == 1) {
            sendMenu(sender, "Help Menu", "\u00A76/" + command + " selector \u00A7a- gives you a region selector",
                    "\u00A76/" + command + " create \u00A7c[tags] \u00A7a- creates a portal with a selection ",
                    "\u00A76/" + command + " portalblock \u00A7a- gives you a portal block",
                    "\u00A76/" + command + " endportalblock \u00A7a- gives you an end portal block",
                    "\u00A76/" + command + " gatewayblock \u00A7a- gives you a gateway block",
                    "\u00A76/" + command + " select \u00A7a- selects an existing portal",
                    "\u00A76/" + command + " unselect \u00A7a- unselects the current portal",
                    "\u00A76/" + command + " command \u00A7a- adds or removes commands from the selected portal",
                    "\u00A76/" + command + " help \u00A7a- shows this help section",
                    "\u00A76/" + command + " help <arg> \u00A7a- shows help for the specified arg",
                    "\u00A76/" + command + " remove \u00A7a- removes a portal",
                    "\u00A76/" + command + " list \u00A7a- lists all the current portals",
                    "\u00A76/" + command + " warp <name> \u00A7a- teleport to warp name",
                    "\u00A76/" + command + " variables \u00A7a- lists all available tags");
        } else if (args.length > 1) {
            if (args[1].toLowerCase().equals("help")) {
                sendMenu(sender, "Help Command",
                        "Shows the help section. You can also use a single argument after it to show the "
                                + "help section for the corresponding command.");
            } else if (args[1].toLowerCase().equals("portalblock")) {
                sendMenu(sender, "Help Command", "Gives you a special wool block to place portal blocks.", "",
                        "\u00A7eLeft Click: \u00A76Rotates the hit portal block",
                        "\u00A7eRight Click: \u00A76Placed a portal block");
            } else if (args[1].toLowerCase().equals("endportalblock")) {
                sendMenu(sender, "Help Command", "Gives you a special wool block to place end portal blocks.", "",
                        "\u00A7eRight Click: \u00A76Placed a end portal block");
            } else if (args[1].toLowerCase().equals("gatewayblock")) {
                sendMenu(sender, "Help Command", "Gives you a special wool block to place gateway blocks.", "",
                        "\u00A7eRight Click: \u00A76Placed a gateway block");
            } else {
                sender.sendMessage(PluginMessages.customPrefix + " Either \u00A7e" + args[1]
                        + "\u00A7a is not a command or a help page has not been added yet.");
            }
        }
    }

    private void sendMenu(CommandSender sender, String title, String... lines) {
        sender.sendMessage(PluginMessages.customPrefix + " " + title);
        sender.sendMessage("\u00A7e\u00A7m-----------------------------------");
        for (String line : lines) {
            sender.sendMessage("\u00A7a" + line);
        }
        sender.sendMessage("\u00A7e\u00A7m-----------------------------------");
    }

    private String parseArgVariable(String[] args, int currentArg, String argStarter) {
        String variableString = args[currentArg].replaceFirst(argStarter, "");
        this.portalArgsStringLength = 1;
        if (variableString.charAt(0) == '"') {
            variableString = variableString.substring(1);
            if (variableString.length() == 0 || variableString.charAt(variableString.length() - 1) != '"') {
                currentArg++;
                for (; currentArg <= args.length; currentArg++) {
                    if (currentArg == args.length) {
                        return null;
                    }
                    variableString += " " + args[currentArg];
                    this.portalArgsStringLength += 1;
                    if (variableString.charAt(variableString.length() - 1) == '"') {
                        variableString = variableString.substring(0, variableString.length() - 1);
                        break;
                    }
                }
            } else {
                variableString = variableString.substring(0, variableString.length() - 1);
            }

        }
        return variableString;
    }

    private void portalEditMenu(CommandSender sender, ConfigAccessor portalConfig, String portalName) {
        // make the text gui with the json message for a list of edit commands to be
        // clicked or hovered
        // put \" for a " in the json messages
        // sadly there is no newline code so these three lines will have to be copied
        // and pasted for each line

        // use the usual messages for normal lines but anything that needs special
        // features make sure you use the
        // chat steriliser
        sender.sendMessage("");
        sender.sendMessage(PluginMessages.customPrefix + " Editing: \u00A7e" + portalName);

        sender.sendMessage(" \u00A7apos1\u00A7e: " + portalConfig.getConfig().getString(portalName + ".pos1.X") + ", "
                + portalConfig.getConfig().getString(portalName + ".pos1.Y") + ", "
                + portalConfig.getConfig().getString(portalName + ".pos1.Z"));
        sender.sendMessage(" \u00A7apos2\u00A7e: " + portalConfig.getConfig().getString(portalName + ".pos2.X") + ", "
                + portalConfig.getConfig().getString(portalName + ".pos2.Y") + ", "
                + portalConfig.getConfig().getString(portalName + ".pos2.Z"));

        String destination = portalConfig.getConfig().getString(portalName + ".destination");
        if (destination != null) {
            sender.sendMessage(" \u00A7adestination\u00A7e: " + destination);
        } else {
            sender.sendMessage(" \u00A7cdestination\u00A7e: null");
        }

        String trigger = portalConfig.getConfig().getString(portalName + ".triggerblock");
        if (trigger != null) {
            sender.sendMessage(" \u00A7atriggerBlock\u00A7e: " + trigger);
        } else {
            sender.sendMessage(" \u00A7ctriggerBlock\u00A7e: null");
        }

        if (portalConfig.getConfig().getString(portalName + ".portalArgs.command.1") != null) {
            int commands = 0;
            String command = portalConfig.getConfig().getString(portalName + ".portalArgs.command.1");
            // TODO possibly change code so it counds number of subvalues rather than a
            // loop.
            while (command != null) {
                command = portalConfig.getConfig().getString(portalName + ".portalArgs.command." + ++commands);
            }
            if (--commands > 1) {
                sender.sendMessage(" \u00A7acommands\u00A7e: " + commands + " commands");
            } else {
                sender.sendMessage(" \u00A7acommands\u00A7e: " + commands + " command");
            }
        } else {
            sender.sendMessage(" \u00A7ccommands\u00A7e: none");
        }
        sender.sendMessage("");

        Player player = (Player) sender;

        plugin.compat.sendRawMessage("{\"text\":\"\u00A7aFunctions\u00A7e: \","
                + "\"extra\":[{\"text\":\"\u00A7eRemove\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Remove the selected portal\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/portal gui remove "
                + portalName + "\"}}"
                + ",{\"text\":\"  \"},{\"text\":\"\u00A7eShow\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Show the selected portal\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/portal show "
                + portalName + "\"}}"
                + ",{\"text\":\"  \"},{\"text\":\"\u00A7eRename\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Change the name of the portal\"},\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/portal rename \"}}"
                + ",{\"text\":\"  \"},{\"text\":\"\u00A7eTeleport\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Teleport to the set destination\n(If there is one)\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/desti warp "
                + destination + "\"}}]}", player);

        sender.sendMessage("");

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
        LinkedList<String> autoComplete = new LinkedList<String>();
        if (sender.hasPermission("advancedportals.createportal")) {
            if (args.length == 1 || (args.length == 2 && args[0].toLowerCase().equals("help"))) {
                autoComplete.addAll(Arrays.asList("create", "list", "portalblock", "select", "unselect", "command",
                        "selector", "show", "gatewayblock", "endportalblock", "variables", "wand", "remove", "rename",
                        "help", "bukkitpage", "helppage", "warp"));
            } else if (args[0].toLowerCase().equals("create")) {

                boolean hasName = false;
                boolean hasTriggerBlock = false;
                boolean hasDestination = false;
                boolean hasDelay = false;
                boolean isBungeePortal = false;
                boolean needsPermission = false;
                boolean hasCommand = false;
                boolean hasCooldownDelay = false;

                // TODO change auto complete when quotes are opened and closed. Such as
                // autocomplete @Player and stuff when specifying commands

                for (int i = 1; i < args.length; i++) {
                    String argTag = getTag(args[i].toLowerCase());
                    if (argTag.length() + 1 < args[i].length()) {
                        switch (argTag) {
                        case "name":
                            hasName = true;
                            break;
                        case "destination":
                            hasDestination = true;
                            break;
                        case "desti":
                            hasDestination = true;
                            break;
                        case "triggerblock":
                            hasTriggerBlock = true;
                            break;
                        case "bungee":
                            isBungeePortal = true;
                            break;
                        case "permission":
                            needsPermission = true;
                            break;
                        case "delayed":
                            hasDelay = true;
                            break;
                        case "command":
                            hasCommand = true;
                            break;
                        case "cooldowndelay":
                            hasCooldownDelay = true;
                            break;
                        }
                    }

                }

                if (!hasName) {
                    autoComplete.add("name:");
                }
                if (!hasTriggerBlock) {
                    autoComplete.add("triggerblock:");
                }
                if (!hasDestination) {
                    autoComplete.add("destination:");
                    autoComplete.add("desti:");
                }
                if (!isBungeePortal) {
                    autoComplete.add("bungee:");
                }
                if (!needsPermission) {
                    autoComplete.add("permission:");
                }
                if (!hasDelay) {
                    autoComplete.add("delayed:");
                }
                if (!hasCommand) {
                    autoComplete.add("command:");
                }
                if (!hasCooldownDelay) {
                    autoComplete.add("cooldowndelay:");
                }
            }
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("warp")) {
            for (AdvancedPortal portal : Portal.portals) {
                String perm = portal.getArg("permission");
                if (perm == null || sender.hasPermission(perm)) {
                    autoComplete.add(portal.getName());
                }
            }
        }
        String triggerBlock = "triggerblock:";
        if (args[args.length - 1].toLowerCase().startsWith(triggerBlock)) {
            String currentArg = args[args.length - 1];
            int length = currentArg.lastIndexOf(',');
            String startString;
            if (triggerBlock.length() > length) {
                startString = triggerBlock;
            } else {
                startString = currentArg.substring(0, length + 1);
            }
            autoComplete
                    .addAll(blockMaterialList.stream().map(value -> startString + value).collect(Collectors.toList()));
        }
        if (args[args.length - 1].startsWith("delayed:")) {
            autoComplete.addAll(Arrays.asList("delayed:true", "delayed:false"));
        }
        if (args[args.length - 1].startsWith("desti:") || args[args.length - 1].startsWith("destination:")) {
            String tagStart = args[args.length - 1].startsWith("desti:") ? "desti:" : "destination:";
            ConfigAccessor destiConfig = new ConfigAccessor(plugin, "destinations.yml");
            List<Object> destiObj = Arrays.asList(destiConfig.getConfig().getKeys(false).toArray());
            for (Object object : destiObj) {
                autoComplete.add(tagStart + object.toString());
            }
        }
        Collections.sort(autoComplete);
        for (Object result : autoComplete.toArray()) {
            if (!result.toString().startsWith(args[args.length - 1])) {
                autoComplete.remove(result);
            }
        }
        return autoComplete;
    }

    private String getTag(String arg) {
        int indexOfSplitter = arg.indexOf(':');
        if (indexOfSplitter > 0) {
            return arg.substring(0, indexOfSplitter);
        }
        return "";
    }

}
