package com.sekwah.advancedportals.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * While there may be a better way to do this for now we are keeping the behavior so it also works with Bungee's horrible API.
 */
@Plugin(id = "advancedportals", name = "Advanced Portals",
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/",
        version = "0.6.1")
public class AdvancedPortalsPlugin {

    public HashMap<String, String[]> PlayerDestiMap = new HashMap<>();

    private final Logger logger;
    private final ProxyServer proxy;
    private LegacyChannelIdentifier AP_CHANNEL;

    @Inject
    public AdvancedPortalsPlugin(ProxyServer proxy, Logger logger) {

        this.proxy = proxy;
        this.logger = logger;

        logger.info("\u00A7aAdvanced portals have been successfully enabled!");

    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {

        String[] splitChannel = BungeeMessages.CHANNEL_NAME.split(":");
        AP_CHANNEL = new LegacyChannelIdentifier(BungeeMessages.CHANNEL_NAME);

        proxy.getChannelRegistrar().register(AP_CHANNEL);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if(event.getIdentifier().equals(AP_CHANNEL)) {
            if(event.getSource() instanceof ServerConnection) {

                ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());

                String subChannel = in.readUTF();

                if (subChannel.equalsIgnoreCase(BungeeMessages.ENTER_PORTAL)) {
                    String targetServer = in.readUTF();
                    String targetDestination = in.readUTF();
                    String targetUUID = in.readUTF();

                    PlayerDestiMap.put(targetUUID, new String[]{targetServer, targetDestination, targetUUID});

                    proxy.getScheduler().buildTask(this, () -> PlayerDestiMap.remove(targetUUID))
                            .delay(10, TimeUnit.SECONDS).schedule();
                }
                else if (subChannel.equalsIgnoreCase(BungeeMessages.BUNGEE_COMMAND)) {
                    String command = in.readUTF();
                    ServerConnection connection = (ServerConnection) event.getSource();
                    if(connection.getPlayer() != null) {
                        proxy.getCommandManager().executeAsync(connection.getPlayer(), command);
                    }

                }
            }
            // So that client packets don't make it through to the servers, always trigger on this channel.
            event.setResult(PluginMessageEvent.ForwardResult.handled());
        }
    }

    @Subscribe
    public void postJoinEvent(ServerPostConnectEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();

        String[] val = PlayerDestiMap.get(uuid);

        if (val != null) {
            // key: UUID (string)
            // value: [0] targetServer, [1] targetDestination, [2] onlineUUID

            event.getPlayer().getCurrentServer().ifPresent(serverConnection -> {

                if (serverConnection.getServerInfo().getName().equalsIgnoreCase(val[0])) {

                    ByteArrayDataOutput out = ByteStreams.newDataOutput();

                    out.writeUTF(BungeeMessages.SERVER_DESTI);
                    out.writeUTF(val[1]);
                    out.writeUTF(val[2]);

                    serverConnection.sendPluginMessage(AP_CHANNEL, out.toByteArray());

                }
            });
        }
    }

}
