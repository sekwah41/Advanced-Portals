package com.sekwah.advancedportals.core.data;

import com.google.gson.annotations.SerializedName;

public class PortalLocation {

    @SerializedName("x")
    public final int posX;

    @SerializedName("y")
    public final int posY;

    @SerializedName("z")
    public final int posZ;

    @SerializedName("w")
    public final String worldName;

    public PortalLocation(String worldName, int posX, int posY, int posZ) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
}
