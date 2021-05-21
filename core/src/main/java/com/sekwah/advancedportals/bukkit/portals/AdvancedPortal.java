package com.sekwah.advancedportals.bukkit.portals;

import com.sekwah.advancedportals.bukkit.api.portaldata.PortalArg;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AdvancedPortal {

    private Set<Material> triggers = null;

    private String worldName = null;

    private Location pos1 = null;

    private Location pos2 = null;

    private String portalName = null;

    // TODO store destinations also as variables like portals
    private String[] destinations = new String[] {}; // Could possibly store the destination name to stop the server having to read the config file

    private String bungee = null; // Could possibly store the bungee server name to stop the server having to read the config file

    // Bungee will be stored inside the destination.

    private PortalArg[] portalArgs = null;

    public HashSet<UUID> inPortal = new HashSet<UUID>();

    public AdvancedPortal(String portalName, Material trigger, String destination, Location pos1, Location pos2, PortalArg... portalArgs) {
        this(portalName, trigger, new String[] { destination }, pos1, pos2, portalArgs);
    }

    // TODO think of relaying out the data input to a more logical format.
    public AdvancedPortal(String portalName, Material trigger, String[] destinations, Location pos1, Location pos2, PortalArg... portalArgs) {
        this(portalName, trigger, pos1, pos2, pos2.getWorld().getName(), portalArgs);
        this.destinations = destinations;
    }

    public AdvancedPortal(String portalName, Material trigger, Location pos1, Location pos2, PortalArg... portalArgs) {
        this(portalName, trigger, pos1, pos2, pos2.getWorld().getName(), portalArgs);
    }

    public AdvancedPortal(String portalName, Material trigger, String destination, Location pos1, Location pos2, String worldName, PortalArg... portalArgs) {
        this(portalName, trigger, new String[] { destination }, pos1, pos2, worldName, portalArgs);
    }

    public AdvancedPortal(String portalName, Material trigger, String[] destinations, Location pos1, Location pos2, String worldName, PortalArg... portalArgs) {
        this(portalName, trigger, pos1, pos2, worldName, portalArgs);
        this.destinations = destinations;
    }

    public AdvancedPortal(String portalName, Material trigger, Location pos1, Location pos2, String worldName, PortalArg... portalArgs) {
        this(portalName, new HashSet<>(Collections.singletonList(trigger)), pos1, pos2, worldName, portalArgs);
    }

    public AdvancedPortal(String portalName, Set<Material> triggers, Location pos1, Location pos2, String worldName, PortalArg... portalArgs) {
        this.portalName = portalName;
        this.triggers = triggers;
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

    public Set<Material> getTriggers() {
        return this.triggers;
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

    @Deprecated
    public String getDestiation() {
        if(this.destinations.length == 0) {
            // backwards compatibility
            return null;
        }
        return String.join(",", this.destinations);
    }

    public String[] getDestinations() {
        return this.destinations;
    }

    @Deprecated
    public void setDestiation(String destiation) {
        if(destiation == null) {
            // backwards compatibility
            this.destinations = new String[] {};
            return;
        }

        this.destinations = destiation.split(",");
    }

    public void setDestinations(String[] destinations) {
        this.destinations = destinations;
    }

    public String getBungee() {
        return this.bungee;
    }

    public void setBungee(String bungee) {
        this.bungee = bungee;
    }

    public boolean isDelayed() {
        return this.hasArg("delayed") && this.getArg("delayed").equalsIgnoreCase("true");
    }
}
