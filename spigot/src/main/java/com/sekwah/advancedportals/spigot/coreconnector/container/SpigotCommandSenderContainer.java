package com.sekwah.advancedportals.spigot.coreconnector.container;

import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotCommandSenderContainer implements CommandSenderContainer {

    private final CommandSender sender;

    public SpigotCommandSenderContainer(CommandSender commandSender) {
        this.sender = commandSender;
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public boolean isOp() {
        return sender.isOp();
    }

    /**
     * @return null if there isnt a player e.g. the console
     */
    public SpigotPlayerContainer getPlayerContainer() {
        if (sender instanceof Player) {
            return new SpigotPlayerContainer((Player) sender);
        }
        return null;
    }

    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
