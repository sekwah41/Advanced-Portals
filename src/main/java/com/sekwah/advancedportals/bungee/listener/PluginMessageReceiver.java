package com.sekwah.advancedportals.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PluginMessageReceiver implements Listener {
    private AdvancedPortalsPlugin plugin;

    public PluginMessageReceiver(AdvancedPortalsPlugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onMessageReceived(PluginMessageEvent event) {
        if(!event.getTag().equalsIgnoreCase(plugin.channelName)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase("PortalEnter")) {
            String targetServer = in.readUTF();
            String targetDestination = in.readUTF();
            UUID uuid;

            if ( event.getReceiver() instanceof ProxiedPlayer )
            {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();
                uuid = receiver.getUniqueId();
            }
            else {
                plugin.getLogger().warning("There has been an issue getting the player for the teleport request.");
                return;
            }
            plugin.PlayerDestiMap.put(uuid, new String[]{targetServer, targetDestination});

            plugin.getProxy().getScheduler().schedule(plugin, () -> {
                plugin.PlayerDestiMap.remove(uuid);
            }, 20, TimeUnit.SECONDS);
        }
        else if (subChannel.equalsIgnoreCase("BungeeCommand")) {
            String targetPlayerUUID = in.readUTF();
            String command = in.readUTF();
            ProxiedPlayer player = plugin.getProxy().getPlayer(UUID.fromString(targetPlayerUUID));
            if (player != null) {
                // To send command to server the player is currently on
               //player.chat("/" + command);
                plugin.getProxy().getPluginManager().dispatchCommand(player, command);
            }
        }
    }
}
