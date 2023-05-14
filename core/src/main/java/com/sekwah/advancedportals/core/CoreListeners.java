package com.sekwah.advancedportals.core;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.data.Direction;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PortalTempDataServices;

import java.util.Objects;

public class CoreListeners {

    @Inject
    private PortalTempDataServices portalTempDataServices;

    @Inject
    private PortalServices portalServices;

    @Inject
    private ConfigRepository configRepository;

    public void playerJoin(PlayerContainer player) {
        this.portalTempDataServices.activateCooldown(player);
    }

    public void teleportEvent(PlayerContainer player) {
        this.portalTempDataServices.activateCooldown(player);
    }

    public void playerLeave(PlayerContainer player) {
        this.portalTempDataServices.playerLeave(player);
    }

    /**
     * @param loc where the entity spawns
     * @return if the entity is allowed to spawn
     */
    public boolean mobSpawn(PlayerLocation loc) {
        return !this.portalServices.inPortalRegion(loc);
    }

    /**
     * @param player
     * @param fromLoc
     * @param toLoc
     * @return if the player is allowed to move
     */
    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return this.portalServices.playerMove(player, fromLoc, toLoc);
    }

    /**
     *
     * @param fromPos
     * @param toPos
     * @return if movement is allowed
     */
    public boolean liquidFlow(BlockLocation fromPos, BlockLocation toPos) {
        return true;
    }

    /**
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to break
     */
    public boolean blockBreak(PlayerContainer player, BlockLocation blockPos, String blockMaterial, String itemInHandMaterial, String itemInHandName) {
        return true;
    }

    /**
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to be placed
     */
    public boolean blockPlace(PlayerContainer player, BlockLocation blockPos, String blockMaterial, String itemInHandMaterial, String itemInHandName) {
        if(itemInHandName != null && player != null && PortalPermissions.BUILD.hasPermission(player)) {
            WorldContainer world = player.getWorld();
            if(itemInHandName.equals("\u00A75Portal Block Placer")) {
                world.setBlock(blockPos, "NETHER_PORTAL");
                for (Direction direction : Direction.values()) {
                    var checkLoc = new BlockLocation(blockPos, direction);
                    if (world.getBlock(checkLoc).equals("NETHER_PORTAL")) {
                        world.setBlockAxis(blockPos, world.getBlockAxis(checkLoc));
                        break;
                    }
                }
                return false;
            }
            else if(itemInHandName.equals("\u00A78End Portal Block Placer")) {
                world.setBlock(blockPos, "END_PORTAL");
                return false;
            }
            else if(itemInHandName.equals("\u00A78Gateway Block Placer")) {
                world.setBlock(blockPos, "END_GATEWAY");
                return false;
            }
        }
        return true;
    }

    /**
     * If the block is allowed to be interacted with e.g. a lever
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @return
     */
    public boolean blockInteract(PlayerContainer player, BlockLocation blockPos) {
        return true;
    }

    /**
     *  @param player
     * @param blockLoc
     * @param leftClick true = left click, false = right click
     * @return if player is allowed to interact with block
     */
    public boolean playerInteractWithBlock(PlayerContainer player, String blockMaterialname, String itemMaterialName, String itemName,
                                           BlockLocation blockLoc, boolean leftClick) {
        if(itemName != null && (player.isOp() || PortalPermissions.CREATE_PORTAL.hasPermission(player)) &&
                itemMaterialName.equalsIgnoreCase(this.configRepository.getSelectorMaterial())
                && (!this.configRepository.getUseOnlySpecialAxe() || itemName.equals("\u00A7ePortal Region Selector"))) {
            this.portalTempDataServices.playerSelectorActivate(player, blockLoc, leftClick);
            return false;
        }
        else if(itemName != null && leftClick &&
                Objects.equals(itemMaterialName, "PURPLE_WOOL") &&
                itemName.equals("\u00A75Portal Block Placer") && PortalPermissions.BUILD.hasPermission(player)) {
            if(!Objects.equals(blockMaterialname, "NETHER_PORTAL")) {
                return false;
            }
            WorldContainer world = player.getWorld();
            if(world.getBlockAxis(blockLoc) == BlockAxis.X) {
                world.setBlockAxis(blockLoc, BlockAxis.Z);
            }
            else {
                world.setBlockAxis(blockLoc, BlockAxis.X);
            }
            return false;
        }

        return true;
    }

}
