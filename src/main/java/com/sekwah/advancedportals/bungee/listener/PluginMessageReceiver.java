package com.sekwah.advancedportals.bungee.listener;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bungee.BungeeMessages;
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

        if (subChannel.equalsIgnoreCase(BungeeMessages.ENTER_PORTAL)) {
            String targetServer = in.readUTF();
            String targetDestination = in.readUTF();
            String targetUUID = in.readUTF();
            String targetName = in.readUTF();

            String bungeeUUID; // If the bungee is offline mode it will be an offline uuid

            String offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + targetName).getBytes(Charsets.UTF_8)).toString();

            if ( event.getReceiver() instanceof ProxiedPlayer )
            {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();
                bungeeUUID = receiver.getUniqueId().toString();
                if(!targetUUID.equals(bungeeUUID)) {
                    Server connection = (Server) event.getSender();
                    plugin.getLogger().warning(BungeeMessages.WARNING_MESSAGE
                            + "\n\nThe server the player was sent from is the offending server.\n" +
                            "Server Name: " + connection.getInfo().getName());
                }
                targetUUID = bungeeUUID;
            }
            else {
                plugin.getLogger().warning("There has been an issue getting the player for the teleport request.");
                return;
            }
            plugin.PlayerDestiMap.put(targetUUID, new String[]{targetServer, targetDestination, targetUUID, offlineUUID});

            String finalTargetUUID = targetUUID;
            plugin.getProxy().getScheduler().schedule(plugin, () -> {
                plugin.PlayerDestiMap.remove(finalTargetUUID);
            }, 20, TimeUnit.SECONDS);
        }
        else if (subChannel.equalsIgnoreCase(BungeeMessages.BUNGEE_COMMAND)) {
            String command = in.readUTF();
            ProxiedPlayer player = (ProxiedPlayer) event.getReceiver();
            if (player != null) {
                // To send command to server the player is currently on in a lazy way
               //player.chat("/" + command);
                plugin.getProxy().getPluginManager().dispatchCommand(player, command);
            }
        }
    }
}
