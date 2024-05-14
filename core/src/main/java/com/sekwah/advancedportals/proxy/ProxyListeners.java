package com.sekwah.advancedportals.proxy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.tags.activation.CommandTag;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProxyListeners {

    @Inject
    private ProxyTargetServices proxyTargetServices;

    public void connectedEvent(PlayerContainer proxyPlayerContainer) {
        TargetInfoArray targetInfoArray = proxyTargetServices.getProxyDestinationMap(proxyPlayerContainer.getUUID());

        if (targetInfoArray == null) return;

        if (proxyPlayerContainer.getServer().getName().equalsIgnoreCase(targetInfoArray.getTargetServer())) return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        // Send UUID, Target Destination and Target Server respectively
        out.writeUTF(proxyPlayerContainer.getUUID().toString());
        out.writeUTF(targetInfoArray.getTargetDestination());
        out.writeUTF(targetInfoArray.getTargetServer());
    }

    public void pluginMessageEvent(byte[] data, ServerContainer proxyServerContainer, PlayerContainer proxyPlayerContainer) {
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        String subChannel = in.readUTF();

        if (subChannel.equalsIgnoreCase(ProxyMessages.ENTER_PORTAL.toString())) {
            String targetUUID = in.readUTF();
            String targetServer = in.readUTF();
            String targetDestination = in.readUTF();

            proxyTargetServices.addProxyDestinationMap(UUID.fromString(targetUUID), targetServer, targetDestination);

            // Schedule removal after 10 seconds
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> proxyTargetServices
                    .removeProxyDestinationMap(UUID.fromString(targetUUID)), 10, TimeUnit.SECONDS);
            scheduler.shutdown();

        } else if (subChannel.equalsIgnoreCase(ProxyMessages.PROXY_COMMAND.toString())) {
            String command = in.readUTF();
            proxyServerContainer.dispatchCommand(proxyPlayerContainer.getUUID(), command, null);
        }
    }

}
