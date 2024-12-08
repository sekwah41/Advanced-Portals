package com.sekwah.advancedportals.core.serializeddata;

import com.sekwah.advancedportals.core.data.Direction;

public class BlockLocation {
    // These should be treated as final, they only are not for serialization
    // purposes
    private final int posX;

    private final int posY;

    private final int posZ;

    private final String worldName;

    public BlockLocation() {
        this.worldName = "";
        this.posX = 0;
        this.posY = 0;
        this.posZ = 0;
    }

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

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean equals(BlockLocation location) {
        return location.posX == this.posX && location.posY == this.posY
            && location.posZ == this.posZ
            && location.worldName.equals(this.worldName);
    }

    public double distanceTo(BlockLocation pos) {
        return Math.sqrt(this.distanceToSq(pos));
    }

    public double distanceToSq(BlockLocation pos) {
        double dx = this.posX - pos.posX;
        double dy = this.posY - pos.posY;
        double dz = this.posZ - pos.posZ;
        return dx * dx + dy * dy + dz * dz;
    }

    public BlockLocation addY(double offsetY) {
        return this.addY((int) Math.floor(offsetY));
    }

    public BlockLocation addY(int offsetY) {
        return new BlockLocation(this.worldName, this.posX,
                                 (this.posY + offsetY), this.posZ);
    }

    public int getSize(BlockLocation pos2) {
        int minX = Math.min(this.getPosX(), pos2.getPosX());
        int minY = Math.min(this.getPosY(), pos2.getPosY());
        int minZ = Math.min(this.getPosZ(), pos2.getPosZ());

        int maxX = Math.max(this.getPosX(), pos2.getPosX());
        int maxY = Math.max(this.getPosY(), pos2.getPosY());
        int maxZ = Math.max(this.getPosZ(), pos2.getPosZ());

        int widthX = maxX - minX + 1;
        int widthY = maxY - minY + 1;
        int widthZ = maxZ - minZ + 1;

        return widthX * widthY * widthZ;
    }

    public Vector toVector() {
        return new Vector(this.posX, this.posY, this.posZ);
    }

    public WorldLocation toWorldLocation() {
        return new WorldLocation(this.worldName, this.posX, this.posY, this.posZ);
    }

    public BlockLocation add(int x, int y, int z) {
        return new BlockLocation(this.worldName, this.posX + x, this.posY + y,
                                 this.posZ + z);
    }
}
