package com.sekwah.advancedportals.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bungee.AdvancedPortalsPlugin;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageReceiver implements Listener {
    private AdvancedPortalsPlugin plugin;

    public PluginMessageReceiver (AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMessageReceived(PluginMessageEvent event) {
        if(!event.getTag().equalsIgnoreCase("AdvancedPortals")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase("PortalEnter")) {
            String targetServer = in.readUTF();
            String targetPlayerUUID = in.readUTF();
            String targetDestination = in.readUTF();

            ServerInfo server = plugin.getProxy().getServerInfo(targetServer);

            if (server != null) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("BungeePortal");
                out.writeUTF(targetPlayerUUID);
                out.writeUTF(targetDestination);

                server.sendData("AdvancedPortals", out.toByteArray());
            }
        }
    }
}
