package com.sekwah.advancedportals.core.entities;

import com.google.gson.annotations.SerializedName;

public class PlayerLocation {

    @SerializedName("x")
    public final double posX;
    @SerializedName("y")
    public final double posY;
    @SerializedName("z")
    public final double posZ;
    @SerializedName("w")
    public final String worldName;
    @SerializedName("yaw")
    public final float yaw;
    @SerializedName("p")
    public final float pitch;

    public PlayerLocation(String worldName, double posX, double posY, double posZ) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = 0;
        this.pitch = 0;
    }

    public PlayerLocation(String worldName, double posX, double posY, double posZ, float yaw, float pitch) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
