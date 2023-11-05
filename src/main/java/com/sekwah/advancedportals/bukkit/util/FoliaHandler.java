package com.sekwah.advancedportals.bukkit.util;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * This is to stop jars such as spigot complaining about folia imports
 */
public class FoliaHandler {
    public static void repeatingTask(AdvancedPortalsPlugin plugin, Runnable runnable, int ticks) {
        plugin.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, (task) -> {
            runnable.run();
        }, ticks, ticks);
    }

    public static void scheduleTask(AdvancedPortalsPlugin plugin, Runnable runnable, int ticks) {
        plugin.getServer().getGlobalRegionScheduler().runDelayed(plugin, (task) -> {
            runnable.run();
        }, ticks);
    }

    public static void scheduleAsyncTask(Plugin plugin, Runnable runnable, int ticks) {
        plugin.getServer().getAsyncScheduler().runDelayed(plugin, (task) -> {
            runnable.run();
        }, ticks * 1000L / 20L, TimeUnit.MILLISECONDS);
    }

    public static void runAsyncTask(Plugin plugin, Runnable runnable) {
        plugin.getServer().getAsyncScheduler().runNow(plugin, (task) -> {
            runnable.run();
        });
    }


    /**
     * Will run if the entity isn't destroyed, if you want to run something different in that case also supply a retired runnable.
     * @param plugin
     * @param entity
     * @param runnable
     * @param ticks
     */
    public static void scheduleEntityTask(Plugin plugin, Entity entity, Runnable runnable, int ticks) {
        scheduleEntityTask(plugin, entity, runnable, null, ticks);
    }
    public static void scheduleEntityTask(Plugin plugin, Entity entity, Runnable runnable, Runnable retired, int ticks) {
        entity.getScheduler().runDelayed(plugin, (task) -> {
            runnable.run();
        }, retired, ticks);
    }
}
