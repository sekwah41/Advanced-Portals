package com.sekwah.advancedportals.bungee;

import com.sekwah.advancedportals.bungee.listener.EventListener;
import com.sekwah.advancedportals.bungee.listener.PluginMessageReceiver;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;

public class AdvancedPortalsPlugin extends Plugin {

    public HashMap<String, String[]> PlayerDestiMap = new HashMap<>();
    // key: UUID (string)
    // value: [0] targetServer, [1] targetDestination

    @Override
    public void onEnable() {
        getProxy().registerChannel(BungeeMessages.CHANNEL_NAME);

        if(BungeeMessages.CHANNEL_NAME != null)

        getProxy().getPluginManager().registerListener(this, new PluginMessageReceiver(this));
        getProxy().getPluginManager().registerListener(this, new EventListener(this));

        getLogger().info("\u00A7aAdvanced portals have been successfully enabled!");
}

    @Override
    public void onDisable() {
        getLogger().info("\\u00A7cAdvanced portals are being disabled!");
    }
}
