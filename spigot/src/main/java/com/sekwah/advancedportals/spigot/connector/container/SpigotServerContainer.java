package com.sekwah.advancedportals.spigot.connector.container;

import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.tags.CommandTag;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class SpigotServerContainer implements ServerContainer {
    @Inject
    private CoreListeners coreListeners;
    private final Server server;
    // Create an array of items
    private final List<String> commonTriggerBlockList =
        Arrays
            .asList(Material.WATER, Material.LAVA, Material.AIR,
                    Material.NETHER_PORTAL, Material.END_GATEWAY,
                    Material.END_PORTAL)
            .stream()
            .map(Enum::name)
            .toList();

    private final List<String> fullTriggerBlockList =
        Arrays.stream(Material.values()).map(Enum::name).toList();

    public SpigotServerContainer(Server server) {
        this.server = server;
    }

    @Override
    public WorldContainer getWorld(String name) {
        var world = server.getWorld(name);
        if (world != null) {
            return new SpigotWorldContainer(world);
        } else {
            return null;
        }
    }

    @Override
    public PlayerContainer getPlayer(String name) {
        var player = server.getPlayer(name);
        if (player != null) {
            return new SpigotPlayerContainer(player);
        } else {
            return null;
        }
    }

    @Override
    public PlayerContainer getPlayer(UUID name) {
        var player = server.getPlayer(name);
        if (player != null) {
            return new SpigotPlayerContainer(player);
        } else {
            return null;
        }
    }

    @Override
    public List<String> getAllTriggerBlocks() {
        return this.fullTriggerBlockList;
    }

    @Override
    public List<String> getCommonTriggerBlocks() {
        return this.commonTriggerBlockList;
    }

    @Override
    public PlayerContainer[] getPlayers() {
        return server.getOnlinePlayers()
            .stream()
            .map(SpigotPlayerContainer::new)
            .toArray(PlayerContainer[] ::new);
    }

    @Override
    public void registerOutgoingChannel(String channel) {
        server.getMessenger().registerOutgoingPluginChannel(
            AdvancedPortalsPlugin.getInstance(), channel);
    }

    @Override
    public void registerIncomingChannel(String channel) {
        server.getMessenger().registerIncomingPluginChannel(
            AdvancedPortalsPlugin.getInstance(), channel,
            (s, player, bytes)
                -> coreListeners.incomingMessage(
                    new SpigotPlayerContainer(player), s, bytes));
    }

    @Override
    public void dispatchCommand(UUID uuid, String command,
                                CommandTag.CommandLevel commandLevel) {
        Player player = server.getPlayer(uuid);
        switch (commandLevel) {
            case CONSOLE:
                server.dispatchCommand(server.getConsoleSender(), command);
                break;
            case PLAYER:
                server.dispatchCommand(player, command);
                break;
            case OP, PERMISSION_WILDCARD:
                executeCommandWithPermission(player, server, command,
                                             commandLevel);
                break;
        }
    }

    // Execute commands with elevated permissions method
    private void executeCommandWithPermission(
        Player player, Server server, String command,
        CommandTag.CommandLevel commandLevel) {
        switch (commandLevel) {
            case PERMISSION_WILDCARD:
                if (player.hasPermission("*")) {
                    server.dispatchCommand(player, command);
                    return;
                }
                PermissionAttachment permissionAttachment =
                    player.addAttachment(
                        server.getPluginManager().getPlugin("AdvancedPortals"));
                try {
                    permissionAttachment.setPermission("*", true);
                    server.dispatchCommand(player, command);
                } finally {
                    player.removeAttachment(permissionAttachment);
                }
                break;
            case OP:
                if (player.isOp()) {
                    server.dispatchCommand(player, command);
                    return;
                }
                try {
                    player.setOp(true);
                    server.dispatchCommand(player, command);
                } finally {
                    player.setOp(false);
                }
                break;
        }
    }

    @Override
    public String matchMaterialName(String materialName) {
        return Arrays.stream(Material.values())
            .map(Enum::name)
            .filter(name -> name.equalsIgnoreCase(materialName))
            .findFirst()
            .orElse(null);
    }
}
