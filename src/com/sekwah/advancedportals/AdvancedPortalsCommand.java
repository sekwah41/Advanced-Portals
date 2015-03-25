package com.sekwah.advancedportals;

import com.sekwah.advancedportals.portals.Portal;
import com.sekwah.advancedportals.portals.PortalArgs;
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
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class AdvancedPortalsCommand implements CommandExecutor, TabCompleter {

	private AdvancedPortalsPlugin plugin;

	public AdvancedPortalsCommand(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;

		plugin.getCommand("advancedportals").setExecutor(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		Player player = (Player)sender;
		ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
		if(sender.hasPermission("advancedportals.portal")){
			if(args.length > 0){
				if(args[0].toLowerCase().equals("wand") || args[0].toLowerCase().equals("selector")){
					PlayerInventory inventory = player.getInventory();

					String ItemID = config.getConfig().getString("AxeItemId");


					Material WandMaterial = Material.getMaterial(ItemID);

					if(WandMaterial == null){
						WandMaterial = Material.IRON_AXE;
					}

					ItemStack regionselector = new ItemStack(WandMaterial);
					ItemMeta selectorname = regionselector.getItemMeta();
					selectorname.setDisplayName("\u00A7ePortal Region Selector");
					selectorname.setLore(Arrays.asList("\u00A7rThis wand with has the power to help"
							, "\u00A7r create portals bistowed upon it!"));
					regionselector.setItemMeta(selectorname);

					inventory.addItem(regionselector);
					sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] You have been given a \u00A7ePortal Region Selector\u00A7a!");
				}
				else if(args[0].toLowerCase().equals("portal") || args[0].toLowerCase().equals("portalblock")){
					PlayerInventory inventory = player.getInventory();

					ItemStack portalBlock = new ItemStack(Material.PORTAL);

					inventory.addItem(portalBlock);

					sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] You have been given a \u00A7ePortal Block\u00A7a!");
				}
				else if(args[0].toLowerCase().equals("create")) {
					if(player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")){
						if(player.getMetadata("Pos1World").get(0).asString().equals(player.getMetadata("Pos2World").get(0).asString()) && player.getMetadata("Pos1World").get(0).asString().equals(player.getLocation().getWorld().getName())){
							if(args.length >= 2){ // may make this next piece of code more efficient, maybe check against a list of available variables or something
								boolean hasName = false;
								boolean hasTriggerBlock = false;
								boolean hasDestination = false;
								boolean isBungeePortal = false;
                                boolean needsPermission = false;
								String destination = null;
								String portalName = null;
								String triggerBlock = null;
								String serverName = null;
                                String permission = null;

                                ArrayList<PortalArgs> extraData = new ArrayList<PortalArgs>();

								for(int i = 1; i < args.length; i++){
									if(args[i].toLowerCase().startsWith("name:") && args[i].length() > 5){
										hasName = true;
										portalName = args[i].replaceFirst("name:", "");
									}
									else if(args[i].toLowerCase().startsWith("name:")) {
										player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You must include a name for the portal that isnt nothing!");
										return true;
									}
									else if(args[i].toLowerCase().startsWith("destination:") && args[i].length() > 12){
										hasDestination = true;
										destination = args[i].toLowerCase().replaceFirst("destination:", "");
									}
									else if(args[i].toLowerCase().startsWith("desti:") && args[i].length() > 6){
										hasDestination = true;
										destination = args[i].toLowerCase().replaceFirst("desti:", "");
									}
									else if(args[i].toLowerCase().startsWith("triggerblock:") && args[i].length() > 13){
										hasTriggerBlock = true;
										triggerBlock = args[i].toLowerCase().replaceFirst("triggerblock:", "");
									}
									else if(args[i].toLowerCase().startsWith("triggerblock:") && args[i].length() > 13){
										hasTriggerBlock = true;
										triggerBlock = args[i].toLowerCase().replaceFirst("triggerblock:", "");
									}
									else if(args[i].toLowerCase().startsWith("bungee:") && args[i].length() > 7){ // not completely implemented
										isBungeePortal = true;
										serverName = args[i].toLowerCase().replaceFirst("bungee:", "");
                                        //extraData.add(new PortalArgs("bungee", serverName));
									}
                                    else if(args[i].toLowerCase().startsWith("permission:") && args[i].length() > 11){ // not completely implemented
                                        needsPermission = true;
                                        permission = args[i].toLowerCase().replaceFirst("permission:", "");
                                        extraData.add(new PortalArgs("permission", serverName));
                                    }
								}
								if(!hasName){
									player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You must include a name for the portal that you are creating in the variables!");
									return true;
								}

								World world = org.bukkit.Bukkit.getWorld(player.getMetadata("Pos1World").get(0).asString());
								Location pos1 = new Location(world, player.getMetadata("Pos1X").get(0).asInt(), player.getMetadata("Pos1Y").get(0).asInt(), player.getMetadata("Pos1Z").get(0).asInt());
								Location pos2 = new Location(world, player.getMetadata("Pos2X").get(0).asInt(), player.getMetadata("Pos2Y").get(0).asInt(), player.getMetadata("Pos2Z").get(0).asInt());

								ConfigAccessor desticonfig = new ConfigAccessor(plugin, "Destinations.yml");
								String destiPosX = desticonfig.getConfig().getString(destination + ".pos1.X");

								if(!Portal.portalExists(portalName)){

									player.sendMessage("");
									player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a]\u00A7e You have created a new portal with the following details:");
									player.sendMessage("\u00A7aname: \u00A7e" + portalName);
									if(hasDestination){
										player.sendMessage("\u00A7adestination: \u00A7e" + destination);
									}
									else if(destiPosX == null){
										player.sendMessage("\u00A7cdestination: \u00A7e" + destination + " (destination does not exist)");
									}
									else{
										player.sendMessage("\u00A7cdestination: \u00A7eN/A (will not work)");
									}

									if(isBungeePortal){
										player.sendMessage("\u00A7abungee: \u00A7e" + serverName);
									}

                                    if(needsPermission){
                                        player.sendMessage("\u00A7apermission: \u00A7e" + permission);
                                    }
                                    else{
                                        player.sendMessage("\u00A7apermission: \u00A7e(none needed)");
                                    }

									Material triggerBlockMat = Material.getMaterial(0);
									if(hasTriggerBlock){
												triggerBlockMat = Material.getMaterial(triggerBlock.toUpperCase());
												if(triggerBlockMat != null){
													player.sendMessage("\u00A7atriggerBlock: \u00A7e" + triggerBlock.toUpperCase());
													player.sendMessage(Portal.create(pos1, pos2, portalName, destination, triggerBlockMat, serverName, (PortalArgs[]) extraData.toArray()));
												}
												else{
													hasTriggerBlock = false;
													ConfigAccessor Config = new ConfigAccessor(plugin, "Config.yml");
													player.sendMessage("\u00A7ctriggerBlock: \u00A7edefault(" + Config.getConfig().getString("DefaultPortalTriggerBlock") + ")");

													player.sendMessage("\u00A7cThe block " + triggerBlock.toUpperCase() + " is not a valid block name in minecraft so the trigger block has been set to the default!");
													player.sendMessage(Portal.create(pos1, pos2, portalName, destination, serverName, (PortalArgs[]) extraData.toArray()));
											}
									}
									else{
										ConfigAccessor Config = new ConfigAccessor(plugin, "Config.yml");
										player.sendMessage("\u00A7atriggerBlock: \u00A7edefault(" + Config.getConfig().getString("DefaultPortalTriggerBlock") + ")");
										player.sendMessage(Portal.create(pos1, pos2, portalName, destination, serverName, (PortalArgs[]) extraData.toArray()));
									}
								}
								else{
									sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] A portal by that name already exists!");
								}

								// add code to save the portal to the portal config and reload the portals

								player.sendMessage("");
							}
							else{
								player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to at least add the name of the portal as a variable, \u00A7cType \u00A7e/portal variables\u00A7c"
										+ " for a full list of currently available variables and an example command!");
							}
						}
						else{
							player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The points you have selected need to be in the same world!");
						}
					}
					else{
						player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to have two points selected to make a portal!");
					}
				}
				else if(args[0].toLowerCase().equals("variables")) {
					player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Currently available variables: name, triggerBlock, destination");
					player.sendMessage("");
					player.sendMessage("\u00A7aExample command: \u00A7e/portal create name:test triggerId:portal");
				}
				else if(args[0].toLowerCase().equals("select")) {

					// TODO finish the select command and the hit block to replace!

					if(!player.hasMetadata("selectingPortal")){
						if(args.length > 1){
							if(Portal.portalExists(args[1])){
								player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, args[1]));
							}
							else{
								player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal by the name \u00A7e" + args[1] + "\u00A7c exists (maybe you got the caps wrong)\n Try typing \u00A7e/portal select\u00A7c and hit inside the apropriate portals area!");
							}
						}
						else{
							player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Hit a block inside the portal region to select the portal!");
							player.setMetadata("selectingPortal", new FixedMetadataValue(plugin, true));
						}

					}
					else{
						player.removeMetadata("selectingPortal", plugin);
						player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] Portal selection cancelled!");
					}
				}
				else if(args[0].toLowerCase().equals("gui")){
					if(args.length > 1){
						if(args[1].toLowerCase().equals("remove") && args.length > 2){
							sender.sendMessage("");
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] Are you sure you would like to remove the portal \u00A7e" + args[2] + "\u00A7c?");
							sender.sendMessage("");
							plugin.nmsAccess.sendRawMessage("{text:\"    \",extra:[{text:\"\u00A7e[Yes]\",hoverEvent:{action:show_text,value:\"Confirm removing this portal\"},clickEvent:{action:run_command,value:\"/portal remove " + args[2] + "\"}}, " +
							"{text:\"     \"},{text:\"\u00A7e[No]\",hoverEvent:{action:show_text,value:\"Cancel removing this portal\"},clickEvent:{action:run_command,value:\"/portal edit " + args[2] + "\"}}]}", player);
					        sender.sendMessage("");
						}
					}
				}
				else if(args[0].toLowerCase().equals("edit")) {
					ConfigAccessor portalConfig = new ConfigAccessor(plugin, "Portals.yml");
					if(args.length > 1){
						String posX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
						if(posX != null){
							portalEditMenu(sender, portalConfig, args[1]);
						}
						else{
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal by the name \u00A7e" + args[1] + "\u00A7c exists!");
						}
					}
					else{
						if(player.hasMetadata("selectedPortal")){
							String portalName = player.getMetadata("selectedPortal").get(0).asString();
							String posX = portalConfig.getConfig().getString(portalName + ".pos1.X");
							if(posX != null){
								portalEditMenu(sender, portalConfig, portalName);
							}
							else{
								sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal you had selected no longer seems to exist!");
								player.removeMetadata("selectedPortal", plugin);
							}
						}
						else{
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal has been defined or selected!");
						}
					}
				}
				else if(args[0].toLowerCase().equals("rename")) {

					// not finished yet /
					ConfigAccessor portalConfig = new ConfigAccessor(plugin, "Portals.yml");
					if(args.length > 1){
						if(player.hasMetadata("selectedPortal")){
							String portalName = player.getMetadata("selectedPortal").get(0).asString();
							if(portalName.toLowerCase() != args[1].toLowerCase()){
								String posX = portalConfig.getConfig().getString(portalName + ".pos1.X");

								String newPortalPosX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
								if(posX != null && newPortalPosX == null){
									Portal.rename(portalName, args[1]);
									sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] The portal \u00A7e" + portalName + "\u00A7a has been renamed to \u00A7e" + args[1] + "\u00A7a.");
									player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, args[1]));
								}
								else if(newPortalPosX != null){
									sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] There is already a portal with the name \u00A7e" + args[1] + "\u00A7c!");
								}
								else{
									sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal you had selected no longer seems to exist!");
									player.removeMetadata("selectedPortal", plugin);
								}
							}
							else{
								sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal you have selected is already called that!");
							}
						}
						else{
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal has been defined or selected!");
						}
					}
					else{
						sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You must select a portal first and then type \u00A7e/portal rename (new name)\u00A7c!");
					}
				}
				else if(args[0].toLowerCase().equals("remove")) {
					ConfigAccessor portalConfig = new ConfigAccessor(plugin, "Portals.yml");
					if(args.length > 1){
						String posX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
						if(posX != null){
							Portal.remove(args[1]);
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal \u00A7e" + args[1] + "\u00A7c has been removed!");
						}
						else{
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal by that name exists!");
						}
					}
					else{
						if(player.hasMetadata("selectedPortal")){
							String portalName = player.getMetadata("selectedPortal").get(0).asString();
							String posX = portalConfig.getConfig().getString(portalName + ".pos1.X");
							if(posX != null){
								Portal.remove(portalName);
								sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal \u00A77" + portalName + " has been removed!");
							}
							else{
								sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The portal you had selected no longer seems to exist!");
								player.removeMetadata("selectedPortal", plugin);
							}
						}
						else{
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal has been defined or selected!");
						}
					}
				}
				else if(args[0].toLowerCase().equals("bukkitpage")) {
					player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Bukkit page: (insert bitly link)!");
				}
				else if(args[0].toLowerCase().equals("helppage")) {
					player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Help page: (insert bitly link)!");
				}
				else if(args[0].toLowerCase().equals("show")){
					ConfigAccessor portalConfig = new ConfigAccessor(plugin, "Portals.yml");
					if(args.length > 1){
						String posX = portalConfig.getConfig().getString(args[1] + ".pos1.X");
						if(posX != null){
							Selection.Show(player, this.plugin, args[1]);
						}
						else{
							sender.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal by that name exists!");
						}
					}
					else{
						if(player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")){
							if(player.getMetadata("Pos1World").get(0).asString().equals(player.getMetadata("Pos2World").get(0).asString()) && player.getMetadata("Pos1World").get(0).asString().equals(player.getLocation().getWorld().getName())){
								player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Your currently selected area has been shown, it will dissapear shortly!");
								Selection.Show(player, this.plugin);
							}
							else{
								player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] The points you have selected need to be in the same world!");
							}
						}
						else{
							player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] You need to have both points selected!");
						}
					}
				}
				else if(args[0].toLowerCase().equals("help")) {
					player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Reloaded values!");
					Listeners.reloadValues(plugin);
					Portal.loadPortals();
				}
				else{
					PluginMessages.UnknownCommand(sender, command);
				}
			}
			else{
				PluginMessages.UnknownCommand(sender, command);
			}

		}
		else{
			PluginMessages.NoPermission(sender, command);
		}

		return true;
	}

	private void portalEditMenu(CommandSender sender, ConfigAccessor portalConfig, String portalName) {
		// make the text gui with the json message for a list of edit commands to be clicked or hovered
		// put \" for a " in the json messages
		// sadly there is no newline code so these three lines will have to be copied and pasted for each line

		// use the usual messages for normal lines but anything that needs special features make sure you use the
		//  chat steriliser
		sender.sendMessage("");
		sender.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] Editing: \u00A7e" + portalName);

		sender.sendMessage(" \u00A7apos1\u00A7e: " + portalConfig.getConfig().getString(portalName + ".pos1.X") + ", " + portalConfig.getConfig().getString(portalName + ".pos1.Y") + ", " + portalConfig.getConfig().getString(portalName + ".pos1.Z"));
		sender.sendMessage(" \u00A7apos2\u00A7e: " + portalConfig.getConfig().getString(portalName + ".pos2.X") + ", " + portalConfig.getConfig().getString(portalName + ".pos2.Y") + ", " + portalConfig.getConfig().getString(portalName + ".pos2.Z"));

		String destination = portalConfig.getConfig().getString(portalName + ".destination");
		if(destination != null){
			sender.sendMessage(" \u00A7adestination\u00A7e: " + destination);
		}
		else{
			sender.sendMessage(" \u00A7cdestination\u00A7e: null");
		}

		String trigger = portalConfig.getConfig().getString(portalName + ".triggerblock");
		if(trigger != null){
			sender.sendMessage(" \u00A7atriggerBlock\u00A7e: " + trigger);
		}
		else{
			sender.sendMessage(" \u00A7ctriggerBlock\u00A7e: null");
		}
		sender.sendMessage("");

		Player player = (Player)sender;

		plugin.nmsAccess.sendRawMessage("{text:\"\u00A7aFunctions\u00A7e: \",extra:[{text:\"\u00A7eRemove\",hoverEvent:{action:show_text,value:\"Remove the selected portal\"},clickEvent:{action:run_command,value:\"/portal gui remove " + portalName + "\"}}"
				+ ",{text:\"  \"},{text:\"\u00A7eShow\",hoverEvent:{action:show_text,value:\"Show the selected portal\"},clickEvent:{action:run_command,value:\"/portal show " + portalName + "\"}}"
				+ ",{text:\"  \"},{text:\"\u00A7eRename\",hoverEvent:{action:show_text,value:\"Change the name of the portal\"},clickEvent:{action:suggest_command,value:\"/portal rename \"}}"
				+ ",{text:\"  \"},{text:\"\u00A7eActivate\",hoverEvent:{action:show_text,value:\"Teleport to the set destination\n(same as entering the portal)\"},clickEvent:{action:run_command,value:\"/warp " + destination + "\"}}]}", player);

		/**IChatBaseComponent comp = ChatSerializer.a("{text:\"\u00A7aFunctions\u00A7e: \",extra:[{text:\"\u00A7eRemove\",hoverEvent:{action:show_text,value:\"Remove the selected portal\"},clickEvent:{action:run_command,value:\"/portal gui remove " + portalName + "\"}}"
				+ ",{text:\"  \"},{text:\"\u00A7eShow\",hoverEvent:{action:show_text,value:\"Show the selected portal\"},clickEvent:{action:run_command,value:\"/portal show " + portalName + "\"}}"
				+ ",{text:\"  \"},{text:\"\u00A7eRename\",hoverEvent:{action:show_text,value:\"Change the name of the portal\"},clickEvent:{action:suggest_command,value:\"/portal rename \"}}"
				+ ",{text:\"  \"},{text:\"\u00A7eActivate\",hoverEvent:{action:show_text,value:\"Teleport to the set destination\n(same as entering the portal)\"},clickEvent:{action:run_command,value:\"/warp " + destination + "\"}}]}");
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);*/
        sender.sendMessage("");

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String command, String[] args) {
		LinkedList<String> autoComplete = new LinkedList<String>();
		if(sender.hasPermission("AdvancedPortals.CreatePortal")){
			if(args.length == 1){
				autoComplete.addAll(Arrays.asList("create", "portal", "portalblock", "select", "selector"
						, "show", "variables", "wand", "remove", "rename", "help", "bukkitpage", "helppage"));
			}
			else if(args[0].toLowerCase().equals("create")){

				boolean hasName = false;
				boolean hasTriggerBlock = false;
				boolean hasDestination = false;
				boolean isBungeePortal = false;
                boolean needsPermission = false;

				for(int i = 1; i < args.length; i++){
					if(args[i].toLowerCase().startsWith("name:") && args[i].length() > 5){
						hasName = true;
					}
					else if(args[i].toLowerCase().startsWith("destination:") && args[i].length() > 12){
						hasDestination = true;
					}
					else if(args[i].toLowerCase().startsWith("desti:") && args[i].length() > 6){
						hasDestination = true;
					}
					else if(args[i].toLowerCase().startsWith("triggerblock:") && args[i].length() > 13){
						hasTriggerBlock = true;
					}
					else if(args[i].toLowerCase().startsWith("bungee:") && args[i].length() > 7){
						isBungeePortal = true;
					}
                    else if(args[i].toLowerCase().startsWith("permission:") && args[i].length() > 11){
                        needsPermission = true;
                    }

				}

				if(!hasName){autoComplete.add("name:");}
				if(!hasTriggerBlock){autoComplete.add("triggerblock:");}
				if(!hasDestination){autoComplete.add("destination:");autoComplete.add("desti:");}
				if(!isBungeePortal){autoComplete.add("bungee:");}
                if(!isBungeePortal){autoComplete.add("permission:");}
			}
		}
		Collections.sort(autoComplete);
		for(Object result: autoComplete.toArray()){
			if(!result.toString().startsWith(args[args.length - 1])){
				autoComplete.remove(result);
			}
		}
		return autoComplete;
	}

}
