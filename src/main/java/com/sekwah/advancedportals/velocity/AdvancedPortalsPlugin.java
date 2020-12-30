package com.sekwah.advancedportals.velocity;

import com.google.inject.Inject;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import java.util.HashMap;

@Plugin(id = "advancedportals", name = "Advanced Portals",
        url = "https://www.spigotmc.org/resources/advanced-portals.14356/",
        version = "0.5.12")
public class AdvancedPortalsPlugin {

    public HashMap<String, String[]> PlayerDestiMap = new HashMap<>();

    private final Logger logger;
    private ProxyServer proxy;

    @Inject
    public AdvancedPortalsPlugin(ProxyServer proxy, Logger logger) {

        this.proxy = proxy;
        this.logger = logger;

        String[] splitChannel = BungeeMessages.CHANNEL_NAME.split(":");
        proxy.getChannelRegistrar().register(MinecraftChannelIdentifier.create(splitChannel[0], splitChannel[1]));

        logger.info("\u00A7aAdvanced portals have been successfully enabled!");

    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        System.out.println("THING HERE");
        System.out.println(event);
    }

}
