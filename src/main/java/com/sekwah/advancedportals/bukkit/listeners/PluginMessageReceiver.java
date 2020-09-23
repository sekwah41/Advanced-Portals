package com.sekwah.advancedportals.bukkit.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class PluginMessageReceiver implements PluginMessageListener {

    private AdvancedPortalsPlugin plugin;

    public PluginMessageReceiver(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if (!channel.equals(plugin.channelName)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals(BungeeMessages.SERVER_DESTI)) {
            String targetDestination = in.readUTF();
            String bungeeUUID = in.readUTF();

            Player targetPlayer = this.plugin.getServer().getPlayer(UUID.fromString(bungeeUUID));

            if (targetPlayer != null) {
                Destination.warp(targetPlayer, targetDestination, false, true);

            }
            else {
                plugin.PlayerDestiMap.put(bungeeUUID, targetDestination);

                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                                plugin.PlayerDestiMap.remove(bungeeUUID),
                        20L * 10
                );
            }

        }
    }

    /**
     * Example forward packet.
     *
     * Construct like the forge packets.
     *
     * out.writeUTF("Forward"); // So BungeeCord knows to forward it
     out.writeUTF("ALL");
     out.writeUTF("MyChannel"); // The channel name to check if this your data

     ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
     DataOutputStream msgout = new DataOutputStream(msgbytes);
     msgout.writeUTF("Some kind of data here"); // You can do anything you want with msgout
     msgout.writeShort(123);

     out.writeShort(msgbytes.toByteArray().length);
     out.write(msgbytes.toByteArray());
     *
     */
}
