package com.sekwah.advancedportals.forge.coreconnector.container;

import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import com.sekwah.advancedportals.coreconnector.container.WorldContainer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class ForgePlayerContainer implements PlayerContainer {

    public ForgePlayerContainer(EntityPlayer sender) {

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
        return false;
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
