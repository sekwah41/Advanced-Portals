package com.sekwah.advancedportals.bukkit.injector;

import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.reflection.ReflectionHelper;
import io.netty.channel.Channel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PacketInjector {

    private Method getPlayerHandle;
    private Field playerConnection;
    private Field networkManager;
    private Field networkChannel;

    public PacketInjector(AdvancedPortalsPlugin plugin, String bukkitImpl) {
            String craftBukkitPackage = "org.bukkit.craftbukkit." + bukkitImpl + ".";
            String minecraftPackage = "net.minecraft.server." + bukkitImpl + ".";

        try {
            getPlayerHandle = Class.forName(craftBukkitPackage + "entity.CraftPlayer").getMethod("getHandle");
            playerConnection = Class.forName(minecraftPackage + "EntityPlayer").getField("playerConnection");
            networkManager = Class.forName(minecraftPackage + "PlayerConnection").getField("networkManager");
            networkChannel = ReflectionHelper.getFieldByType(Class.forName(minecraftPackage + "NetworkManager"), Channel.class, true);

        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
