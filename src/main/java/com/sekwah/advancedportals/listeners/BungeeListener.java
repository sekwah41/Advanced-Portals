package com.sekwah.advancedportals.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created by on 29/03/2016.
 *
 * @author sekwah41
 */
public class BungeeListener implements PluginMessageListener {

    private AdvancedPortalsPlugin plugin;

    public BungeeListener(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("AdvancedPortals")) {
            // Any data after this is read like the packets used in the naruto mod
            // (same order as sent)
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
