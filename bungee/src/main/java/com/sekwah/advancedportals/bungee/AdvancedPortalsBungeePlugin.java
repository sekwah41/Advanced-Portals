package com.sekwah.advancedportals.bungee;

import com.sekwah.advancedportals.bungee.connector.container.BungeeProxyContainer;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.proxycore.AdvancedPortalsProxyCore;
import net.md_5.bungee.api.plugin.Plugin;

public class AdvancedPortalsBungeePlugin extends Plugin {
    private AdvancedPortalsProxyCore proxyCore;

    @Override
    public void onEnable() {
        this.proxyCore = new AdvancedPortalsProxyCore(new BungeeInfoLogger(this), new BungeeProxyContainer(this));
        this.proxyCore.onEnable();

        getProxy().registerChannel(ProxyMessages.CHANNEL_NAME);

        getProxy().getPluginManager().registerListener(this, new EventListener(this, this.proxyCore));
    }

    @Override
    public void onDisable() {
        this.proxyCore.onDisable();
    }
}
