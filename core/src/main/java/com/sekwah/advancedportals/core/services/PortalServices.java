package com.sekwah.advancedportals.core.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.tags.NameTag;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.PlayerUtils;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.core.warphandler.TriggerType;

import javax.annotation.Nullable;
import java.util.*;

@Singleton
public class PortalServices {
    @Inject
    private IPortalRepository portalRepository;

    @Inject
    private transient PlayerDataServices playerDataServices;

    @Inject
    private ConfigRepository configRepository;

    private final Map<String, AdvancedPortal> portalCache = new HashMap<>();

    @Inject
    TagRegistry tagRegistry;

    public void loadPortals() {
        List<String> portalNames = portalRepository.getAllNames();
        portalCache.clear();
        for (String name : portalNames) {
            AdvancedPortal portal = portalRepository.get(name);
            if (portal == null) {
                continue;
            }
            portalCache.put(name, portal);
            portal.updateBounds(portal.getMinLoc(), portal.getMaxLoc());
        }
    }

    public boolean inPortalRegionProtected(BlockLocation loc) {
        for (AdvancedPortal portal : portalCache.values()) {
            if (portal.isLocationInPortal(
                    loc, configRepository.getProtectionRadius())) {
                return true;
            }
        }
        return false;
    }

    public boolean inPortalRegionProtected(PlayerLocation loc) {
        for (AdvancedPortal portal : portalCache.values()) {
            if (portal.isLocationInPortal(
                    loc, configRepository.getProtectionRadius())) {
                return true;
            }
        }
        return false;
    }

    public boolean inPortalRegion(BlockLocation loc, int extraBlocks) {
        return inPortalRegionGetName(loc, extraBlocks) != null;
    }

    public String inPortalRegionGetName(PlayerLocation loc, int extraBlocks) {
        return inPortalRegionGetName(loc.toBlockPos(), extraBlocks);
    }

    public String inPortalRegionGetName(BlockLocation loc) {
        return inPortalRegionGetName(loc, 0);
    }

    public String inPortalRegionGetName(BlockLocation loc, int extraBlocks) {
        for (AdvancedPortal portal : portalCache.values()) {
            if (portal.isLocationInPortal(loc, extraBlocks)) {
                return portal.getName();
            }
        }
        return null;
    }

    public AdvancedPortal getPortal(String portalName) {
        return portalCache.get(portalName);
    }

    public enum PortalActivationResult {
        NOT_IN_PORTAL,
        PORTAL_TELEPORTED,
        PORTAL_ACTIVATED,
        PORTAL_DENIED
    }

    public PortalActivationResult checkPortalActivation(
        PlayerContainer player, PlayerLocation toLoc, TriggerType triggerType) {
        var blockLoc = toLoc.toBlockPos();
        var blockEntityTopLoc = blockLoc.addY(player.getHeight());
        var world = player.getWorld();
        var blockMaterial = world.getBlock(blockLoc);
        var blockEntityTopMaterial = world.getBlock(blockEntityTopLoc);
        var playerData = playerDataServices.getPlayerData(player);

        for (AdvancedPortal portal : portalCache.values()) {
            if ((portal.isLocationInPortal(toLoc)
                 && portal.isTriggerBlock(blockMaterial))
                || (portal.isLocationInPortal(blockEntityTopLoc)
                    && portal.isTriggerBlock(blockEntityTopMaterial))) {
                var portalName = portal.getName();
                if (Objects.equals(playerData.inPortal(), portalName)) {
                    return PortalActivationResult.PORTAL_DENIED;
                }
                switch (portal.activate(player, triggerType)) {
                    case SUCCESS:
                        playerData.setInPortal(portal.getName());
                        return PortalActivationResult.PORTAL_ACTIVATED;
                    case FAILED_DO_KNOCKBACK:
                        playerData.setInPortal(portal.getName());
                        var strength = configRepository.getThrowbackStrength();
                        PlayerUtils.throwPlayerBack(player, strength);
                        return PortalActivationResult.PORTAL_DENIED;
                }
            }
        }
        if (playerData.inPortal() != null) {
            playerData.setNotInPortal();
        }
        return PortalActivationResult.NOT_IN_PORTAL;
    }

    public List<String> getPortalNames() {
        return portalRepository.getAllNames();
    }

    public List<AdvancedPortal> getPortals() {
        return new ArrayList<>(portalCache.values());
    }

    public boolean removePortal(String name, PlayerContainer player) {
        this.portalCache.remove(name);
        if (this.portalRepository.containsKey(name)) {
            this.portalRepository.delete(name);
            return true;
        }
        return false;
    }

    public AdvancedPortal createPortal(PlayerContainer player,
                                       ArrayList<DataTag> tags) {
        PlayerData tempData = playerDataServices.getPlayerData(player);

        if (tempData.getPos1() == null || tempData.getPos2() == null) {
            player.sendMessage(
                Lang.getNegativePrefix()
                + Lang.translate("portal.error.selection.missing"));
            return null;
        }

        if (!tempData.getPos1().getWorldName().equals(
                tempData.getPos2().getWorldName())) {
            player.sendMessage(
                Lang.getNegativePrefix()
                + Lang.translate("portal.error.selection.differentworlds"));
            return null;
        }

        return createPortal(player, tempData.getPos1(), tempData.getPos2(),
                            tags);
    }

    /**
     * @param player if null it is assumed an automation is creating the portal so checks for permissions are not needed
     * @param pos1
     * @param pos2
     * @param tags
     * @return
     */
    public AdvancedPortal createPortal(@Nullable PlayerContainer player,
                                       BlockLocation pos1, BlockLocation pos2,
                                       List<DataTag> tags) {
        // Find the tag with the "name" NAME
        DataTag nameTag = tags.stream()
                              .filter(tag -> tag.NAME.equals(NameTag.TAG_NAME))
                              .findFirst()
                              .orElse(null);

        String name = nameTag == null ? null : nameTag.VALUES[0];
        if (nameTag == null || name == null || name.isEmpty()) {
            if (player != null)
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("command.error.noname"));
            return null;
        } else if (this.portalRepository.containsKey(name)) {
            if (player != null)
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translateInsertVariables(
                                       "command.error.nametaken", name));
            return null;
        }

        AdvancedPortal portal =
            new AdvancedPortal(pos1, pos2, tagRegistry, playerDataServices);

        for (DataTag portalTag : tags) {
            portal.setArgValues(portalTag);
        }

        for (DataTag portalTag : tags) {
            Tag.Creation creation =
                tagRegistry.getCreationHandler(portalTag.NAME);
            if (creation != null) {
                if (!creation.created(portal, player, portalTag.VALUES)) {
                    return null;
                }
            }
        }

        try {
            if (this.portalRepository.save(name, portal)) {
                this.portalCache.put(name, portal);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (player != null)
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("portal.error.save"));
        }

        return portal;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }
}
