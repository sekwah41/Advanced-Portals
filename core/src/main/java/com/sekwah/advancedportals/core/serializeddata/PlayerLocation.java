package com.sekwah.advancedportals.core.serializeddata;

public class PlayerLocation extends WorldLocation {
    private final float yaw;

    private final float pitch;

    public PlayerLocation() {
        super("", 0, 0, 0);
        this.yaw = 0;
        this.pitch = 0;
    }

    public PlayerLocation(String worldName, double posX, double posY,
                          double posZ) {
        super(worldName, posX, posY, posZ);
        this.yaw = 0;
        this.pitch = 0;
    }

    public PlayerLocation(String worldName, double posX, double posY,
                          double posZ, float yaw, float pitch) {
        super(worldName, posX, posY, posZ);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getPosX() {
        return x;
    }

    public double getPosY() {
        return y;
    }

    public double getPosZ() {
        return z;
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

        double y = -Math.sin(Math.toRadians(rotY));
        double xz = Math.cos(Math.toRadians(rotY));
        double x = (-xz * Math.sin(Math.toRadians(rotX)));
        double z = Math.cos(Math.toRadians(rotX));

        return new Vector(x, y, z);
    }
}
