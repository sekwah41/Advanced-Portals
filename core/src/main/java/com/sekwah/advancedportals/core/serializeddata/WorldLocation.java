package com.sekwah.advancedportals.core.serializeddata;

public class WorldLocation extends Vector {

    public final String worldName;

    public WorldLocation(String worldName, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.worldName = worldName;
    }

    public BlockLocation toBlockPos() {
        return new BlockLocation(
                this.worldName,
                (int) Math.floor(this.x),
                (int) Math.floor(this.y),
                (int) Math.floor(this.z));
    }
}
