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
}
