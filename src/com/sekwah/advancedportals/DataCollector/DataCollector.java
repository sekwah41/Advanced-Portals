package com.sekwah.advancedportals.DataCollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.destinations.Destination;
import com.sekwah.advancedportals.metrics.Metrics;
import com.sekwah.advancedportals.metrics.Metrics.Graph;
import com.sekwah.advancedportals.portalcontrolls.AdvancedPortal;
import com.sekwah.advancedportals.portalcontrolls.Portal;

import org.bukkit.Material;

public class DataCollector {

	private static AdvancedPortalsPlugin plugin;

	public DataCollector(AdvancedPortalsPlugin plugin) {
		DataCollector.plugin = plugin;
	}
	
	/**
	 * 
	 * This is currently being tested as it doesn't fully work at the moment.
	 * 
	 */
	
	/**public static void playerWarped() {
		try {
		    Metrics metrics = new Metrics(plugin);

		    Graph TotalWarps = metrics.createGraph("Total Warps");
		    
		    TotalWarps.addPlotter(new Metrics.Plotter("Internal Warps") {

		            @Override
		            public int getValue() {
		                    return 1; // number of warps
		            }

		    });

		    metrics.start();
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not submit data", e);
		}
	}*/
	
	public static void setupMetrics() {
		
		try {
		    Metrics metrics = new Metrics(plugin);
		    Graph TotalWarps = metrics.createGraph("Portal Trigger Blocks");
		    
		    /**List<Material> MaterialList = new ArrayList<Material>();
		    for(AdvancedPortal portal : Portal.Portals){
		    	MaterialList.add(portal.trigger);
		    }*/

		    /**TotalWarps.addPlotter(new Metrics.Plotter(triggerName) {

		            @Override
		            public int getValue() {
		                    return 1; // number of portals created
		            }

		    });*/
		    
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not submit data", e);
		}
	}

}
