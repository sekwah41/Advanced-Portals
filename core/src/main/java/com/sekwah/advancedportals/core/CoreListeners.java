package com.sekwah.advancedportals.core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.EntityContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.data.BlockAxis;
import com.sekwah.advancedportals.core.data.Direction;
import com.sekwah.advancedportals.core.network.ServerDestiPacket;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.TriggerType;
import java.util.Objects;

public class CoreListeners {
    @Inject
    private PlayerDataServices playerDataServices;

    @Inject
    private PortalServices portalServices;

    @Inject
    private DestinationServices destinationServices;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private GameScheduler gameScheduler;

    public void playerJoin(PlayerContainer player) {
        this.playerDataServices.setJoinCooldown(player);

        this.setIfInPortal(player);
    }

    private void setIfInPortal(PlayerContainer player) {
        String inPortal =
            this.portalServices.inPortalRegionGetName(player.getBlockLoc());

        if (inPortal == null) {
            inPortal = this.portalServices.inPortalRegionGetName(
                player.getBlockLoc().addY((int) player.getHeight()));
        }

        this.playerDataServices.getPlayerData(player).setInPortal(inPortal);
    }

    public void teleportEvent(PlayerContainer player) {
        this.playerDataServices.setJoinCooldown(player);
    }

    public void playerLeave(PlayerContainer player) {
        this.playerDataServices.playerLeave(player);
    }

    public void incomingMessage(PlayerContainer player, String channel,
                                byte[] message) {
        ByteArrayDataInput buffer = ByteStreams.newDataInput(message);
        String messageType = buffer.readUTF();

        switch (messageType) {
            case ProxyMessages.SERVER_DESTI: {
                ServerDestiPacket serverDestiPacket =
                    ServerDestiPacket.decode(buffer);
                this.destinationServices.teleportToDestination(
                    serverDestiPacket.getDestination(), player, true);
            }
        }
    }

    public void tick() {
        this.gameScheduler.tick();
    }

    /**
     * @param player
     * @param toLoc
     */
    public void playerMove(PlayerContainer player, PlayerLocation toLoc) {
        this.portalServices.checkPortalActivation(player, toLoc,
                                                  TriggerType.MOVEMENT);
    }

