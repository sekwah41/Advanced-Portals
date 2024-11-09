package com.sekwah.advancedportals.velocity;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.BuildConstants;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(authors = {"sekwah41"} ,id = "advancedportals", name = "Advanced Portals",
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/",
        version = BuildConstants.VERSION)
public class AdvancedPortalsVelocityPlugin {

    private final Logger logger;
    private final ProxyServer proxy;

    @Inject
    public AdvancedPortalsVelocityPlugin(ProxyServer proxy, Logger logger) {

        this.proxy = proxy;
        this.logger = logger;

    }

}
