package com.sekwah.advancedportals;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.sekwah.advancedportals.portalcontrolls.CreatePortal;

public class AdvancedPortalsCommand implements CommandExecutor {
	
	private AdvancedPortalsPlugin plugin;

	public AdvancedPortalsCommand(AdvancedPortalsPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("advancedportals").setExecutor(this);
	}

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
				// CreatePortal.CreatePortal(pos1, pos2); need to get pos 1 and 2
			}
			else if(args[0].toLowerCase().equals("show")){
				if(player.hasMetadata("Pos1World") && player.hasMetadata("Pos2World")){
					if(player.getMetadata("Pos1World").get(0).asString().equals(player.getMetadata("Pos2World").get(0).asString()) && player.getMetadata("Pos1World").get(0).asString().equals(player.getLocation().getWorld().getName())){
						Selection.Show(player, this.plugin);
					}
					else{
						player.sendMessage("§a[§eAdvancedPortals§a] Your currently selected area has been shown, it will dissapear shortly!");
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
