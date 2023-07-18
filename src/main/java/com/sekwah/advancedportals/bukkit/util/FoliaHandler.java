package com.sekwah.advancedportals.bukkit.util;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.listeners.Listeners;

import java.util.concurrent.TimeUnit;

/**
 * This is to stop jars such as spigot complaining about folia imports
 */
public class FoliaHandler {
    public static void repeatingTask(AdvancedPortalsPlugin plugin, Runnable runnable, int seconds) {
        plugin.getServer().getAsyncScheduler().runAtFixedRate(plugin, (task) -> {
            runnable.run();
        }, seconds, seconds, TimeUnit.SECONDS);
    }
}
