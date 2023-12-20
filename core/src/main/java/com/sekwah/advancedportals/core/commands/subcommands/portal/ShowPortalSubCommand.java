package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PortalTempDataServices;
import com.sekwah.advancedportals.core.tags.activation.NameTag;
import com.sekwah.advancedportals.core.util.Debug;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;

import java.awt.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This will be different from the old show command and I believe it is 1.16+ till the latest version as of writing this.
 */
public class ShowPortalSubCommand implements SubCommand, SubCommand.SubCommandOnInit {

    static final int SHOW_TICKS = 1010;

    boolean alternate_show_trigger = true;

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

    final Color POS1_COLOR = new Color(0, 255, 0);
    final Color POS2_COLOR = new Color(255, 0, 0);

    final Color SELECTION_COLOR = new Color(255, 0, 0, 100);

    final Color OUTLINE_COLOR = new Color(0, 255, 0, 100);

    final Color TRIGGER_COLOR = new Color(0, 0, 255, 100);

    final Color TRIGGER_OUTLINE_COLOR = new Color(0, 117, 200, 100);

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
            alternate_show_trigger = !alternate_show_trigger;
            for(PlayerContainer player : serverContainer.getPlayers()) {
                var tempData = tempDataServices.getPlayerTempData(player);
                if(!tempData.isPortalVisible()) {
                    continue;
                }


                if (tempData.getPos1() != null && tempData.getPos2() != null) {
                    debugVisuals(player, tempData.getPos1(), tempData.getPos2(), SELECTION_COLOR, SHOW_TICKS);
                }

                if(tempData.getPos1() != null) {
                    Debug.addMarker(player, tempData.getPos1(), "Pos1", POS1_COLOR, SHOW_TICKS);
                }
                if(tempData.getPos2() != null) {
                    Debug.addMarker(player, tempData.getPos2(), "Pos2", POS2_COLOR, SHOW_TICKS);
                }

                var world = player.getWorld();
                for (var portal : portalServices.getPortals()) {
                    if(portal.isLocationInPortal(player.getLoc(), config.getVisibleRange())) {
                        BlockLocation minLoc = portal.getMinLoc();
                        BlockLocation maxLoc = portal.getMaxLoc();
                        int midX = (minLoc.posX + maxLoc.posX) / 2;
                        int midZ = (minLoc.posZ + maxLoc.posZ) / 2;
                        BlockLocation midPoint = new BlockLocation(minLoc.worldName, midX, maxLoc.posY, midZ);
                        Color color;
                        if(portal.isTriggerBlock(world.getBlock(midPoint))) {
                            color = TRIGGER_OUTLINE_COLOR;
                        } else {
                            if(midPoint.posX == minLoc.posX || midPoint.posX == maxLoc.posX || midPoint.posZ == minLoc.posZ || midPoint.posZ == maxLoc.posZ)
                                color = OUTLINE_COLOR;
                            else
                                color = new Color(0, 0, 0, 0);
                        }
                        debugVisuals(player, portal, OUTLINE_COLOR, SHOW_TICKS, TRIGGER_COLOR);
                        Debug.addMarker(player, midPoint, portal.getArgValues(NameTag.TAG_NAME)[0], color, SHOW_TICKS);
                    }
                }
            }
        }, 1, 20);
    }

    private void debugVisuals(PlayerContainer player, BlockLocation pos1, BlockLocation pos2, Color color, int time) {
        debugVisuals(player, pos1, pos2, color, time, null, null);
    }

    private void debugVisuals(PlayerContainer player, AdvancedPortal portal, Color color, int time, Color triggerColor) {
        debugVisuals(player, portal.getMinLoc(), portal.getMaxLoc(), color, time, triggerColor, portal);
    }

    private void debugVisuals(PlayerContainer player, BlockLocation pos1, BlockLocation pos2, Color color, int time, Color triggerColor, AdvancedPortal portal) {
        int minX = Math.min(pos1.posX, pos2.posX);
        int minY = Math.min(pos1.posY, pos2.posY);
        int minZ = Math.min(pos1.posZ, pos2.posZ);

        int maxX = Math.max(pos1.posX, pos2.posX);
        int maxY = Math.max(pos1.posY, pos2.posY);
        int maxZ = Math.max(pos1.posZ, pos2.posZ);

        var world = player.getWorld();


        int widthX = maxX - minX + 1;
        int widthY = maxY - minY + 1;
        int widthZ = maxZ - minZ + 1;

        int totalBlocks = widthX * widthY * widthZ;

        if(totalBlocks <= config.getMaxTriggerVisualisationSize()) {
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        var pos = new BlockLocation(pos1.worldName, x, y, z);
                        boolean isTrigger = portal != null && portal.isTriggerBlock(world.getBlock(pos));
                        System.out.println(world.getBlock(pos));
                        boolean isOutline = (y == minY || y == maxY) && (x == minX || x == maxX || z == minZ || z == maxZ) || (z == minZ || z == maxZ) && (x == minX || x == maxX);
                        if (isTrigger && isOutline && alternate_show_trigger) {
                            Debug.addMarker(player, pos, "", TRIGGER_OUTLINE_COLOR, time);
                        } else if (isOutline) {
                            Debug.addMarker(player, pos, "", color, time);
                        } else if(isTrigger) {
                            if(alternate_show_trigger)
                                Debug.addMarker(player, pos, "", triggerColor, time);
                        }
                    }
                }
            }
        } else {

            for (int x = minX; x <= maxX; x++) {
                Debug.addMarker(player, new BlockLocation(pos1.worldName, x, minY, minZ), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, x, minY, maxZ), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, x, maxY, minZ), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, x, maxY, maxZ), "", color, time);
            }
            for (int z = minZ + 1; z < maxZ; z++) {
                Debug.addMarker(player, new BlockLocation(pos1.worldName, minX, minY, z), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, maxX, minY, z), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, minX, maxY, z), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, maxX, maxY, z), "", color, time);
            }

            for (int y = minY + 1; y < maxY; y++) {
                Debug.addMarker(player, new BlockLocation(pos1.worldName, minX, y, minZ), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, maxX, y, minZ), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, minX, y, maxZ), "", color, time);
                Debug.addMarker(player, new BlockLocation(pos1.worldName, maxX, y, maxZ), "", color, time);
            }
        }
    }
}
