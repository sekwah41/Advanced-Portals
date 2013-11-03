package com.sekwah.advancedportals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;



public class Portals {
	
	private final AdvancedPortalsPlugin plugin;
	
	private List<String> portals;
	
	
	
    public Portals(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        this.loadPortalData();
    }
	
	public void loadPortalData(){
		ConfigAccessor config = new ConfigAccessor(plugin, "Portals.yml");
        List<String> portals = config.getConfig().getStringList("portals");
        if(portals.size() > 0){
        	
        }
        else{
        	plugin.getLogger().log(Level.INFO, "There are currently no portals!");
        }
	}
}
