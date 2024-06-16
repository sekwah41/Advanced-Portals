package com.sekwah.advancedportals.spigot.reflection;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Just a util class to force spigot to allow us to have fun with the minecraft: channel category
 *
 * <p>Atm at least this is just designed to be able to access debug/ for showing visuals of the
 * portals and such
 */
public class MinecraftCustomPayload {

    public static boolean sendCustomPayload(Player player, String channel, byte[] data) {
        try {
            // Access the MinecraftKey class using reflection
            Class<?> minecraftKeyClass = Class.forName("net.minecraft.resources.MinecraftKey");
            Constructor<?> minecraftKeyConstructor = minecraftKeyClass.getConstructor(String.class);

            // Create an instance of MinecraftKey with the channel name
            Object minecraftKey = minecraftKeyConstructor.newInstance(channel);

            // Access the sendCustomPayload method in the CraftPlayer class
            Method sendCustomPayloadMethod =
                    player.getClass()
                            .getDeclaredMethod(
                                    "sendCustomPayload", minecraftKeyClass, byte[].class);

            // Make the private method accessible
            sendCustomPayloadMethod.setAccessible(true);

            // Invoke the sendCustomPayload method with the MinecraftKey and data
            sendCustomPayloadMethod.invoke(player, minecraftKey, data);

            return true; // Successfully sent the custom payload
        } catch (Exception e) {
            // Catch any reflection-related errors
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
}
