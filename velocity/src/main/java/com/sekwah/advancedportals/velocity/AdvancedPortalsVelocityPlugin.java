package com.sekwah.advancedportals.velocity;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.BuildConstants;
import com.sekwah.advancedportals.proxycore.AdvancedPortalsProxyCore;
import com.sekwah.advancedportals.velocity.connector.container.VelocityProxyContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(authors = {"sekwah41"} ,id = "advancedportals", name = "Advanced Portals",
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/",
        version = BuildConstants.VERSION)
public class AdvancedPortalsVelocityPlugin {

    private AdvancedPortalsProxyCore proxyCore;

    private final Logger logger;
    private final ProxyServer proxy;

    @Inject
    public AdvancedPortalsVelocityPlugin(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
        this.proxyCore = new AdvancedPortalsProxyCore(new VelocityInfoLogger(this.logger, this.proxy), new VelocityProxyContainer());
        this.proxyCore.onEnable();
    }

}
