package com.sekwah.advancedportals.bungee.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class EventListener implements Listener {
    private AdvancedPortalsPlugin plugin;

    public EventListener(AdvancedPortalsPlugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerSwitchServer(ServerSwitchEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        if (plugin.PlayerDestiMap.containsKey(uuid)) {
            String[] val = plugin.PlayerDestiMap.get(uuid);

            // key: UUID (string)
            // value: [0] targetServer, [1] targetDestination

            if (event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase(val[0])) {

                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("BungeePortal");
                out.writeUTF(val[1]);

                event.getPlayer().sendData(plugin.channelName, out.toByteArray());
            }
        }
    }
}
