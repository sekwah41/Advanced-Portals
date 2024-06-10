package com.sekwah.advancedportals.proxy;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class ProxyTargetServices {
    private final Map<UUID, TargetInfoArray> proxyDestinationMap = new HashMap<>();

    public TargetInfoArray getProxyDestinationMap(UUID uuid){
        return proxyDestinationMap.get(uuid);
    }

    public void addProxyDestinationMap(UUID uuid, String targetServer, String targetDestination) {
        proxyDestinationMap.put(uuid, new TargetInfoArray(targetServer, targetDestination));
    }

    public void removeProxyDestinationMap(UUID uuid) {
        if(!proxyDestinationMap.containsKey(uuid)) {
            return;
        }
        proxyDestinationMap.remove(uuid);
    }
}
