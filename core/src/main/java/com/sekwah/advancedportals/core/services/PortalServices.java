package com.sekwah.advancedportals.core.services;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.serializeddata.PlayerTempData;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PortalServices {

    @Inject
    InfoLogger infoLogger;

    @Inject
    private IPortalRepository portalRepository;

    @Inject
    private PortalTempDataServices portalTempDataServices;

    private final Map<String, AdvancedPortal> portalCache = new HashMap<>();

    public void loadPortals() {

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }

    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }

    public ImmutableList<? extends Map.Entry<String, AdvancedPortal>> getPortals() {
        return ImmutableList.copyOf(Collections.emptyList());
    }

    public boolean removePortal(String name, PlayerContainer player) {
        return false;
    }

    public AdvancedPortal createPortal(PlayerContainer player, ArrayList<DataTag> tags) {
        // Find the tag with the "name" NAME
        DataTag nameTag = tags.stream().filter(tag -> tag.NAME.equals("name")).findFirst().orElse(null);

        String name = nameTag == null ? null : nameTag.VALUES[0];
        if(nameTag == null) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("desti.error.noname"));
            return null;
        }

        if(name == null || name.equals("")) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.error.noname"));
            return null;
        }
        else if(this.portalRepository.containsKey(name)) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translateInsertVariables("command.error.nametaken", name));
            return null;
        }

        PlayerTempData tempData = portalTempDataServices.getPlayerTempData(player);

        if(tempData.getPos1() == null || tempData.getPos2() == null) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.selector.error.missing"));
            return null;
        }

        if(!tempData.getPos1().worldName.equals(tempData.getPos2().worldName)) {
            player.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("portal.selector.error.worlds"));
            return null;
        }

        AdvancedPortal portal = new AdvancedPortal(tempData.getPos1(), tempData.getPos2());

        return null;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }
}
