package com.sekwah.advancedportals.compat;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by on 02/08/2016.
 *
 * I don't think there will be any others supported other than bukkit but if there are its not just the compat that will
 * need to change.
 *
 * @author sekwah41
 */
public class CraftBukkit {

    private final String craftBukkitPackage;

    private final String minecraftPackage;

    private final AdvancedPortalsPlugin plugin;

    private Method chatMessageMethod;

    private Class<?> craftPlayer;

    private Class<?> chatPacket;

    private Class<?> chatBaseComponent;

    private Class<?> packet;


    // Classes so it doesnt keep fetching them.

    public CraftBukkit(AdvancedPortalsPlugin plugin, String craftBukkitVer) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {

        this.craftBukkitPackage = "org.bukkit.craftbukkit." + craftBukkitVer + ".";

        this.minecraftPackage = "net.minecraft.server." + craftBukkitVer + ".";

        this.plugin = plugin;

        this.setupCompat();
    }

    private void setupCompat() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {

        this.craftPlayer = Class.forName(craftBukkitPackage + "entity.CraftPlayer");

        this.chatBaseComponent = Class.forName(minecraftPackage + "IChatBaseComponent");

        this.chatMessageMethod = this.findClass(chatBaseComponent, "ChatSerializer").getMethod("a", String.class);

        this.chatPacket = Class.forName(minecraftPackage + "PacketPlayOutChat");

        this.packet = Class.forName(minecraftPackage + "Packet");

    }

    public void sendRawMessage(String rawMessage, Player player) {
        this.sendMessage(rawMessage,player, (byte) 1);
    }

    public void sendActionBarMessage(String rawMessage, Player player) {
        this.sendMessage(rawMessage,player, (byte) 2);
    }

    public void sendMessage(String rawMessage, Player player, byte msgType) {
        try {
            Object comp = this.chatMessageMethod.invoke(null,rawMessage);

            Object handle = this.craftPlayer.getMethod("getHandle").invoke(player);

            Field playerConnectionObj = handle.getClass().getDeclaredField("playerConnection");

            Constructor<?> packetConstructor = this.chatPacket.getConstructor(this.chatBaseComponent, byte.class);

            Object packet = packetConstructor.newInstance(comp, msgType);

            Object playerConnection = playerConnectionObj.get(handle);

            playerConnection.getClass().getMethod("sendPacket", this.packet).invoke(playerConnection, packet);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException
                | InstantiationException e) {
            this.plugin.getLogger().warning("Error creating raw message, something must be wrong with reflection");
            e.printStackTrace();
        }

        /*IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(rawMessage);
        // "json message", position(0: chat (chat box), 1: system message (chat box), 2: above action bar)
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);*/
    }


    public Class<?> findClass(Class<?> classObj, String className){
        for(Class<?> classes : classObj.getDeclaredClasses()){
            if(classes.getSimpleName().equals(className)){
                return classes;
            }
        }
        return null;
    }


}
