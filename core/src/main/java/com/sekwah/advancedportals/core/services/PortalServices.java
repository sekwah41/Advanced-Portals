package com.sekwah.advancedportals.core.services;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.tags.activation.NameTag;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.PlayerUtils;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.*;
import javax.inject.Singleton;

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
        for (AdvancedPortal portal : portalCache.values()) {
            if (portal.isLocationInPortal(loc, extraBlocks)) {
                return true;
            }
        }
        return false;
    }

    public void playerMove(PlayerContainer player, PlayerLocation toLoc) {
        var blockLoc = toLoc.toBlockPos();
        var blockEntityTopLoc = blockLoc.addY(player.getHeight());
        var world = player.getWorld();
        var blockMaterial = world.getBlock(blockLoc);
        var blockEntityTopMaterial = world.getBlock(blockEntityTopLoc);

        var notInPortal = true;
        for (AdvancedPortal portal : portalCache.values()) {
            if ((portal.isLocationInPortal(toLoc)
                 && portal.isTriggerBlock(blockMaterial))
                || (portal.isLocationInPortal(blockEntityTopLoc)
                    && portal.isTriggerBlock(blockEntityTopMaterial))) {
                notInPortal = false;
                if (portal.activate(player, true)) {
                    return;
                }
            }
        }
        var playerData = playerDataServices.getPlayerData(player);
        if (!notInPortal) {
            var strength = configRepository.getThrowbackStrength();
            PlayerUtils.throwPlayerBack(player, strength);
        }
        if (playerData.isInPortal() && notInPortal) {
            playerData.setInPortal(false);
        }
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

    public AdvancedPortal createPortal(BlockLocation pos1, BlockLocation pos2,
                                       List<DataTag> tags) {
        return createPortal(null, pos1, pos2, tags);
    }

    public AdvancedPortal createPortal(PlayerContainer player,
                                       ArrayList<DataTag> tags) {
        PlayerData tempData = playerDataServices.getPlayerData(player);

        if (tempData.getPos1() == null || tempData.getPos2() == null) {
            player.sendMessage(
                Lang.translate("messageprefix.negative")
                + Lang.translate("portal.error.selection.missing"));
            return null;
        }

        if (!tempData.getPos1().getWorldName().equals(
                tempData.getPos2().getWorldName())) {
            player.sendMessage(
                Lang.translate("messageprefix.negative")
                + Lang.translate("portal.error.selection.differentworlds"));
            return null;
        }

        return createPortal(player, tempData.getPos1(), tempData.getPos2(),
                            tags);
    }

    public AdvancedPortal createPortal(PlayerContainer player,
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
                player.sendMessage(Lang.translate("messageprefix.negative")
                                   + Lang.translate("command.error.noname"));
            return null;
        } else if (this.portalRepository.containsKey(name)) {
            if (player != null)
                player.sendMessage(Lang.translate("messageprefix.negative")
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
                player.sendMessage(Lang.translate("messageprefix.negative")
                                   + Lang.translate("portal.error.save"));
        }

        return portal;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }
}