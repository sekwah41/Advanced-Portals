package com.sekwah.advancedportals.core.serializeddata;

import com.google.gson.annotations.SerializedName;

public class WorldLocation {

    @SerializedName("x")
    public final double posX;

    @SerializedName("y")
    public final double posY;

    @SerializedName("z")
    public final double posZ;

    @SerializedName("w")
    public final String worldName;

    public WorldLocation(String worldName, double posX, double posY, double posZ) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public double distanceTo(WorldLocation pos) {
        return Math.sqrt(this.distanceToSq(pos));
    }

    public double distanceToSq(WorldLocation pos) {
        double dx = this.posX - pos.posX;
        double dy = this.posY - pos.posY;
        double dz = this.posZ - pos.posZ;
        return dx * dx + dy * dy + dz * dz;
    }

    public BlockLocation toBlockPos() {
        return new BlockLocation(this.worldName, (int) Math.floor(this.posX), (int) Math.floor(this.posY), (int) Math.floor(this.posZ));
    }
}
