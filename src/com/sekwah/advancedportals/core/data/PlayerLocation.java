package com.sekwah.advancedportals.core.data;

public class PlayerLocation {

    public final double posX;
    public final double posY;
    public final double posZ;
    public final String worldName;

    public PlayerLocation(String worldName, double posX, double posY, double posZ) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
}
