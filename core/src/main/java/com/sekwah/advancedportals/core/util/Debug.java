package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.util.FriendlyDataOutput;

import java.awt.*;

public class Debug {
    public static boolean addMarker(PlayerContainer player, BlockLocation blockPos, String name, Color color, int milliseconds) {
        FriendlyDataOutput out = new FriendlyDataOutput();
        out.writeBlock(blockPos);
        out.writeInt(color(color));
        out.writeUtf(name);
        out.writeInt(milliseconds);
        return player.sendPacket("minecraft:debug/game_test_add_marker", out.toByteArray());
    }

    public static boolean clear(PlayerContainer player) {
        FriendlyDataOutput out = new FriendlyDataOutput();
        return player.sendPacket("minecraft:debug/game_test_clear", out.toByteArray());
    }

    public static int color(Color color) {
        return color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    public static int color(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public float getR(int color) {
        return (float)(color >> 16 & 255) / 255.0F;
    }

    public float getG(int color) {
        return (float)(color >> 8 & 255) / 255.0F;
    }

    public float getB(int color) {
        return (float)(color & 255) / 255.0F;
    }

    public float getA(int color) {
        return (float)(color >> 24 & 255) / 255.0F;
    }
}
