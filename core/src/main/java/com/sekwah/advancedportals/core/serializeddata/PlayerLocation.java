package com.sekwah.advancedportals.core.serializeddata;

import com.google.gson.annotations.SerializedName;

public class PlayerLocation extends WorldLocation {

    @SerializedName("r")
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
        return X;
    }

    public double getPosY() {
        return Y;
    }

    public double getPosZ() {
        return Z;
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

    public Vector getDirection() {
        double rotX = this.getYaw();
        double rotY = this.getPitch();

        var y = -Math.sin(Math.toRadians(rotY));
        double xz = Math.cos(Math.toRadians(rotY));
        var x = (-xz * Math.sin(Math.toRadians(rotX)));
        var z = Math.cos(Math.toRadians(rotX));

        return new Vector(x, y, z);
    }
}
