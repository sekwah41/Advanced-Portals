package com.sekwah.advancedportals.portalcontrolls;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class CreatePortal {
	
	public static void CreatePortal(Location pos1, Location pos2) {
		if(pos1.getWorld() == pos2.getWorld()) {
			// creation code
		}
		else{
			// code to say they have to be in the same world.
		}
	}
	
}
