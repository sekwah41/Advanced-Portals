package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

public class CoreListeners {

    private final AdvancedPortalsCore portalsCore;

    public CoreListeners(AdvancedPortalsCore portalsCore) {
        this.portalsCore = portalsCore;
    }

    public void playerJoin(PlayerContainer player) {
        AdvancedPortalsCore.getPortalManager().activateCooldown(player);
        if(player.isOp()) {
            if(!Lang.translate("translatedata.lastchange").equals(AdvancedPortalsCore.lastTranslationUpdate)) {
                player.sendMessage(Lang.translateColor("messageprefix.negative")
                        + Lang.translateInsertVariablesColor("translatedata.translationsoutdated", AdvancedPortalsCore.getTranslationName()));
                player.sendMessage(Lang.translateColor("messageprefix.negative")
                        + Lang.translateColor("translatedata.replacecommand"));
            }
        }
    }

    public void teleportEvent(PlayerContainer player) {
        AdvancedPortalsCore.getPortalManager().activateCooldown(player);
    }

    public void playerLeave(PlayerContainer player) {
        AdvancedPortalsCore.getPortalManager().playerLeave(player);
    }

    /**
     * @param loc where the entity spawns
     * @return if the entity is allowed to spawn
     */
    public boolean mobSpawn(PlayerLocation loc) {
        return !AdvancedPortalsCore.getPortalManager().inPortalRegion(loc);
    }

    /**
     * @param player
     * @param fromLoc
     * @param toLoc
     * @return if the player is allowed to move
     */
    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return AdvancedPortalsCore.getPortalManager().playerMove(player, fromLoc, toLoc);
    }

    /**
     *
     * @param fromPos
     * @param toPos
     * @return if movement is allowed
     */
    public boolean liquidFlow(PortalLocation fromPos, PortalLocation toPos) {
        return true;
    }

    /**
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to break
     */
    public boolean blockBreak(PlayerContainer player, PortalLocation blockPos, String blockMaterial) {
        return true;
    }

    /**
     * If the block is allowed to be interacted with e.g. a lever
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @return
     */
    public boolean blockInteract(PlayerContainer player, PortalLocation blockPos) {
        return true;
    }

    /**
     *  @param player
     * @param blockLoc
     * @param leftClick true = left click, false = right click
     * @return if player is allowed to interact with block
     */
    public boolean playerInteractWithBlock(PlayerContainer player, String materialName, String itemName,
                                           PortalLocation blockLoc, boolean leftClick) {
        if((player.isOp() || player.hasPermission("advancedportals.createportal")) &&
                materialName.equalsIgnoreCase(this.portalsCore.getConfig().getSelectorMaterial())
                && (!this.portalsCore.getConfig().getUseOnlySpecialAxe() || itemName.equals("\u00A7ePortal Region Selector"))) {
            AdvancedPortalsCore.getPortalManager().playerSelectorActivate(player, blockLoc, leftClick);
            return false;
        }
        return true;
    }

}
