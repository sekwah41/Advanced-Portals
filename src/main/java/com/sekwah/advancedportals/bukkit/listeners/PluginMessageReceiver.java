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
        // plugin.getLogger().info(""+channel.equals(plugin.channelName));

        if (!channel.equals(plugin.channelName)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        // plugin.getLogger().info("bukkit plugin received: " + subchannel);

        if (subchannel.equals("BungeePortal")) {
            String targetPlayerUUID = in.readUTF();
            String targetDestination = in.readUTF();

            OfflinePlayer msgPlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(targetPlayerUUID));

            Destination.warp(msgPlayer.getPlayer(), targetDestination);

            /* plugin.PlayerDestiMap.put(msgPlayer, targetDestination);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                plugin.PlayerDestiMap.remove(msgPlayer),
                20L*10
            ); */
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
