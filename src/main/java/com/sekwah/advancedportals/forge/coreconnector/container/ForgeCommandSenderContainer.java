package com.sekwah.advancedportals.forge.coreconnector.container;

import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ForgeCommandSenderContainer implements CommandSenderContainer {
    private final ICommandSender sender;

    public ForgeCommandSenderContainer(ICommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(new TextComponentString(message));
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public PlayerContainer getPlayerContainer() {
        if(this.sender.getCommandSenderEntity() instanceof EntityPlayer) {
            return new ForgePlayerContainer((EntityPlayer) this.sender);
        }
        return null;
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }
}
