package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerTempData;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PortalTempDataServices;
import com.sekwah.advancedportals.core.tags.activation.NameTag;
import com.sekwah.advancedportals.core.util.Debug;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;

import java.awt.*;
import java.util.List;

/**
 * This will be different from the old show command and I believe it is 1.16+ till the latest version as of writing this.
 */
public class ShowPortalSubCommand implements SubCommand, SubCommand.SubCommandOnInit {

    static final int SHOW_TICKS = 1300;

    @Inject
    PortalTempDataServices tempDataServices;

    @Inject
    GameScheduler gameScheduler;

    @Inject
    AdvancedPortalsCore core;

    @Inject
    ServerContainer serverContainer;

    @Inject
    PortalServices portalServices;

    @Inject
    ConfigRepository config;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(core.getMcVersion()[1] < 16) {
            sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.portal.show.unsupported"));
            return;
        }

        var tempData = tempDataServices.getPlayerTempData(sender.getPlayerContainer());
        if(tempData.isPortalVisible()) {
            sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.portal.show.disabled"));
        } else {
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.portal.show.enabled"));
        }
        tempData.setPortalVisible(!tempData.isPortalVisible());
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.show.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.show.detailedhelp");
    }

    @Override
    public void registered() {
        gameScheduler.intervalTickEvent("show_portal", () -> {
            for(PlayerContainer player : serverContainer.getPlayers()) {
                var tempData = tempDataServices.getPlayerTempData(player);
                if(!tempData.isPortalVisible()) {
                    continue;
                }


                if(tempData.getPos1() != null) {
                    Debug.addMarker(player, tempData.getPos1(), "Pos1", new Color(0, 255, 0), SHOW_TICKS);
                }
                if(tempData.getPos2() != null) {
                    Debug.addMarker(player, tempData.getPos2(), "Pos2", new Color(255, 0, 0), SHOW_TICKS);
                }

                if (tempData.getPos1() != null && tempData.getPos2() != null) {
                    debugPortal(player, tempData.getPos1(), tempData.getPos2(), new Color(255, 0, 0, 100), SHOW_TICKS, true);
                }

                for (var portal : portalServices.getPortals()) {
                    if(portal.isLocationInPortal(player.getLoc(), config.getVisibleRange())) {
                        BlockLocation minLoc = portal.getMinLoc();
                        BlockLocation maxLoc = portal.getMaxLoc();
                        int midX = (minLoc.posX + maxLoc.posX) / 2;
                        int midZ = (minLoc.posZ + maxLoc.posZ) / 2;
                        BlockLocation midPoint = new BlockLocation(minLoc.worldName, midX, maxLoc.posY, midZ);
                        var color = new Color(0, 255, 0, 100);
                        debugPortal(player, portal.getMinLoc(), portal.getMaxLoc(), color, 1000, false);
                        Debug.addMarker(player, midPoint, portal.getArgValues(NameTag.TAG_NAME)[0], color, SHOW_TICKS);
                    }
                }
            }
        }, 1, 20);
    }

    private static void debugPortal(PlayerContainer player, BlockLocation pos1, BlockLocation pos2, Color color, int time, boolean hideCorners) {
        int minX = Math.min(pos1.posX, pos2.posX);
        int minY = Math.min(pos1.posY, pos2.posY);
        int minZ = Math.min(pos1.posZ, pos2.posZ);

        int maxX = Math.max(pos1.posX, pos2.posX);
        int maxY = Math.max(pos1.posY, pos2.posY);
        int maxZ = Math.max(pos1.posZ, pos2.posZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if ((y == minY || y == maxY) && (x == minX || x == maxX || z == minZ || z == maxZ) || (z == minZ || z == maxZ) && (x == minX || x == maxX)) {
                        var pos = new BlockLocation(pos1.worldName, x, y, z);
                        if ((pos.equals(pos1) || pos.equals(pos2)) && hideCorners)
                            continue;
                        Debug.addMarker(player, pos, "", color, time);
                    }
                }
            }
        }
    }
}
