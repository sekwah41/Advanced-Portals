package com.sekwah.advancedportals.spigot.connector.container;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.tags.activation.CommandTag;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;

public class SpigotServerContainer implements ServerContainer {
    private final Server server;
    private final List<String> triggerBlockList =
        Arrays.stream(Material.values())
            .filter(this::isAdvancedPortalBlock)
            .map(Enum::name)
            .toList();

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
    public List<String> getTriggerBlocks() {
        return this.triggerBlockList;
    }

    @Override
    public PlayerContainer[] getPlayers() {
        return server.getOnlinePlayers()
            .stream()
            .map(SpigotPlayerContainer::new)
            .toArray(PlayerContainer[] ::new);
    }

    // Check if it's a material compatible with making portals
    private boolean isAdvancedPortalBlock(Material material) {
        return switch (material) {
            case WATER, LAVA, AIR, NETHER_PORTAL, END_GATEWAY, END_PORTAL -> true;
            default -> false;
        };
    }

    @Override
    public void dispatchCommand(UUID uuid, String command, CommandTag.CommandLevel commandLevel) {
        Player player = server.getPlayer(uuid);
        switch (commandLevel) {
            case CONSOLE:
                server.dispatchCommand(server.getConsoleSender(), command);
                break;
            case PLAYER:
                server.dispatchCommand(player, command);
                break;
            case OP, PERMISSION_WILDCARD:
                executeCommandWithPermission(player, server, command, commandLevel);
                break;
        }
    }

    // Execute commands with elevated permissions method
    private void executeCommandWithPermission (Player player, Server server, String command, CommandTag.CommandLevel commandLevel) {
        switch (commandLevel) {
            case PERMISSION_WILDCARD:
                if (player.hasPermission("*")) {
                    server.dispatchCommand(player, command);
                    return;
                }
                PermissionAttachment permissionAttachment = player.addAttachment(server.getPluginManager().getPlugin("AdvancedPortals"));
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
}
