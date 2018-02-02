package com.sekwah.advancedportals.core.data;

public class PortalLocation {

    public final int posX;
    public final int posY;
    public final int posZ;
    public final String worldName;

    public PortalLocation(String worldName, int posX, int posY, int posZ) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
}
