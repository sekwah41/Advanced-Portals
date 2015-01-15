package com.sekwah.advancedportals;

import com.sekwah.advancedportals.events.WarpEvent;
import com.sekwah.advancedportals.portalcontrolls.AdvancedPortal;
import com.sekwah.advancedportals.portalcontrolls.Portal;
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
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Listeners implements Listener {
	
	private final AdvancedPortalsPlugin plugin;

	private boolean DefaultPortalMessages = true;
	
	// The needed config values will be stored so they are easier to access later
	// an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("\u00A7eP...
	private static boolean UseOnlyServerAxe = false;

	private static Material WandMaterial;

	private static boolean ShowBungeeMessage;
	
    @SuppressWarnings("deprecation")
	public Listeners(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        
        ConfigAccessor config = new ConfigAccessor(plugin, "Config.yml");
        UseOnlyServerAxe = config.getConfig().getBoolean("UseOnlyServerMadeAxe");
        
		String ItemID = config.getConfig().getString("AxeItemId");
		
		DefaultPortalMessages  = config.getConfig().getBoolean("PortalWarpMessages");
		
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
        
        ShowBungeeMessage = config.getConfig().getBoolean("ShowBungeeWarpMessage");
        
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
        	AdvancedPortal[] portals = Portal.Portals;
        	int portalId = 0;
        	for(AdvancedPortal portal : portals){
        		if(Portal.Portals[portalId].worldName.equals(loc.getWorld().getName())){
        			if(Portal.Portals[portalId].trigger.equals(loc.getBlock().getType())
        					|| Portal.Portals[portalId].trigger.equals(eyeloc.getBlock().getType())){
        				if((Portal.Portals[portalId].pos1.getX() + 1D) >= loc.getX() && (Portal.Portals[portalId].pos1.getY() + 1D) >= loc.getY() && (Portal.Portals[portalId].pos1.getZ() + 1D) >= loc.getZ()){
        					if(Portal.Portals[portalId].pos2.getX() <= loc.getX() && Portal.Portals[portalId].pos2.getY() <= loc.getY() && Portal.Portals[portalId].pos2.getZ() <= loc.getZ()){
        						
        						
        						WarpEvent warpEvent = new WarpEvent(player, portal.portalName);
        						plugin.getServer().getPluginManager().callEvent(event);
        						
        						if (!event.isCancelled()) {
        							boolean warped = Portal.activate(player, portal.portalName);
            						if(DefaultPortalMessages && warped){
            							ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
            							player.sendMessage("");
            							player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] You have warped to \u00A7e" + config.getConfig().getString(Portal.Portals[portalId].portalName + ".destination") + ".");
            							player.sendMessage("");
            						}
            						
            						if(!warped){
            							player.teleport(fromloc);
            							event.setCancelled(true);
            						}
        				        }
        						else{
        							
        						}
        						
        						
        						if(Portal.Portals[portalId].trigger.equals(Material.PORTAL)){
        							final Player finalplayer = event.getPlayer();
        							if(player.getGameMode().equals(GameMode.CREATIVE)){
        								player.setMetadata("HasWarped", new FixedMetadataValue(plugin, true));
        								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
        									public void run(){
        										finalplayer.removeMetadata("HasWarped", plugin);
        									}
        								}, 10);
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
    			if(Portal.Portals[portalId].worldName.equals(player.getWorld().getName())){

    				if((Portal.Portals[portalId].pos1.getX() + 1D) >= loc.getX() && (Portal.Portals[portalId].pos1.getY() + 1D) >= loc.getY() && (Portal.Portals[portalId].pos1.getZ() + 1D) >= loc.getZ()){

    					if((Portal.Portals[portalId].pos2.getX()) <= loc.getX() && (Portal.Portals[portalId].pos2.getY()) <= loc.getY() && (Portal.Portals[portalId].pos2.getZ()) <= loc.getZ()){
    						
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
    			if(Portal.Portals[portalId].worldName.equals(block.getWorld().getName())){

    				if((Portal.Portals[portalId].pos1.getX() + 3D) >= block.getX() && (Portal.Portals[portalId].pos1.getY() + 3D) >= block.getY() && (Portal.Portals[portalId].pos1.getZ() + 3D) >= block.getZ()){

    					if((Portal.Portals[portalId].pos2.getX() - 3D) <= block.getX() && (Portal.Portals[portalId].pos2.getY() - 3D) <= block.getY() && (Portal.Portals[portalId].pos2.getZ() - 3D) <= block.getZ()){
    						player.sendMessage("\u00A7a[\u00A7eAdvancedPortals\u00A7a] You have selected: \u00A7e" + Portal.Portals[portalId].portalName);
    						// TODO add code somewhere so when a portal is removed or changed if someone has it selected it notifies them
    						//   or removed their selections and tells them, maybe not before this update.
    						player.removeMetadata("selectingPortal", plugin);
    						player.setMetadata("selectedPortal", new FixedMetadataValue(plugin, Portal.Portals[portalId].portalName)); // adds the name to the metadata of the character
    						event.setCancelled(true);
    						player.removeMetadata("selectingPortal", plugin);
    			    		return;

    					}
    				}

    			}
    			portalId++;
    		}
    		player.sendMessage("\u00A7c[\u00A77AdvancedPortals\u00A7c] No portal was selected - if you would like to stop selecting please type \u00A7e/portal select \u00A7cagain!");
    		event.setCancelled(true);
    		return;
    	}
    	
    	if(player.hasPermission("AdvancedPortals.CreatePortal")){
    		
    		// UseOnlyServerMadeAxe being set to true makes is so only the axe generated by the server can be used so other iron axes can be used normally,
    		//  by default its false but it is a nice feature in case the user wants to use the axe normally too, such as a admin playing survival or it being used
    		//  as a weapon.
    		if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("\u00A7ePortal Region Selector")) && event.getPlayer().getItemInHand().getTypeId() == WandMaterial.getId()) {
    			
    			// This checks if the action was a left or right click and if it was directly effecting a block.
    			if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
    				Location blockloc = event.getClickedBlock().getLocation();
    				// stores the selection as metadata on the character so then it isn't saved anywhere, if the player logs out it will
    				//  have to be selected again if the player joins, also it does not affect any other players.
    				player.setMetadata("Pos1X", new FixedMetadataValue(plugin, blockloc.getBlockX()));
    				player.setMetadata("Pos1Y", new FixedMetadataValue(plugin, blockloc.getBlockY()));
    				player.setMetadata("Pos1Z", new FixedMetadataValue(plugin, blockloc.getBlockZ()));
    				player.setMetadata("Pos1World", new FixedMetadataValue(plugin, blockloc.getWorld().getName()));
    				player.sendMessage("\u00A7eYou have selected pos1! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ() + " World: " +  blockloc.getWorld().getName());
    				
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
    				player.sendMessage("\u00A7eYou have selected pos2! X:" + blockloc.getBlockX() + " Y:" + blockloc.getBlockY() + " Z:" + blockloc.getBlockZ() + " World: " +  blockloc.getWorld().getName());
    				
    				// Stops the event so the block is not interacted with
    				event.setCancelled(true);
    				
    				// Returns the event so no more code is executed(stops unnecessary code being executed)
    				return;
    			}
    			
    		}
    		
    	}
    	
    }
    
    
	
}
