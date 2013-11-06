package com.sekwah.advancedportals;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.sekwah.advancedportals.portalcontrolls.Portal;

public class AdvancedPortalsCommand implements CommandExecutor {
	
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
		if(args.length > 0){
			if(args[0].toLowerCase().equals("wand") || args[0].toLowerCase().equals("selector")){
				if(sender.hasPermission("AdvancedPortals.CreatePortal")){
					PlayerInventory inventory = player.getInventory();
					
					ItemStack regionselector = new ItemStack(Material.IRON_AXE);
					ItemMeta selectorname = regionselector.getItemMeta();
					selectorname.setDisplayName("§ePortal Region Selector");
					selectorname.setLore(Arrays.asList("§rThis iron axe with has the power to help"
							, "§r create portals bistowed upon it!"));
					regionselector.setItemMeta(selectorname);
					
					inventory.addItem(regionselector);
					sender.sendMessage("§a[§eAdvancedPortals§a] You have been given a §ePortal Region Selector§a!");
				}
				else{
					PluginMessages.NoPermission(sender, command);
				}
			}
			else if(args[0].toLowerCase().equals("create")) {
				if(player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")){
					if(player.getMetadata("Pos1World").get(0).asString().equals(player.getMetadata("Pos2World").get(0).asString()) && player.getMetadata("Pos1World").get(0).asString().equals(player.getLocation().getWorld().getName())){
						player.sendMessage("");
						player.sendMessage("§a[§eAdvancedPortals§a] You have created a new portal with the following details:");
						boolean hasTriggerBlock = false;
						Material triggerBlockId = Material.getMaterial(0);
						if(hasTriggerBlock){
							
							try
							{
								triggerBlockId = Material.getMaterial(Integer.parseInt("90"));
							}
							catch(Exception e)
							{
								triggerBlockId = Material.getMaterial("PORTAL");
							}
						}
						Portal.create(player, null, null, "portalname");
					}
					else{
						player.sendMessage("§c[§7AdvancedPortals§c] The points you have selected need to be in the same world!");
					}
				}
				else{
					player.sendMessage("§c[§7AdvancedPortals§c] You need to have two points selected to make a portal!");
				}
			}
			else if(args[0].toLowerCase().equals("select")) {
				
			}
			else if(args[0].toLowerCase().equals("show")){
				if(player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")){
					if(player.getMetadata("Pos1World").get(0).asString().equals(player.getMetadata("Pos2World").get(0).asString()) && player.getMetadata("Pos1World").get(0).asString().equals(player.getLocation().getWorld().getName())){
						player.sendMessage("§a[§eAdvancedPortals§a] Your currently selected area has been shown, it will dissapear shortly!");
						Selection.Show(player, this.plugin);
					}
					else{
						player.sendMessage("§c[§7AdvancedPortals§c] The points you have selected need to be in the same world!");
					}
				}
				else{
					player.sendMessage("§c[§7AdvancedPortals§c] You need to have both points selected!");
				}
			}
			else{
				PluginMessages.UnknownCommand(sender, command);
			}
		}
		else{
			sender.sendMessage("§c[§7AdvancedPortals§c] You need to type something after /" + command + "\n"
					+ "if you do not know what you can put or would like some help with the commands please type /" + command + " help");
		}
		
		return true;
	}

}
