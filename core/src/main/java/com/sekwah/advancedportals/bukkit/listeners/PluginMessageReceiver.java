package com.sekwah.advancedportals.bukkit.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.bukkit.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;
import com.sekwah.advancedportals.bukkit.config.ConfigHelper;
import com.sekwah.advancedportals.bukkit.destinations.Destination;
import com.sekwah.advancedportals.bukkit.PluginMessages;
import com.sekwah.advancedportals.bungee.BungeeMessages;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PluginMessageReceiver implements PluginMessageListener {

    public static final String ENABLE_MESSAGE = PluginMessages.customPrefixFail + "§c Warning! To avoid vulnerabilities we have disabled proxy messages by default. To enable full proxy features, please change §eEnableProxySupport §cin the config.yml and ensure you have the plugin installed on the proxy.";
    public static final String WARNING_MESSAGE = PluginMessages.customPrefixFail + "§c Warning! A proxy message was received but proxy plugin support is not enabled. To enable it, please set §eEnableProxySupport §cto true and install the plugin on the proxy. If you do not remember having the proxy plugin, please ignore this message as it may be someone trying to attack your server.";
    private final AdvancedPortalsPlugin plugin;
    private final int teleportDelay;
    private boolean isNotifiedAboutEnabling = false;

    public PluginMessageReceiver(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        teleportDelay = config.getConfig().getInt(ConfigHelper.PROXY_TELEPORT_DELAY, 0);
        if(!plugin.isProxyPluginEnabled()) {
            Bukkit.getConsoleSender().sendMessage(ENABLE_MESSAGE);
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if (!channel.equals(BungeeMessages.CHANNEL_NAME)) {
            return;
        }

        if(!plugin.isProxyPluginEnabled()) {
            if(!isNotifiedAboutEnabling) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.isOp()) continue;
                    p.sendMessage(WARNING_MESSAGE);
                }
                Bukkit.getConsoleSender().sendMessage(WARNING_MESSAGE);

                isNotifiedAboutEnabling = true;
            }
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals(BungeeMessages.SERVER_DESTI)) {
            String targetDestination = in.readUTF();
            String bungeeUUID = in.readUTF();

            Player targetPlayer = this.plugin.getServer().getPlayer(UUID.fromString(bungeeUUID));

            if(teleportDelay <= 0) {
                teleportPlayerToDesti(targetPlayer, targetDestination, bungeeUUID);
            } else {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                                teleportPlayerToDesti(targetPlayer, targetDestination, bungeeUUID),
                        20L * teleportDelay
                );
            }

        }
    }

    public void teleportPlayerToDesti(Player player, String desti, String bungeeUUID) {
        if (player != null) {
            Destination.warp(player, desti, false, true);

        }
        else {
            plugin.getPlayerDestiMap().put(bungeeUUID, desti);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                            plugin.getPlayerDestiMap().remove(bungeeUUID),
                    20L * 10
            );
        }
    }
}
