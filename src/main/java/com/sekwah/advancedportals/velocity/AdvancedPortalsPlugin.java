package com.sekwah.advancedportals.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.lifecycle.ProxyInitializeEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.connection.ServerConnection;
import com.velocitypowered.api.proxy.messages.PluginChannelId;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import net.kyori.adventure.key.Key;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * While there may be a better way to do this for now we are keeping the behavior so it also works with Bungee's horrible API.
 */
@Plugin(id = "advancedportals", name = "Advanced Portals",
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/",
        version = "0.6.0",
        description = "Customisable portal plugin",
        dependencies = {
        @Dependency(id = "velocity", version = "2.x.x")
})
public class AdvancedPortalsPlugin {

    public HashMap<String, String[]> PlayerDestiMap = new HashMap<>();

    private static final Key ADVANCED_PORTALS = Key.key("advancedportals", "events");

    private final Logger logger;
    private final ProxyServer proxy;
    private PluginChannelId advancedPortalsChannelId;

    @Inject
    public AdvancedPortalsPlugin(ProxyServer proxy, Logger logger) {

        this.proxy = proxy;
        this.logger = logger;

        logger.info("\u00A7aAdvanced portals have been successfully enabled!");

    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {

        String[] splitChannel = BungeeMessages.CHANNEL_NAME.split(":");
        advancedPortalsChannelId = PluginChannelId.withLegacy(BungeeMessages.CHANNEL_NAME, ADVANCED_PORTALS);

        proxy.channelRegistrar().register(advancedPortalsChannelId);
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if(event.channel().equals(advancedPortalsChannelId)) {
            if(event.source() instanceof ServerConnection) {

                ByteArrayDataInput in = ByteStreams.newDataInput(event.rawMessage());

                String subChannel = in.readUTF();

                if (subChannel.equalsIgnoreCase(BungeeMessages.ENTER_PORTAL)) {
                    String targetServer = in.readUTF();
                    String targetDestination = in.readUTF();
                    String targetUUID = in.readUTF();

                    PlayerDestiMap.put(targetUUID, new String[]{targetServer, targetDestination, targetUUID});

                    proxy.scheduler().buildTask(this, () -> PlayerDestiMap.remove(targetUUID))
                            .delay(10, TimeUnit.SECONDS).schedule();
                }
                else if (subChannel.equalsIgnoreCase(BungeeMessages.BUNGEE_COMMAND)) {
                    String command = in.readUTF();
                    ServerConnection connection = (ServerConnection) event.source();
                    if(connection.player() != null) {
                        proxy.commandManager().execute(connection.player(), command);
                    }

                }
            }
            // So that client packets don't make it through to the servers, always trigger on this channel.
            event.setHandled(true);
        }
    }

    @Subscribe
    public void postJoinEvent(ServerPostConnectEvent event) {
        String uuid = event.player().id().toString();

        String[] val = PlayerDestiMap.get(uuid);

        if (val != null) {
            // key: UUID (string)
            // value: [0] targetServer, [1] targetDestination, [2] onlineUUID

            @Nullable ServerConnection serverConnection = event.player().connectedServer();
            if(serverConnection == null) {
                if (serverConnection.serverInfo().name().equalsIgnoreCase(val[0])) {

                    ByteArrayDataOutput out = ByteStreams.newDataOutput();

                    out.writeUTF(BungeeMessages.SERVER_DESTI);
                    out.writeUTF(val[1]);
                    out.writeUTF(val[2]);

                    serverConnection.sendPluginMessage(advancedPortalsChannelId, out.toByteArray());

                }
            }
        }
    }

}
