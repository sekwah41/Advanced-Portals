package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.util.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSenderContainer {

    private final CommandSender sender;

    public CommandSenderContainer(CommandSender commandSender) {
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
    public PlayerContainer getPlayerContainer() {
        if (sender instanceof Player) {
            return new PlayerContainer((Player) sender);
        }
        return null;
    }

    public boolean hasPermission(String permission) {
        return false;
    }
}
