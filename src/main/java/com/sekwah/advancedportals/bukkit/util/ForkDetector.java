package com.sekwah.advancedportals.bukkit.util;

public class ForkDetector {

    public static boolean isSpigot() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.AsyncScheduler");
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

}
