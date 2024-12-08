package com.sekwah.advancedportals.core.serializeddata;

public class Vector {
    protected final double x;

    protected final double y;

    protected final double z;

    public Vector(double X, double Y, double Z) {
        this.x = X;
        this.y = Y;
        this.z = Z;
    }

    public Vector add(Vector vec) {
        return new Vector(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    public Vector multiply(double value) {
        return new Vector(this.x * value, this.y * value, this.z * value);
    }

    public Vector setY(double y) {
        return new Vector(this.x, y, this.z);
    }

    public double distanceTo(Vector pos) {
        return Math.sqrt(this.distanceToSq(pos));
    }

    private double distanceToSq(Vector pos) {
        double dx = this.x - pos.x;
        double dy = this.y - pos.y;
        double dz = this.z - pos.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Vector normalize() {
        return this.multiply(1.0D / this.length());
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector subtract(Vector vec) {
        return new Vector(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }
}
