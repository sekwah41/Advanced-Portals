package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.api.events.WarpEvent;
import com.sekwah.advancedportals.bukkit.api.portaldata.PortalArg;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.listeners.Listeners;
import com.sekwah.advancedportals.bukkit.portals.AdvancedPortal;
import com.sekwah.advancedportals.bukkit.portals.Portal;
import com.sekwah.advancedportals.bukkit.util.WorldEditIntegration;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
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
    private final AdvancedPortalsPlugin plugin;

    private int portalArgsStringLength = 0;

    private final HashSet<String> ignoreExtras = new HashSet<>(Arrays.asList("command.1", "permission"));

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
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        ConfigAccessor portalConfig = new ConfigAccessor(plugin, "portals.yml");
        if (!(sender instanceof Player)) {
            sender.sendMessage(PluginMessages.customPrefixFail + " You cannot use commands with the console.");
            return true;
        }
        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();

        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("warp") && player.hasPermission("advancedportals.warp")) {
                if (args.length == 2 && (player.hasPermission("advancedportals.warp.*")
                        || player.hasPermission("advancedportals.warp." + args[1]))) {
                    AdvancedPortal portal = Portal.getPortal(args[1]);

                    if(portal == null) {
                        sender.sendMessage(PluginMessages.customPrefixFail
                                + " Could not find a portal with that name");
                    }
                    else {
                        if (portal.inPortal.contains(player.getUniqueId()))
                            return true;
                        WarpEvent warpEvent = new WarpEvent(player, portal);
                        plugin.getServer().getPluginManager().callEvent(warpEvent);

                        if (!warpEvent.isCancelled()) {
                            Portal.activate(player, portal, false);
                            return true;
                        }
                    }
                } else if (args.length == 1 && player.hasPermission("advancedportals.warp")) {
                    sendMenu(player, "Help Menu: Warp",
                            "\u00A76/" + command + " warp <name> \u00A7a- teleport to warp name");
                }
                else {
                    sender.sendMessage(PluginMessages.customPrefixFail
                            + " You do not have permission to perform that command");
                }
                return true;
            }

        }

        if (sender.hasPermission("advancedportals.portal")) {
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "disablebeacon":
                        if (player.hasPermission("advancedportals.build")) {
                            if(args.length == 1) {
                                sender.sendMessage(PluginMessages.customPrefixFail
                                        + " You need to specify a portal to replace the blocks.");
                            }
                            else {
                                AdvancedPortal portal = Portal.getPortal(args[1]);

                                if(portal == null) {
                                    sender.sendMessage(PluginMessages.customPrefixFail
                                            + " Could not find a portal with that name");
                                }
                                else {
                                    sender.sendMessage(PluginMessages.customPrefix
                                            + " Replacing any found beacon blocks.");
                                    disableBeacons(portal);
                                }
                            }
                        }
                        break;
                    case "wand":
                    case "selector":
                        if (plugin.isWorldEditActive()) {
                            sender.sendMessage(PluginMessages.customPrefix
                                    + " Use the WorldEdit wand to select stuff. Checkout //wand.");
                            return true;
                        }
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
                        return createPortalRequest(player, args);
                    case "variables":
                        sender.sendMessage(
                                PluginMessages.customPrefix +
                                        " \u00A77Variables \u00A7c: " +
                                        "\u00A7aname, triggerBlock, desti, destination, " +
                                        "bungee, permission, command, cooldowndelay, leavedesti" +
                                        "particlein, particleout"
                        );
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
                        // /portal gui remove testarg
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("remove") && args.length > 2) {
                                sender.sendMessage("");
                                sender.sendMessage(PluginMessages.customPrefixFail
                                        + " Are you sure you would like to remove the portal \u00A7e" + args[2]
                                        + "\u00A7c?");

                                TextComponent removeMessage = new TextComponent();
                                TextComponent yes = new TextComponent("[Yes]");
                                yes.setColor(ChatColor.YELLOW);
                                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/portal remove " + args[2]));
                                yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Confirm removing this portal").create()));
                                TextComponent no = new TextComponent("[No]");
                                no.setColor(ChatColor.YELLOW);
                                no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/portal edit " + args[2]));
                                no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cancel removing this portal").create()));

                                removeMessage.addExtra("    ");
                                removeMessage.addExtra(yes);
                                removeMessage.addExtra("     ");
                                removeMessage.addExtra(no);

                                sender.sendMessage("");
                                sender.spigot().sendMessage(removeMessage);
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
                                if (args[1].equalsIgnoreCase("add")) {
                                    if (args.length > 2) {
                                        StringBuilder portalCommand = new StringBuilder(args[2]);
                                        for (int i = 3; i < args.length; i++) {
                                            portalCommand.append(" ").append(args[i]);
                                        }
                                        if (Portal.addCommand(portalName, portalCommand.toString())) {
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

                                } else if (args[1].equalsIgnoreCase("remove")) {
                                    // Specify what line to remove
                                } else if (args[1].equalsIgnoreCase("show")) {
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
                    case "bukkitpage":
                        sender.sendMessage(
                                PluginMessages.customPrefix + " Bukkit Page: http://dev.bukkit.org/bukkit-plugins/advanced-portals/");
                        break;
                    case "helppage":
                        sender.sendMessage(
                                PluginMessages.customPrefix + " Help Page: https://www.guilded.gg/Sekwah/groups/MDqAZyrD/channels/72ffdaa3-9273-4722-bf47-b75408b371af/docs/1090356006");
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
                    case "we-selection":
                        if (!Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
                            player.sendMessage(PluginMessages.customPrefixFail + " WorldEdit is not enabled.");
                            return true;
                        }
                        if (args.length <= 1) {
                            player.sendMessage(PluginMessages.customPrefixFail + " Specify a portal name!");
                            return true;
                        }
                        AdvancedPortal wePortal = Portal.getPortal(args[1]);
                        if (wePortal == null) {
                            sender.sendMessage(PluginMessages.customPrefixFail + " No portal by that name exists!");
                            return true;
                        }
                        WorldEditIntegration.explainRegion(player, wePortal.getPos1(), wePortal.getPos2());
                        player.sendMessage(PluginMessages.customPrefix
                                + " The portal has been selected with worldedit!");
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

    private void disableBeacons(AdvancedPortal portal) {
        Location loc1 = portal.getPos1();
        Location loc2 =  portal.getPos2();

        Location scanner = loc1.clone();

        for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
            scanner.setZ(x);
            for (int y = loc2.getBlockY(); y <= loc1.getBlockY(); y++) {
                scanner.setZ(y);
                for (int z = loc2.getBlockZ(); z <= loc1.getBlockZ(); z++) {
                    scanner.setZ(z);
                    Block block = scanner.getBlock();
                    if(block.getType() == Material.END_GATEWAY) {
                        EndGateway tileState = (EndGateway) block.getState();
                        tileState.setAge(Long.MIN_VALUE);
                        tileState.update();
                    }
                }
            }
        }
    }

    private boolean checkValidSelection(Player player) {
        if (plugin.isWorldEditActive()) {
            if (!WorldEditIntegration.validateSelection(player)) {
                player.sendMessage(PluginMessages.customPrefixFail
                        + " Your WorldEdit selection is invalid!");
                return false;
            }
        } else {
            if (!player.hasMetadata("Pos1World") || !player.hasMetadata("Pos2World")) {
                player.sendMessage(PluginMessages.customPrefixFail
                        + " You need to have two points selected to make a portal!");
                return false;
            }
            if (!player.getMetadata("Pos1World").get(0).asString()
                    .equals(player.getMetadata("Pos2World").get(0).asString())
                    || !player.getMetadata("Pos1World").get(0).asString()
                    .equals(player.getLocation().getWorld().getName())) {
                player.sendMessage(PluginMessages.customPrefixFail
                        + " The points you have selected need to be in the same world as each other and yourself!");
                return false;
            }
        }
        return true;
    }

    private boolean createPortalRequest(Player player, String[] args) {
        if (!checkValidSelection(player)) {
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(PluginMessages.customPrefixFail
                + " You need to at least add the name of the portal as a variable, \u00A7cType \u00A7e/portal variables\u00A7c"
                + " for a full list of currently available variables and an example command!");
            return true;
        }

        // may make this next piece of code more efficient, maybe check
        // against a list of available variables or something
        // TODO change system to use arrays and hashmaps
        boolean hasName = false;
        boolean hasTriggerBlock = false;
        boolean hasDestination = false;
        boolean isBungeePortal = false;
        boolean needsPermission = false;
        boolean executesCommand = false;
        String[] destinations = new String[] { };
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
            } else if (startsWithPortalArg("destinations:", args[i])) {
                hasDestination = true;
                destinations = args[i].toLowerCase().replaceFirst("destinations:", "").split(",");
            } else if (startsWithPortalArg("destination:", args[i])) {
                hasDestination = true;
                destinations = args[i].toLowerCase().replaceFirst("destination:", "").split(",");
            } else if (startsWithPortalArg("desti:", args[i])) {
                hasDestination = true;
                destinations = args[i].toLowerCase().replaceFirst("desti:", "").split(",");
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
                        && !(this.plugin.getSettings().enabledCommandLevel("c")
                        && (player.hasPermission(
                        "advancedportals.createportal.commandlevel.console")
                        || player.isOp()))) {
                    player.sendMessage(PluginMessages.customPrefixFail
                            + " You need permission to make a console command portal!");
                    return true;
                } else if (portalCommand.startsWith("!")
                        && !(this.plugin.getSettings().enabledCommandLevel("o")
                        && (player.hasPermission(
                        "advancedportals.createportal.commandlevel.op")
                        || player.isOp()))) {
                    player.sendMessage(PluginMessages.customPrefixFail
                            + " You need permission to make a op command portal!");
                    return true;
                } else if (portalCommand.startsWith("%")
                        && !(this.plugin.getSettings().enabledCommandLevel("b")
                        && (player.hasPermission(
                        "advancedportals.createportal.commandlevel.bungee")
                        || player.isOp()))) {
                    player.sendMessage(PluginMessages.customPrefixFail
                            + " You need permission to make a bungee command portal!");
                    return true;
                } else if (portalCommand.startsWith("^")
                        && !(this.plugin.getSettings().enabledCommandLevel("p")
                        && (player.hasPermission(
                        "advancedportals.createportal.commandlevel.perms")
                        || player.isOp()))) {
                    player.sendMessage(PluginMessages.customPrefixFail
                            + " You need permission to make a all perms command portal!");
                    return true;
                }
                extraData.add(new PortalArg("command.1", portalCommand));
            } else if (startsWithPortalArg("cooldowndelay:", args[i])) {
                String cooldownDelay = parseArgVariable(args, i, "cooldowndelay:");
                extraData.add(new PortalArg("cooldowndelay", cooldownDelay));
            } else if (startsWithPortalArg("leavedesti:", args[i])) {
                String leaveDesti = parseArgVariable(args, i, "leavedesti:");
                extraData.add(new PortalArg("leavedesti", leaveDesti));
            } else if (startsWithPortalArg("particlein:", args[i])) {
                String value = parseArgVariable(args, i, "particlein:");
                extraData.add(new PortalArg("particlein", value));
            } else if (startsWithPortalArg("particleout:", args[i])) {
                String value = parseArgVariable(args, i, "particleout:");
                extraData.add(new PortalArg("particleout", value));
            }
        }
        if (!hasName) {
            player.sendMessage(PluginMessages.customPrefixFail
                    + " You must include a name for the portal that you are creating in the variables!");
            return true;
        }

        Location pos1, pos2;
        if (plugin.isWorldEditActive()) {
            pos1 = WorldEditIntegration.getPos1(player);
            pos2 = WorldEditIntegration.getPos2(player);
        } else {
            World world = Bukkit
                .getWorld(player.getMetadata("Pos1World").get(0).asString());
            pos1 = new Location(world, player.getMetadata("Pos1X").get(0).asInt(),
                player.getMetadata("Pos1Y").get(0).asInt(),
                player.getMetadata("Pos1Z").get(0).asInt());
            pos2 = new Location(world, player.getMetadata("Pos2X").get(0).asInt(),
                player.getMetadata("Pos2Y").get(0).asInt(),
                player.getMetadata("Pos2Z").get(0).asInt());
        }

        if (!Portal.portalExists(portalName)) {

            player.sendMessage("");
            player.sendMessage(PluginMessages.customPrefix
                    + "\u00A7e You have created a new portal with the following details:");
            player.sendMessage("\u00A7aname: \u00A7e" + portalName);
            if (hasDestination) {
                ConfigAccessor desticonfig = new ConfigAccessor(plugin, "destinations.yml");
                for (String destination : destinations) {
                    String destiPosX = desticonfig.getConfig().getString(destination + ".pos.X");
                    if (!isBungeePortal && destiPosX == null) {
                        player.sendMessage("\u00A7cdestination: \u00A7e" + destination
                                + " (destination does not exist)");
                        return true;
                    }
                }
                player.sendMessage("\u00A7adestinations: \u00A7e" + String.join(", ", destinations));

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
                    player.sendMessage(Portal.create(pos1, pos2, portalName, destinations,
                            materialSet, serverName, portalArgs));
                    if(materialSet.contains(Material.END_GATEWAY)) {
                        AdvancedPortal portal = Portal.getPortal(portalName);
                        if(portal != null) {
                            disableBeacons(portal);
                        }
                    }
                } else {
                    ConfigAccessor Config = new ConfigAccessor(plugin, "config.yml");
                    player.sendMessage("\u00A7ctriggerBlock: \u00A7edefault("
                            + Config.getConfig().getString("DefaultPortalTriggerBlock") + ")");

                    player.sendMessage("\u00A7c" + triggerBlock.toUpperCase()
                            + " no valid blocks were listed so the default has been set.");
                    PortalArg[] portalArgs = new PortalArg[extraData.size()];
                    portalArgs = extraData.toArray(portalArgs);
                    player.sendMessage(Portal.create(pos1, pos2, portalName, destinations,
                            serverName, portalArgs));
                }
            } else {
                ConfigAccessor Config = new ConfigAccessor(plugin, "config.yml");
                player.sendMessage("\u00A7atriggerBlock: \u00A7edefault("
                        + Config.getConfig().getString("DefaultPortalTriggerBlock") + ")");
                PortalArg[] portalArgs = new PortalArg[extraData.size()];
                portalArgs = extraData.toArray(portalArgs);
                player.sendMessage(Portal.create(pos1, pos2, portalName, destinations,
                        serverName, portalArgs));
            }
        } else {
            player.sendMessage(
                    PluginMessages.customPrefixFail + " A portal by that name already exists!");
        }

        // add code to save the portal to the portal config and reload the portals

        player.sendMessage("");
        return true;
    }


    private boolean startsWithPortalArg(String portalArg, String arg) {
        return arg.toLowerCase().startsWith(portalArg) && arg.length() > portalArg.length();
    }

    private void helpCommand(CommandSender sender, String command, String[] args) {
        // Add pages if there starts to become too many
        if (args.length == 1) {
            sendMenu(sender, "Help Menu",
                    "\u00A76/" + command + " selector/wand \u00A7a- gives you a region selector",
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
            if (args[1].equalsIgnoreCase("help")) {
                sendMenu(sender, "Help Command",
                        "Shows the help section. You can also use a single argument after it to show the "
                                + "help section for the corresponding command.");
            } else if (args[1].equalsIgnoreCase("portalblock")) {
                sendMenu(sender, "Help Command", "Gives you a special wool block to place portal blocks.", "",
                        "\u00A7eLeft Click: \u00A76Rotates the hit portal block",
                        "\u00A7eRight Click: \u00A76Placed a portal block");
            } else if (args[1].equalsIgnoreCase("endportalblock")) {
                sendMenu(sender, "Help Command", "Gives you a special wool block to place end portal blocks.", "",
                        "\u00A7eRight Click: \u00A76Placed a end portal block");
            } else if (args[1].equalsIgnoreCase("gatewayblock")) {
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

        TextComponent removeButton = new TextComponent("Remove");
        removeButton.setColor(ChatColor.YELLOW);
        removeButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Remove the selected portal").create()));
        removeButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/portal gui remove " + portalName));

        TextComponent showButton = new TextComponent("Show");
        showButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Show the selected portal").create()));
        showButton.setColor(ChatColor.YELLOW);
        showButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/portal show " + portalName));

        TextComponent renameButton = new TextComponent("Rename");
        renameButton.setColor(ChatColor.YELLOW);
        renameButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Change the name of the portal").create()));
        renameButton.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/portal rename "));

        TextComponent activateButton = new TextComponent("Activate");
        activateButton.setColor(ChatColor.YELLOW);
        activateButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Trigger it as if you've just walked into it (Minus failing knockback)").create()));
        activateButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/portal warp " + portalName));

        BaseComponent[] editMessage = new ComponentBuilder("Functions").color(ChatColor.GREEN)
                .append(": ").color(ChatColor.YELLOW)
                .append(removeButton).append("  ")
                .append(showButton).append("  ")
                .append(renameButton).append("  ")
                .append(activateButton).append("  ")
                .create();

        sender.spigot().sendMessage(editMessage);

        sender.sendMessage("");

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
        LinkedList<String> autoComplete = new LinkedList<String>();
        if(args.length == 1 && (sender.hasPermission("advancedportals.warp")))  {
            autoComplete.add("warp");
        }
        if (sender.hasPermission("advancedportals.createportal")) {
            if (args.length == 1 || (args.length == 2 && args[0].equalsIgnoreCase("help"))) {
                autoComplete.addAll(Arrays.asList("create", "list", "portalblock", "select", "unselect", "command",
                        "show", "gatewayblock", "endportalblock", "variables", "disablebeacon", "remove", "rename",
                        "help", "bukkitpage", "helppage"));
                if (Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
                    autoComplete.add("we-selection");
                }
                if (!plugin.isWorldEditActive()) {
                    autoComplete.addAll(Arrays.asList("selector", "wand"));
                }
            } else if (args[0].equalsIgnoreCase("create")) {

                boolean hasName = false;
                boolean hasTriggerBlock = false;
                boolean hasDestination = false;
                boolean hasDelay = false;
                boolean isBungeePortal = false;
                boolean needsPermission = false;
                boolean hasCommand = false;
                boolean hasCooldownDelay = false;
                boolean hasLeaveDesti = false;
                boolean hasDispParticle = false;
                boolean hasDestParticle = false;

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
                            case "particlein":
                                hasDispParticle = true;
                                break;
                            case "particleout":
                                hasDestParticle = true;
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
                if (!hasLeaveDesti) {
                    autoComplete.add("leavedesti:");
                }
                if (!hasDispParticle) {
                    autoComplete.add("particlein:");
                }
                if (!hasDestParticle) {
                    autoComplete.add("particleout:");
                }
            }
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("warp"))) {
            for (AdvancedPortal portal : Portal.portals) {
                String perm = portal.getArg("permission");
                if ((perm == null || sender.hasPermission(perm))
                        && (sender.hasPermission("advancedportals.warp.*")
                        || sender.hasPermission("advancedportals.warp." + portal.getName()))) {
                    autoComplete.add(portal.getName());
                }
            }
        }
        else if (args.length == 2 &&
                (args[0].equalsIgnoreCase("remove")
                || args[0].equalsIgnoreCase("disablebeacon")
                || args[0].equalsIgnoreCase("we-selection"))) {
            for (AdvancedPortal portal : Portal.portals) {
                autoComplete.add(portal.getName());
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
            Object[] destiObj = destiConfig.getConfig().getKeys(false).toArray();
            for (Object object : destiObj) {
                autoComplete.add(tagStart + object.toString());
            }
        }
        if (args[args.length - 1].startsWith("particlein:") || args[args.length - 1].startsWith("particleout:")) {
            String tagStart = args[args.length - 1].startsWith("particlein:") ? "particlein:" : "particleout:";
            for (Particle particle : Particle.values()) {
                if(particle.getDataType() != Void.class)
                    continue;
                autoComplete.add(tagStart + particle.name().toLowerCase());
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
