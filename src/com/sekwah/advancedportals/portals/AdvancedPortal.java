package com.sekwah.advancedportals.portals;

import com.sekwah.advancedportals.api.portaldata.PortalArg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class AdvancedPortal {

    private Material trigger = null;

    private String worldName = null;

    private Location pos1 = null;

    private Location pos2 = null;

    private String portalName = null;

    // TODO store destinations also as variables like portals
    private String destiation = null; // Could possibly store the destination name to stop the server having to read the config file

    private String bungee = null; // Could possibly store the bungee server name to stop the server having to read the config file

    // Bungee will be stored inside the destination.

    private PortalArg[] portalArgs = null;

    public HashSet<UUID> inPortal = new HashSet<UUID>();

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
        for (PortalArg portalArg : this.portalArgs) {
            if (arg.equals(portalArg.argName)) {
                return portalArg.value;
            }
        }
        return null;
    }

    public PortalArg[] getArgs(){
        return this.portalArgs;
    }

    public boolean hasArg(String arg) {
        return this.getArg(arg) != null;
    }

    public Material getTrigger() {
        return this.trigger;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public Location getPos1() {
        return this.pos1;
    }

    public Location getPos2() {
        return this.pos2;
    }

    public String getName() {
        return this.portalName;
    }

    public String getDestiation() {
        return this.destiation;
    }

    public void setDestiation(String destiation) {
        this.destiation = destiation;
    }

    public String getBungee() {
        return this.bungee;
    }

    public void setBungee(String bungee) {
        this.bungee = bungee;
    }
}
