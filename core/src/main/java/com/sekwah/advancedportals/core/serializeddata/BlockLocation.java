package com.sekwah.advancedportals.core.serializeddata;

import com.google.gson.annotations.SerializedName;
import com.sekwah.advancedportals.core.data.Direction;

public class BlockLocation {

    @SerializedName("x")
    public final int posX;

    @SerializedName("y")
    public final int posY;

    @SerializedName("z")
    public final int posZ;

    @SerializedName("w")
    public final String worldName;

    public BlockLocation(String worldName, int posX, int posY, int posZ) {
        this.worldName = worldName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public BlockLocation(BlockLocation location, Direction direction) {
        this.worldName = location.worldName;
        this.posX = location.posX + direction.x;
        this.posY = location.posY + direction.y;
        this.posZ = location.posZ + direction.z;

    }
}
