package com.sekwah.advancedportals.core.services;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.PlayerTempData;
import com.sekwah.advancedportals.core.tags.activation.NameTag;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class PortalServices {

    @Inject
    InfoLogger infoLogger;

    @Inject
    private IPortalRepository portalRepository;

    @Inject
    private PortalTempDataServices portalTempDataServices;

    private final Map<String, AdvancedPortal> portalCache = new HashMap<>();

    @Inject
    TagRegistry tagRegistry;

    public void loadPortals() {
        List<String> portalNames = portalRepository.getAllNames();
        portalCache.clear();
        for (String name : portalNames) {
            AdvancedPortal portal = portalRepository.get(name);
            portalCache.put(name, portal);
            portal.updateBounds(portal.getMinLoc(), portal.getMaxLoc());
        }

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }

    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }

    public List<String> getPortalNames() {
        return portalRepository.getAllNames();
    }

    public List<AdvancedPortal> getPortals() {
        return new ArrayList<>(portalCache.values());
    }

    public boolean removePortal(String name, PlayerContainer player) {
        this.portalCache.remove(name);
        if(this.portalRepository.containsKey(name)) {
            this.portalRepository.delete(name);
            return true;
        }
        return false;
    }

    public AdvancedPortal createPortal(PlayerContainer player, ArrayList<DataTag> tags) {
        // Find the tag with the "name" NAME
        DataTag nameTag = tags.stream().filter(tag -> tag.NAME.equals(NameTag.TAG_NAME)).findFirst().orElse(null);

        String name = nameTag == null ? null : nameTag.VALUES[0];
        if(nameTag == null || name == null || name.isEmpty()) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.error.noname"));
            return null;
        }
        else if(this.portalRepository.containsKey(name)) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translateInsertVariables("command.error.nametaken", name));
            return null;
        }

        PlayerTempData tempData = portalTempDataServices.getPlayerTempData(player);

        if(tempData.getPos1() == null || tempData.getPos2() == null) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.error.selection.missing"));
            return null;
        }

        if(!tempData.getPos1().worldName.equals(tempData.getPos2().worldName)) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.error.selection.differentworlds"));
            return null;
        }

        AdvancedPortal portal = new AdvancedPortal(tempData.getPos1(), tempData.getPos2());

        for (DataTag portalTag : tags) {
            portal.setArgValues(portalTag);
        }

        for (DataTag portalTag : tags) {
            Tag.Creation creation = tagRegistry.getCreationHandler(portalTag.NAME);
            if(creation != null) {
                if(!creation.created(portal, player, portalTag.VALUES)) {
                    return null;
                }
            }
        }

        try {
            if(this.portalRepository.save(name, portal)) {
                this.portalCache.put(name, portal);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.error.save"));
        }

        return portal;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }
}
