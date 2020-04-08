package com.sekwah.advancedportals.bukkit.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import org.bukkit.OfflinePlayer;
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

        if (subchannel.equals("BungeePortal")) {
            String targetPlayerUUID = in.readUTF();
            String targetDestination = in.readUTF();

            Player msgPlayer = plugin.getServer().getPlayer(UUID.fromString(targetPlayerUUID));

            if (msgPlayer != null) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                        () -> Destination.warp(msgPlayer, targetDestination, false, true),
                        20L
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
