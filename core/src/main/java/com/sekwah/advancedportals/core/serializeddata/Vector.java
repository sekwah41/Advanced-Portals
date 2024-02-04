package com.sekwah.advancedportals.core.serializeddata;

import com.google.gson.annotations.SerializedName;

public class Vector {
    @SerializedName("x")
    public final double X;

    @SerializedName("y")
    public final double Y;

    @SerializedName("z")
    public final double Z;

    public Vector(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public Vector add(Vector vec) {
        return new Vector(this.X + vec.X, this.Y + vec.Y, this.Z + vec.Z);
    }

    public Vector multiply(double value) {
        return new Vector(this.X * value, this.Y * value, this.Z * value);
    }

    public Vector setY(double y) {
        return new Vector(this.X, y, this.Z);
    }

    public double distanceTo(Vector pos) {
        return Math.sqrt(this.distanceToSq(pos));
    }

    private double distanceToSq(Vector pos) {
        double dx = this.X - pos.X;
        double dy = this.Y - pos.Y;
        double dz = this.Z - pos.Z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double getX() {
        return this.X;
    }

    public double getY() {
        return this.Y;
    }

    public double getZ() {
        return this.Z;
    }

    public Vector normalize() {
        return this.multiply(1.0D / this.length());
    }

    private double length() {
        return Math.sqrt(this.X * this.X + this.Y * this.Y + this.Z * this.Z);
    }
}
