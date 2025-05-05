package com.sekwah.advancedportals.forge.connector.container;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class ForgeCommandSenderContainer implements CommandSenderContainer {
    private final CommandSourceStack source;

    public ForgeCommandSenderContainer(CommandSourceStack source) {
        this.source = source;
    }

    @Override
    public void sendMessage(String message) {
        this.source.sendSystemMessage(Component.literal(message));
    }

    @Override
    public PlayerContainer getPlayerContainer() {
        return null;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }
}
