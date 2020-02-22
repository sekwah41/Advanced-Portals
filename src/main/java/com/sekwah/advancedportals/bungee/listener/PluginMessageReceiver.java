package com.sekwah.advancedportals.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
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
            String targetPlayerUUID = in.readUTF();
            String targetDestination = in.readUTF();

            plugin.PlayerDestiMap.put(targetPlayerUUID, new String[]{targetServer, targetDestination});

            plugin.getProxy().getScheduler().schedule(plugin, () -> {
                if (plugin.PlayerDestiMap.containsKey(targetPlayerUUID)) {
                    plugin.PlayerDestiMap.remove(targetPlayerUUID);
                }
            }, 20, TimeUnit.SECONDS);
        }
    }
}
