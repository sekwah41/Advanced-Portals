package com.sekwah.advancedportals.DataCollector;

import java.io.IOException;
import java.util.logging.Level;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.destinations.Destination;
import com.sekwah.advancedportals.metrics.Metrics;
import com.sekwah.advancedportals.metrics.Metrics.Graph;

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
	
	public static void playerWarped() {
		try {
		    Metrics metrics = new Metrics(plugin);

		    Graph TotalWarps = metrics.createGraph("Total Warps");

		    TotalWarps.addPlotter(new Metrics.Plotter("Internal Warps") {

		            @Override
		            public int getValue() {
		                    return 1; // number of warps
		            }

		    });

		    /**TotalWarps.addPlotter(new Metrics.Plotter("Iron Sword") {
		    	// can be used to add more data to the graph, can be in a pie chart or multiple lines on one graph
		            @Override
		            public int getValue() {
		                    return 17;
		            }

		    });*/

		    metrics.start();
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not submit data", e);
		}
	}
	
	public static void portalCreated(String triggerName) {
		try {
		    Metrics metrics = new Metrics(plugin);
		    Graph TotalWarps = metrics.createGraph("Portal Trigger Blocks");

		    TotalWarps.addPlotter(new Metrics.Plotter(triggerName) {

		            @Override
		            public int getValue() {
		                    return 1; // number of portals created
		            }

		    });

		    /**TotalWarps.addPlotter(new Metrics.Plotter("Iron Sword") {
		    	// can be used to add more data to the graph, can be in a pie chart or multiple lines on one graph
		            @Override
		            public int getValue() {
		                    return 17;
		            }

		    });*/

		    metrics.start();
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not submit data", e);
		}
	}

}
