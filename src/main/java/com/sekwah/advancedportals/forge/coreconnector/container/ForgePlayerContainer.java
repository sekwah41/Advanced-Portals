package com.sekwah.advancedportals.forge.coreconnector.container;

import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import com.sekwah.advancedportals.coreconnector.container.WorldContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class ForgePlayerContainer implements PlayerContainer {

    private EntityPlayer sender;

    public ForgePlayerContainer(EntityPlayer sender) {

        this.sender = sender;
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public boolean isOp() {
        UserListOpsEntry userlistopsentry = FMLCommonHandler.instance().getMinecraftServerInstance()
                .getPlayerList().getOppedPlayers().getEntry(this.sender.getGameProfile());
        if (userlistopsentry != null) {
            return userlistopsentry.getPermissionLevel() >= 2;
        } else {
            return FMLCommonHandler.instance().getMinecraftServerInstance().getOpPermissionLevel() >= 2;
        }
    }

    @Override
    public PlayerLocation getLoc() {
        return null;
    }

    @Override
    public double getEyeHeight() {
        return 0;
    }

    @Override
    public void teleport(PlayerLocation location) {

    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public WorldContainer getWorld() {
        return null;
    }

    @Override
    public void sendFakeBlock(PortalLocation blockPos, String material) {

    }

    @Override
    public void sendFakeBlockWithData(PortalLocation blockPos, String material, byte data) {

    }

    @Override
    public void giveWool(String dyeColor, String itemName, String... itemDescription) {

    }

    @Override
    public void giveItem(String material, String itemName, String... itemDescription) {

    }
}
