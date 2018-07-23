package com.sekwah.advancedportals.forge.coreconnector.container;

import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

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
        if(this.sender.getCommandSenderEntity() instanceof EntityPlayer) {

            if(!FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().canSendCommands(((EntityPlayer) this.sender).getGameProfile())) {
                return false;
            }
            else {
                UserListOpsEntry userlistopsentry = FMLCommonHandler.instance().getMinecraftServerInstance()
                        .getPlayerList().getOppedPlayers().getEntry(((EntityPlayer) this.sender).getGameProfile());
                // Intellij may say this will always be true but it isn't
                if (userlistopsentry != null) {
                    return userlistopsentry.getPermissionLevel() >= 2;
                } else {
                    return FMLCommonHandler.instance().getMinecraftServerInstance().getOpPermissionLevel() >= 2;
                }
            }

        }
        else {
            return false;
        }
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
