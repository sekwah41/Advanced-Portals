package com.sekwah.advancedportals.spigot.reflection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;

/**
 * Just a util class to force spigot to allow us to have fun with the
 * minecraft: channel category
 *
 * <p>Atm at least this is just designed to be able to access debug/ for
 * showing visuals of the portals and such
 */
public class MinecraftCustomPayload {

    public static Field playerConnectionField = null;

    private static Field getFieldFromType(String fieldType, Object obj) throws IllegalAccessException, NoSuchFieldException {
        for (var field : obj.getClass().getDeclaredFields()) {
            if (field.getType().getName().equals(fieldType)) {
                field.setAccessible(true);
                return field;
            }
        }
        throw new NoSuchFieldException("Field with type " + fieldType + " not found in object");
    }

    private static void outputAllMethodsToFile(Class clazz) throws IOException {
        var pluginInstance = AdvancedPortalsPlugin.getInstance();
        var dataFolder = pluginInstance.getDataFolder();

        // Create a "debug" folder inside the plugin's data folder
        File debugFolder = new File(dataFolder, "debug");
        if (!debugFolder.exists() && !debugFolder.mkdirs()) {
            pluginInstance.getLogger().severe("Failed to create debug folder!");
            return;
        }

        // Name the file after the class name
        String fileName = clazz.getSimpleName() + ".txt";
        File outputFile = new File(debugFolder, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Output the class name
            writer.write("Class: " + clazz.getName());
            writer.newLine();

            // Output constructors and their parameter types
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                writer.write("Constructor: " + constructor.getName());
                writer.newLine();
                for (Class<?> parameterType : constructor.getParameterTypes()) {
                    writer.write("  Parameter: " + parameterType.getName());
                    writer.newLine();
                }
            }

            // Output each method and its parameter types
            for (Method method : clazz.getDeclaredMethods()) {
                writer.write("Method: " + method.getName());
                writer.newLine();
                for (Class<?> parameterType : method.getParameterTypes()) {
                    writer.write("  Parameter: " + parameterType.getName());
                    writer.newLine();
                }
            }

            // Also output all of the fields
            for (var field : clazz.getDeclaredFields()) {
                writer.write("Field: " + field.getName());
                writer.write("  Type: " + field.getType().getName());
                writer.newLine();
            }
        } catch (IOException e) {
            pluginInstance.getLogger().severe("Failed to write methods to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void outputAllMethodsToFile(Object obj) throws IOException {
        outputAllMethodsToFile(obj.getClass());
    }

    public static boolean sendCustomPayload(Player player, String channel,
                                            byte[] data) {
        try {
            // Access the MinecraftKey class using reflection
            Class<?> minecraftKeyClass =
                Class.forName("net.minecraft.resources.MinecraftKey");
            Constructor<?> minecraftKeyConstructor =
                minecraftKeyClass.getConstructor(String.class);
            // Create an instance of MinecraftKey with the channel name
            Object minecraftKey = minecraftKeyConstructor.newInstance(channel);

            /*

            // Call and get the value from player.getClass() getHandle
            Method getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
            Object playerHandle = getHandleMethod.invoke(player);

            outputAllMethodsToFile(playerHandle);

            if(playerConnectionField == null) {
                playerConnectionField = getFieldFromType("net.minecraft.server.network.PlayerConnection", playerHandle);
            }
            // Get the value of field with type net.minecraft.server.network.PlayerConnection
            Object playerConnection = playerConnectionField.get(playerHandle);

            Class<?> customPacketPayload =
                    Class.forName("net.minecraft.network.protocol.game.PacketPlayInCustomPayload");

            Class<?> packetDataSerializer =
                    Class.forName("net.minecraft.network.PacketDataSerializer");

            outputAllMethodsToFile(customPacketPayload);
            outputAllMethodsToFile(packetDataSerializer);

            var unpooledData = Unpooled.wrappedBuffer(data);
            var packetData = packetDataSerializer.getConstructor(ByteBuf.class).newInstance(unpooledData);

            Constructor<?> customPacketPayloadConstructor = customPacketPayload.getConstructor(minecraftKeyClass, packetDataSerializer);

            Object customPayload = customPacketPayloadConstructor.newInstance(minecraftKey, packetData);

            // Get method with name a and parameter types PacketPlayInCustomPayload
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("a", customPacketPayload);

            // Send the packet on this method
            sendPacketMethod.invoke(playerConnection, customPayload);*/

            // Access the sendCustomPayload method in the CraftPlayer class
            Method sendCustomPayloadMethod =
                player.getClass().getDeclaredMethod(
                    "sendCustomPayload", minecraftKeyClass, byte[].class);

            // Make the private method accessible
            sendCustomPayloadMethod.setAccessible(true);

            // Invoke the sendCustomPayload method with the MinecraftKey and
            // data
            sendCustomPayloadMethod.invoke(player, minecraftKey, data);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
