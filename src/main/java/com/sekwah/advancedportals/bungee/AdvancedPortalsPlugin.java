package com.sekwah.advancedportals.bungee;

import com.sekwah.advancedportals.bungee.listener.EventListener;
import com.sekwah.advancedportals.bungee.listener.PluginMessageReceiver;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class AdvancedPortalsPlugin extends Plugin {

    public String channelName = "mc:advancedportals";

    public HashMap<UUID, String[]> PlayerDestiMap = new HashMap<>();
    // key: UUID (string)
    // value: [0] targetServer, [1] targetDestination

    @Override
    public void onEnable() {
        getProxy().registerChannel(channelName);

        getProxy().getPluginManager().registerListener(this, new PluginMessageReceiver(this));
        getProxy().getPluginManager().registerListener(this, new EventListener(this));

        getLogger().info("\u00A7aAdvanced portals have been successfully enabled!");
}

    @Override
    public void onDisable() {
        getLogger().info("\\u00A7cAdvanced portals are being disabled!");
    }
}
