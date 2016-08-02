package com.sekwah.advancedportals.portals;

import com.sekwah.advancedportals.api.portaldata.PortalArg;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AdvancedPortal {

    public Material trigger = null;

    public String worldName = null;

    public Location pos1 = null;

    public Location pos2 = null;

    public String portalName = null;

    // TODO store destinations also as variables like portals
    public String destiation = null; // Could possibly store the destination name to stop the server having to read the config file

    public String bungee = null; // Could possibly store the bungee server name to stop the server having to read the config file

    // Bungee will be stored inside the destination.

    public PortalArg[] portalArgs = null;

    public HashSet<Player> inPortal = new HashSet<Player>();

    // TODO think of relaying out the data input to a more logical format.
    public AdvancedPortal(String portalName, Material trigger, String destination, Location pos1, Location pos2, PortalArg... portalArgs) {
        this(portalName, trigger, pos1, pos2, pos2.getWorld().getName(), portalArgs);
        this.destiation = destination;
    }

    public AdvancedPortal(String portalName, Material trigger, Location pos1, Location pos2, PortalArg... portalArgs) {
        this(portalName, trigger, pos1, pos2, pos2.getWorld().getName(), portalArgs);
    }

    public AdvancedPortal(String portalName, Material trigger, String destination, Location pos1, Location pos2, String worldName, PortalArg... portalArgs) {
        this(portalName, trigger, pos1, pos2, worldName, portalArgs);
        this.destiation = destination;
    }

    public AdvancedPortal(String portalName, Material trigger, Location pos1, Location pos2, String worldName, PortalArg... portalArgs) {
        this.portalName = portalName;
        this.trigger = trigger;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.worldName = worldName;
        this.portalArgs = portalArgs;
    }

    public String getArg(String arg) {
        for (PortalArg portalArg : portalArgs) {
            if (arg.equals(portalArg.argName)) {
                return portalArg.value;
            }
        }
        return null;
    }

}
