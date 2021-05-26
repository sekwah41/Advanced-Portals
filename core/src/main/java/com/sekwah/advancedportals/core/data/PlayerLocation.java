package com.sekwah.advancedportals.core.data;

import com.google.gson.annotations.SerializedName;

public class PlayerLocation extends WorldLocation {

    @SerializedName("yaw")
    private final float yaw;

    @SerializedName("p")
    private final float pitch;

    public PlayerLocation(String worldName, double posX, double posY, double posZ) {
        super(worldName, posX, posY, posZ);
        this.yaw = 0;
        this.pitch = 0;
    }

    public PlayerLocation(String worldName, double posX, double posY, double posZ, float yaw, float pitch) {
        super(worldName, posX, posY, posZ);
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
