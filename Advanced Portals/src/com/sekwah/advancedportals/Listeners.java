package com.sekwah.advancedportals;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.event.player.PlayerPortalEvent;

import com.sekwah.advancedportals.DataCollector.DataCollector;
import com.sekwah.advancedportals.portalcontrolls.Portal;

public class Listeners implements Listener {
	
	private final AdvancedPortalsPlugin plugin;
	
	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("§eP...
	private static boolean UseOnlyServerAxe = false;

	private static Material WandMaterial;
	
    @SuppressWarnings("deprecation")
	public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");
        
		String ItemID = config.getConfig().getString("AxeItemId");
		
		try
		{
			WandMaterial = Material.getMaterial(Integer.parseInt(ItemID));
		}
		catch(Exception e)
		{
			WandMaterial = Material.getMaterial(ItemID);
		}
        
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @SuppressWarnings("deprecation")
	public static void reloadValues(AdvancedPortalsPlugin plugin) {
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");
        
		String ItemID = config.getConfig().getString("AxeItemId");
		
		try
		{
			WandMaterial = Material.getMaterial(Integer.parseInt(ItemID));
		}
		catch(Exception e)
		{
			WandMaterial = Material.getMaterial(ItemID);
		}
    }
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMoveEvent(PlayerMoveEvent event) {
    	// will check if the player is in the portal or not.
    	if(Portal.portalsActive){
        	Player player = event.getPlayer();
        	Location fromloc = event.getFrom();
        	Location loc = event.getTo();
        	Location eyeloc = event.getTo();
        	//System.out.println(loc.getBlock().getType()); // for debugging, remove or comment out when not needed
        	eyeloc.setY(eyeloc.getY() + player.getEyeHeight());
        	Object[] portals = Portal.Portals;
        	int portalId = 0;
        	for(Object portal : portals){
        		if(Portal.worldName[portalId].equals(loc.getWorld().getName())){
        			if(Portal.triggers[portalId].equals(loc.getBlock().getType())
        					|| Portal.triggers[portalId].equals(eyeloc.getBlock().getType())){
        				if((Portal.pos1[portalId].getX() + 1D) >= loc.getX() && (Portal.pos1[portalId].getY() + 1D) >= loc.getY() && (Portal.pos1[portalId].getZ() + 1D) >= loc.getZ()){
        					if(Portal.pos2[portalId].getX() <= loc.getX() && Portal.pos2[portalId].getY() <= loc.getY() && Portal.pos2[portalId].getZ() <= loc.getZ()){
        						
        						boolean warped = Portal.activate(player, portal.toString());
        						
        						
        						if(!warped){
        							player.teleport(fromloc);
        							event.setCancelled(true);
        						}
        						else{
        							DataCollector.playerWarped();
        						}
        						
        						if(Portal.triggers[portalId].equals(Material.PORTAL)){
        							final Player finalplayer = event.getPlayer();
        							if(player.getGameMode().equals(GameMode.CREATIVE)){
        								player.setMetadata("HasWarped", new FixedMetadataValue(plugin, true));
        								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
        									public void run(){
        										finalplayer.removeMetadata("HasWarped", plugin);
        									}
        								}, 40);
        							}
        						}
        						
        					}
        				}
        				
        			}
        		}
        		portalId++;
        	}
        	
    	}
    	
    }
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {

    	if(Portal.portalsActive){
    		Player player = event.getPlayer();
    		
			if(player.hasMetadata("HasWarped")){
				event.setCancelled(true);
				return;
			}
			
    		Location loc = player.getLocation();
    		Object[] portals = Portal.Portals;
    		int portalId = 0;
    		for(Object portal : portals){
    			if(Portal.worldName[portalId].equals(player.getWorld().getName())){

    				if((Portal.pos1[portalId].getX() + 1D) >= loc.getX() && (Portal.pos1[portalId].getY() + 1D) >= loc.getY() && (Portal.pos1[portalId].getZ() + 1D) >= loc.getZ()){

    					if((Portal.pos2[portalId].getX()) <= loc.getX() && (Portal.pos2[portalId].getY()) <= loc.getY() && (Portal.pos2[portalId].getZ()) <= loc.getZ()){
    						
    						event.setCancelled(true);

    					}
    				}

    			}
    			portalId++;
    		}

    	}

    }
    
    @SuppressWarnings("deprecation")
    @EventHandler
    public void oniteminteract(PlayerInteractEvent event) {
    	// will detect if the player is using an axe so the points of a portal can be set
    	// also any other detections such as sign interaction or basic block protection
    	Player player = event.getPlayer();
    	
    	if(player.hasMetadata("selectingPortal") && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
    		Block block = event.getClickedBlock();
    		Object[] portals = Portal.Portals;
    		int portalId = 0;
    		for(Object portal : portals){
    			if(Portal.worldName[portalId].equals(block.getWorld().getName())){

    				if((Portal.pos1[portalId].getX() + 3D) >= block.getX() && (Portal.pos1[portalId].getY() + 3D) >= block.getY() && (Portal.pos1[portalId].getZ() + 3D) >= block.getZ()){

    					if((Portal.pos2[portalId].getX() - 3D) <= block.getX() && (Portal.pos2[portalId].getY() - 3D) <= block.getY() && (Portal.pos2[portalId].getZ() - 3D) <= block.getZ()){
    						player.sendMessage("§a[§eAdvancedPortals§a] You have selected: §e" + Portal.portalName[portalId]);
    						// TODO add code somewhere so when a portal is removed or changed if someone has it selected it notifies them
    						//   or removed their selections and tells them, maybe not before this update.
    						player.removeMetadata("selectingPortal", plugin);
    						player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, Portal.portalName[portalId])); // adds the name to the metadata of the character
    						event.setCancelled(true);
    						player.removeMetadata("selectingPortal", plugin);
    			    		return;

    					}
    				}

    			}
    			portalId++;
    		}
    		player.sendMessage("§c[§7AdvancedPortals§c] No portal was selected - if you would like to stop selecting please type §e/portal select §cagain!");
    		event.setCancelled(true);
    		return;
    	}
    	
    	if(player.hasPermission("AdvancedPortals.CreatePortal")){
    		
    		// UseOnlyServerMadeAxe being set to true makes is so only the axe generated by the server can be used so other iron axes can be used normally,
    		//  by default its false but it is a nice feature in case the user wants to use the axe normally too, such as a admin playing survival or it being used
    		//  as a weapon.
    		if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("§ePortal Region Selector")) && event.getPlayer().getItemInHand().getTypeId() == WandMaterial.getId()) {
    			
    			// This checks if the action was a left or right click and if it was directly effecting a block.
    			if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
    				Location blockloc = event.getClickedBlock().getLocation();
    				// stores the selection as metadata on the character so then it isn't saved anywhere, if the player logs out it will
    				//  have to be selected again if the player joins, also it does not affect any other players.
    				player.setMetadata("Pos1X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
    				player.setMetadata("Pos1Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
    				player.setMetadata("Pos1Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
    				player.setMetadata("Pos1World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
    				player.sendMessage("§eYou have selected pos1! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ() + " World: " +  blockloc.getWorld().getName());
    				
    				// Stops the event so the block is not damaged
    				event.setCancelled(true);
    				
    				// Returns the event so no more code is executed(stops unnecessary code being executed)
    				return;
    			}
    			else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    				Location blockloc = event.getClickedBlock().getLocation();
    				player.setMetadata("Pos2X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
    				player.setMetadata("Pos2Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
    				player.setMetadata("Pos2Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
    				player.setMetadata("Pos2World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
    				player.sendMessage("§eYou have selected pos2! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ() + " World: " +  blockloc.getWorld().getName());
    				
    				// Stops the event so the block is not interacted with
    				event.setCancelled(true);
    				
    				// Returns the event so no more code is executed(stops unnecessary code being executed)
    				return;
    			}
    			
    		}
    		
    	}
    	
    }
    
    
	
}
