package com.sekwah.advancedportals.legacyspigot.connector.container;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LegacySpigotCommandSenderContainer implements CommandSenderContainer {
    private final CommandSender sender;

    public LegacySpigotCommandSenderContainer(CommandSender commandSender) {
        this.sender = commandSender;
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public ServerContainer getServer() {
        return new LegacySpigotServerContainer(sender.getServer());
    }

    public boolean isOp() {
        return sender.isOp();
    }

    /**
     * @return null if there isnt a player e.g. the console
     */
    public LegacySpigotPlayerContainer getPlayerContainer() {
        if (sender instanceof Player) {
            return new LegacySpigotPlayerContainer((Player) sender);
        }
        return null;
    }

    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
