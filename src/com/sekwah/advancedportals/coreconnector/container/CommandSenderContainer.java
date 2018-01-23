package com.sekwah.advancedportals.coreconnector.container;

import com.sekwah.advancedportals.core.util.Lang;
import org.bukkit.command.CommandSender;

public class CommandSenderContainer {

    private final CommandSender sender;

    public CommandSenderContainer(CommandSender commandSender) {
        this.sender = commandSender;
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public boolean isOp() {
        return false;
    }
}
