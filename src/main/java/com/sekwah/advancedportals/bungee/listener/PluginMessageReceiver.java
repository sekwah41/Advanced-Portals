package com.sekwah.advancedportals.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PluginMessageReceiver implements Listener {
    private final AdvancedPortalsPlugin plugin;

    public PluginMessageReceiver(AdvancedPortalsPlugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onMessageReceived(PluginMessageEvent event) {
        if(!event.getTag().equalsIgnoreCase(BungeeMessages.CHANNEL_NAME) || !(event.getSender() instanceof Server)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase(BungeeMessages.ENTER_PORTAL)) {
            String targetServer = in.readUTF();
            String targetDestination = in.readUTF();
            String targetUUID = in.readUTF();

            plugin.PlayerDestiMap.put(targetUUID, new String[]{targetServer, targetDestination, targetUUID});

            plugin.getProxy().getScheduler().schedule(plugin, () -> plugin.PlayerDestiMap.remove(targetUUID),
                    10, TimeUnit.SECONDS);
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

        event.setCancelled(true);
    }
}
