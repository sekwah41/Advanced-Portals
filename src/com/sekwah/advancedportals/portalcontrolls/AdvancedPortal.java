package com.sekwah.advancedportals.portalcontrolls;

import org.bukkit.Location;
import org.bukkit.Material;

public class AdvancedPortal {
	
	public Material trigger = null;
	
	public String worldName = null;
	
	public Location pos1 = null;
	
	public Location pos2 = null;

	public String portalName = null;
	
	public String destiation = null; // Could possibly store the destination name to stop the server having to read the config file
	
	public String bungee = null; // Could possibly store the bungee server name to stop the server having to read the config file
}
