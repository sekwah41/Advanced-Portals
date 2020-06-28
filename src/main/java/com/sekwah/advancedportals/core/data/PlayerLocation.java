package com.sekwah.advancedportals.core.data;

import com.google.gson.annotations.SerializedName;

public class PlayerLocation {


    @SerializedName("x")
    private final double posX;

    @SerializedName("y")
    private final double posY;

    @SerializedName("z")
    private final double posZ;

    @SerializedName("w")
    private final String worldName;

    @SerializedName("yaw")
    private final float yaw;

    @SerializedName("p")
    private final float pitch;

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

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public String getWorldName() {
        return worldName;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
