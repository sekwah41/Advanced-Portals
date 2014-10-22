package com.sekwah.advancedportals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.plugin.java.JavaPlugin;

import com.sekwah.advancedportals.DataCollector.DataCollector;
import com.sekwah.advancedportals.compat.NMS;
import com.sekwah.advancedportals.destinations.Destination;
import com.sekwah.advancedportals.metrics.Metrics;
import com.sekwah.advancedportals.portalcontrolls.Portal;

public class AdvancedPortalsPlugin extends JavaPlugin {
	
	public NMS nmsAccess;
	
	public void onEnable() {
		
		new Assets(this);
		
		// Opens a channel that messages bungeeCord
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		// thanks to the new config accessor code the config.saveDefaultConfig(); will now
		//  only copy the file if it doesnt exist!
        ConfigAccessor config = new ConfigAccessor(this, "Config.yml");
        config.saveDefaultConfig();
        
        ConfigAccessor portalconfig = new ConfigAccessor(this, "Portals.yml");
        portalconfig.saveDefaultConfig();
        
        ConfigAccessor destinationconfig = new ConfigAccessor(this, "Destinations.yml");
        destinationconfig.saveDefaultConfig();
		
        
        // Loads the portal and destination editors
        new Portal(this);
        new Destination(this);
        
        new DataCollector(this);
        
        // These register the commands
		new AdvancedPortalsCommand(this);
		new DestinationCommand(this);
		new WarpCommand(this);
		
		
		// These register the listeners
		new Listeners(this);
		
		new FlowStopper(this);
		new PortalProtect(this);
		new PortalPlacer(this);
		
		Selection.LoadData(this);
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
		
		this.getServer().getConsoleSender().sendMessage("§aAdvanced portals have been sucsessfully enabled!");
		
		String packageName = getServer().getClass().getPackage().getName();
		String[] packageSplit = packageName.split("\\.");
		String version = packageSplit[packageSplit.length - 1];

		try {
			Class<?> nmsClass = Class.forName("com.sekwah.advancedportals.compat." + version);
			if(NMS.class.isAssignableFrom(nmsClass)){
				this.nmsAccess = (NMS) nmsClass.getConstructor().newInstance();
			}else
			{
				System.out.println("Something went wrong, please notify the author and tell them this version v:" + version);
				this.setEnabled(false);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("This version of craftbukkit is not yet supported, please notify the author and give them this version v:" + version);
			this.setEnabled(false);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("§cAdvanced portals are being disabled!");
	}
	
	
}
