package com.sekwah.advancedportals.bungee.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;
import java.util.Set;

public class EventListener implements Listener {
    private AdvancedPortalsPlugin plugin;

    public EventListener(AdvancedPortalsPlugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();

        String[] val = plugin.PlayerDestiMap.get(uuid);

        if (val != null) {

            // key: UUID (string)
            // value: [0] targetServer, [1] targetDestination, [2] onlineUUID

            if (event.getServer().getInfo().getName().equalsIgnoreCase(val[0])) {

                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF(BungeeMessages.SERVER_DESTI);
                out.writeUTF(val[1]);
                out.writeUTF(val[2]);

                event.getServer().sendData(plugin.channelName, out.toByteArray());
            }
        }
    }
}
