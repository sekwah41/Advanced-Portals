package com.sekwah.advancedportals.bungee;

import com.sekwah.advancedportals.proxy.ProxyMessages;
import net.md_5.bungee.api.plugin.Plugin;

public class AdvancedPortalsPlugin extends Plugin {

    @Override
    public void onEnable() {
        getProxy().registerChannel(ProxyMessages.CHANNEL_NAME.toString());

        getProxy().getPluginManager().registerListener(this, new BungeeListeners(this));
    }
}
