package com.sekwah.advancedportals.core;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.EntityContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.data.Direction;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.Objects;

public class CoreListeners {

    @Inject
    private PlayerDataServices playerDataServices;

    @Inject
    private PortalServices portalServices;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private GameScheduler gameScheduler;

    public void playerJoin(PlayerContainer player) {
        this.playerDataServices.activateCooldown(player);
    }

    public void teleportEvent(PlayerContainer player) {
        this.playerDataServices.activateCooldown(player);
    }

    public void playerLeave(PlayerContainer player) {
        this.playerDataServices.playerLeave(player);
    }

    public void tick() {
        this.gameScheduler.tick();
    }

    /**
     * @param player
     * @param toLoc
     */
    public void playerMove(PlayerContainer player, PlayerLocation toLoc) {
        this.portalServices.playerMove(player, toLoc);
    }

    /**
     * If the block is indirectly broken it will also take null for the player e.g. with tnt
     *
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to break
     */
    public boolean blockBreak(PlayerContainer player, BlockLocation blockPos, String blockMaterial, String itemInHandMaterial, String itemInHandName) {
        if(player == null) {
            return !portalServices.inPortalRegionProtected(blockPos);
        }
        if(!(PortalPermissions.BUILD.hasPermission(player) || !portalServices.inPortalRegionProtected(blockPos))) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.nobuild"));
            return false;
        }
        return true;
    }

    /**
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to be placed
     */
    public boolean blockPlace(PlayerContainer player, BlockLocation blockPos, String blockMaterial, String itemInHandMaterial, String itemInHandName) {
        if(player != null && PortalPermissions.BUILD.hasPermission(player)) {
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
                return true;
            }
            else if(itemInHandName.equals("\u00A78End Portal Block Placer")) {
                world.setBlock(blockPos, "END_PORTAL");
                return true;
            }
            else if(itemInHandName.equals("\u00A78Gateway Block Placer")) {
                world.setBlock(blockPos, "END_GATEWAY");
                return true;
            }
            return true;
        }
        if(portalServices.inPortalRegionProtected(blockPos)) {
            if(player != null) {
                player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.nobuild"));
            }
            return false;
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
            this.playerDataServices.playerSelectorActivate(player, blockLoc, leftClick);
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

    public void worldChange(PlayerContainer player) {
        this.playerDataServices.activateCooldown(player);
    }

    public boolean preventEntityCombust(EntityContainer entity) {
        return portalServices.inPortalRegion(entity.getBlockLoc(), 2);
    }

    public boolean portalEvent(PlayerContainer player) {
        return !portalServices.inPortalRegion(player.getBlockLoc(), 1)
                && (!(player.getHeight() > 1) || !portalServices.inPortalRegion(player.getBlockLoc().addY((int) player.getHeight()), 1));
    }

    public boolean entityPortalEvent(EntityContainer entity) {
        return !portalServices.inPortalRegion(entity.getBlockLoc(), 1)
                && (!(entity.getHeight() > 1) || !portalServices.inPortalRegion(entity.getBlockLoc().addY((int) entity.getHeight()), 1));
    }
}
