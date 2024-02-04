package com.sekwah.advancedportals.core.serializeddata;

import com.google.gson.annotations.SerializedName;

public class WorldLocation extends Vector {

    @SerializedName("w")
    public final String worldName;

    public WorldLocation(String worldName, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.worldName = worldName;
    }

    public BlockLocation toBlockPos() {
        return new BlockLocation(this.worldName, (int) Math.floor(this.X), (int) Math.floor(this.Y), (int) Math.floor(this.Z));
    }
}