    /**
     * If the block is indirectly broken it will also take null for the player
     * e.g. with tnt
     *
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to break
     */
    public boolean blockBreak(PlayerContainer player, BlockLocation blockPos,
                              String blockMaterial, String itemInHandMaterial,
                              String itemInHandName) {
        if (!configRepository.getPortalProtection())
            return true;

        if (player == null) {
            return !portalServices.inPortalRegionProtected(blockPos);
        }
        if (!(Permissions.BUILD.hasPermission(player)
              || !portalServices.inPortalRegionProtected(blockPos))) {
            player.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("portal.nobuild"));
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
    public boolean blockPlace(PlayerContainer player, BlockLocation blockPos,
                              String blockMaterial, String itemInHandMaterial,
                              String itemInHandName) {
        if (blockPos == null || blockMaterial == null) {
            return false;
        }

        if (player != null && Permissions.BUILD.hasPermission(player)) {
            WorldContainer world = player.getWorld();
            if (itemInHandName != null
                && itemInHandName.equals("\u00A75Portal Block Placer")) {
                world.setBlock(blockPos, "NETHER_PORTAL");
                for (Direction direction : Direction.values()) {
                    BlockLocation checkLoc =
                        new BlockLocation(blockPos, direction);
                    if (world.getBlock(checkLoc).equals("NETHER_PORTAL")) {
                        world.setBlockAxis(blockPos,
                                           world.getBlockAxis(checkLoc));
                        break;
                    }
                }
                return true;
            } else if (itemInHandName != null
                       && itemInHandName.equals(
                           "\u00A78End Portal Block Placer")) {
                world.setBlock(blockPos, "END_PORTAL");
                return true;
            } else if (itemInHandName != null
                       && itemInHandName.equals(
                           "\u00A78Gateway Block Placer")) {
                world.setBlock(blockPos, "END_GATEWAY");
                world.disableBeacon(blockPos);
                return true;
            }
            return true;
        }

        if (portalServices.inPortalRegionProtected(blockPos)) {
            if (player != null) {
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("portal.nobuild"));
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
    public boolean blockInteract(PlayerContainer player,
                                 BlockLocation blockPos) {
        return true;
    }

    /**
     *  @param player
     * @param blockLoc
     * @param leftClick true = left click, false = right click
     * @return if player is allowed to interact with block
     */
    public boolean playerInteractWithBlock(PlayerContainer player,
                                           String blockMaterialname,
                                           String itemMaterialName,
                                           String itemName,
                                           BlockLocation blockLoc,
                                           boolean leftClick) {
        if (itemName != null && Permissions.CREATE_PORTAL.hasPermission(player)
            && itemMaterialName.equalsIgnoreCase(
                this.configRepository.getSelectorMaterial())
            && (!this.configRepository.getUseOnlySpecialAxe()
                || itemName.equals("\u00A7ePortal Region Selector"))) {
            this.playerDataServices.playerSelectorActivate(player, blockLoc,
                                                           leftClick);
            return false;
        } else if (itemName != null && leftClick
                   && Objects.equals(itemMaterialName, "PURPLE_WOOL")
                   && itemName.equals("\u00A75Portal Block Placer")
                   && Permissions.BUILD.hasPermission(player)) {
            if (!Objects.equals(blockMaterialname, "NETHER_PORTAL")) {
                return false;
            }
            WorldContainer world = player.getWorld();
            if (world.getBlockAxis(blockLoc) == BlockAxis.X) {
                world.setBlockAxis(blockLoc, BlockAxis.Z);
            } else {
                world.setBlockAxis(blockLoc, BlockAxis.X);
            }
            return false;
        }

        return true;
    }

    public void worldChange(PlayerContainer player) {
        this.playerDataServices.setJoinCooldown(player);
        this.setIfInPortal(player);
    }

    public boolean preventEntityCombust(EntityContainer entity) {
        return portalServices.inPortalRegion(entity.getBlockLoc(), 2);
    }

    public boolean entityPortalEvent(EntityContainer entity) {
        BlockLocation pos = entity.getBlockLoc();
        if (entity instanceof PlayerContainer) {
            PlayerContainer player = (PlayerContainer) entity;
            PlayerData playerData = playerDataServices.getPlayerData(player);
            if (playerData.getPortalBlockCooldown()) {
                return false;
            }
        }

        return !(portalServices.inPortalRegion(pos, 1)
                 || portalServices.inPortalRegion(
                     pos.addY((int) entity.getHeight()), 1));
    }

    public boolean playerPortalEvent(PlayerContainer player,
                                     PlayerLocation toLoc) {
        PlayerData playerData = playerDataServices.getPlayerData(player);

        if (playerData.getPortalBlockCooldown()) {
            return false;
        }

        PortalServices.PortalActivationResult portalResult =
            this.portalServices.checkPortalActivation(player, toLoc,
                                                      TriggerType.PORTAL);

        if (portalResult
            != PortalServices.PortalActivationResult.NOT_IN_PORTAL) {
            return false;
        }

        // Extra checks to prevent the player from being teleported by touching
        // a portal but not having their body fully in the portal
        BlockLocation pos = player.getBlockLoc();

        boolean feetInPortal = portalServices.inPortalRegion(pos, 1);
        boolean headInPortal = portalServices.inPortalRegion(
            pos.addY((int) player.getHeight()), 1);

        return !(feetInPortal || headInPortal);
    }

    public boolean physicsEvent(BlockLocation blockLocation, String string) {
        return !configRepository.getDisablePhysicsEvents()
            || !portalServices.inPortalRegionProtected(blockLocation);
    }
}
