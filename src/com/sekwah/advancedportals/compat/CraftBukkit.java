package com.sekwah.advancedportals.compat;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by on 02/08/2016.
 *
 * I don't think there will be any others supported other than bukkit but if there are its not just the compat that will
 * need to change.
 *
 * @author sekwah41
 */
public class CraftBukkit {

    private final String craftBukkitVer;

    private final String craftBukkitPackage;

    private final String minecraftPackage;


    // Classes so it doesnt keep fetching them.
    private Class<?> chatBaseComponent;
    private Class<?> chatSerializer;

    public CraftBukkit(String craftBukkitVer) throws ClassNotFoundException, NoSuchFieldException {
        this.craftBukkitVer = craftBukkitVer;
        this.craftBukkitPackage = "org.bukkit.craftbukkit." + craftBukkitVer;
        this.minecraftPackage = "net.minecraft.server." + craftBukkitVer;


        this.setupCompat();
    }

    private void setupCompat() throws ClassNotFoundException, NoSuchFieldException {
        this.chatBaseComponent = Class.forName(minecraftPackage + "IChatBaseComponent");
        Field modfield = chatBaseComponent.getDeclaredField("modifiers");
        chatBaseComponent.getDeclaredClasses();

    }

    // Convert to reflection
    public void sendRawMessage(String rawMessage, Player player) {

        try {

            Class<?> nmsClass = Class.forName(minecraftPackage + "IChatBaseComponent");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(rawMessage);
        // "json message", position(0: chat (chat box), 1: system message (chat box), 2: above action bar)
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);*/
    }

    public void sendActionBarMessage(String rawMessage, Player player) {
        /*IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(rawMessage);
        // "json message", position(0: chat (chat box), 1: system message (chat box), 2: above action bar)
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);*/
    }


}
