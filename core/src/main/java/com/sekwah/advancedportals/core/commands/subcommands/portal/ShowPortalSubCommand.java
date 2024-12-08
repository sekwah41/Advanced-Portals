package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * This will be different from the old show command and I believe it is 1.16+
 * till the latest version as of writing this.
 */
public class ShowPortalSubCommand
    implements SubCommand, SubCommand.SubCommandOnInit {

    boolean alternate_show_trigger = true;

    @Inject
    PlayerDataServices playerDataServices;

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

    final Color SELECTION_COLOR = new Color(166, 166, 166, 255);

    final Color OUTLINE_COLOR = new Color(0, 255, 0, 100);

    final Color TRIGGER_COLOR = new Color(0, 0, 255, 100);

    final Vector OFFSET = new Vector(0.5, 0.5, 0.5);

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (core.getMcVersion()[1] < 16) {
            sender.sendMessage(
                Lang.getNegativePrefix()
                + Lang.translate("command.portal.show.unsupported"));
            return;
        }

        var tempData =
            playerDataServices.getPlayerData(sender.getPlayerContainer());
        if (tempData.isPortalVisible()) {
            sender.sendMessage(
                Lang.getNegativePrefix()
                + Lang.translate("command.portal.show.disabled"));
        } else {
            sender.sendMessage(Lang.getPositivePrefix()
                               + Lang.translate("command.portal.show.enabled"));
        }
        tempData.setPortalVisible(!tempData.isPortalVisible());
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.SHOW_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
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
            for (PlayerContainer player : serverContainer.getPlayers()) {
                var tempData = playerDataServices.getPlayerData(player);

                if (!tempData.isPortalVisible()) {
                    continue;
                }

                var pos1 = tempData.getPos1();
                var pos2 = tempData.getPos2();

                if (pos1 != null && pos2 != null
                    && pos1.getWorldName().equals(
                        player.getWorldName())
                    && pos2.getWorldName().equals(
                        player.getWorldName())) {
                    int widthX = Math.abs(pos1.getPosX()
                                          - pos2.getPosX());
                    int widthY = Math.abs(pos1.getPosY()
                                          - pos2.getPosY());
                    int widthZ = Math.abs(pos1.getPosZ()
                                          - pos2.getPosZ());
                    int totalBlocks = widthX * widthY * widthZ;
                    if (totalBlocks <= config.maxPortalVisualisationSize())
                        debugVisuals(player, pos1,
                                     pos2, SELECTION_COLOR);
                }

                if (pos1 != null
                    && pos1.getWorldName().equals(
                        player.getWorldName())) {
                    drawBox(player, pos1, pos1, POS1_COLOR, 0.25f);
                }
                if (pos2 != null
                    && pos2.getWorldName().equals(
                        player.getWorldName())) {
                    drawBox(player, pos2, pos2, POS2_COLOR, 0.25f);
                }

                // If both are selected and both worlds are the same as the player
                if(pos1 != null && pos2 != null &&
                        pos1.getWorldName().equals(player.getWorldName()) &&
                        pos2.getWorldName().equals(player.getWorldName())) {
                    if(pos1.distanceTo(pos2) <= config.maxSelectionVisualisationSize()) {
                        drawBox(player, pos1, pos2, SELECTION_COLOR, 1f);
                    }
                }

                for (var portal : portalServices.getPortals()) {
                    if (Objects.equals(portal.getMinLoc().getWorldName(),
                                       player.getWorldName())
                        && portal.isLocationInPortal(
                            player.getLoc(), config.getShowVisibleRange())) {
                        debugVisuals(player, portal, OUTLINE_COLOR,
                                     TRIGGER_COLOR);
                    }
                }
            }
        }, 1,5);
    }

    private void debugVisuals(PlayerContainer player, BlockLocation pos1,
                              BlockLocation pos2, Color color) {
        debugVisuals(player, pos1, pos2, color, null, null);
    }

    private void debugVisuals(PlayerContainer player, AdvancedPortal portal,
                              Color color, Color triggerColor) {
        debugVisuals(player, portal.getMinLoc(), portal.getMaxLoc(), color, triggerColor, portal);
    }

    private void drawBox(PlayerContainer player, BlockLocation pos1,
                         BlockLocation pos2, Color color, float particleDensity) {
        int minX = Math.min(pos1.getPosX(), pos2.getPosX());
        int minY = Math.min(pos1.getPosY(), pos2.getPosY());
        int minZ = Math.min(pos1.getPosZ(), pos2.getPosZ());

        int maxX = Math.max(pos1.getPosX(), pos2.getPosX()) + 1;
        int maxY = Math.max(pos1.getPosY(), pos2.getPosY()) + 1;
        int maxZ = Math.max(pos1.getPosZ(), pos2.getPosZ()) + 1;

        player.drawLine(new Vector(minX, maxY, maxZ), new Vector(maxX, maxY, maxZ), color, particleDensity);
        player.drawLine(new Vector(minX, minY, minZ), new Vector(maxX, minY, minZ), color, particleDensity);
        player.drawLine(new Vector(minX, minY, minZ), new Vector(minX, maxY, minZ), color, particleDensity);
        player.drawLine(new Vector(maxX, minY, maxZ), new Vector(maxX, maxY, maxZ), color, particleDensity);
        player.drawLine(new Vector(maxX, maxY, minZ), new Vector(maxX, maxY, maxZ), color, particleDensity);
        player.drawLine(new Vector(minX, minY, minZ), new Vector(minX, minY, maxZ), color, particleDensity);
        player.drawLine(new Vector(maxX, minY, minZ), new Vector(maxX, maxY, minZ), color, particleDensity);
        player.drawLine(new Vector(maxX, minY, minZ), new Vector(maxX, minY, maxZ), color, particleDensity);
        player.drawLine(new Vector(minX, maxY, minZ), new Vector(maxX, maxY, minZ), color, particleDensity);
        player.drawLine(new Vector(minX, maxY, minZ), new Vector(minX, maxY, maxZ), color, particleDensity);
        player.drawLine(new Vector(minX, minY, maxZ), new Vector(maxX, minY, maxZ), color, particleDensity);
        player.drawLine(new Vector(minX, minY, maxZ), new Vector(minX, maxY, maxZ), color, particleDensity);

    }

    private void debugVisuals(PlayerContainer player, BlockLocation pos1,
                              BlockLocation pos2, Color color,
                              Color triggerColor, AdvancedPortal portal) {
        int minX = Math.min(pos1.getPosX(), pos2.getPosX());
        int minY = Math.min(pos1.getPosY(), pos2.getPosY());
        int minZ = Math.min(pos1.getPosZ(), pos2.getPosZ());

        int maxX = Math.max(pos1.getPosX(), pos2.getPosX());
        int maxY = Math.max(pos1.getPosY(), pos2.getPosY());
        int maxZ = Math.max(pos1.getPosZ(), pos2.getPosZ());

        var size = pos1.getSize(pos2);

        var world = player.getWorld();

        if (size <= config.maxPortalVisualisationSize()) {
            drawBox(player, pos1, pos2, color, 0.5f);

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        var pos =
                            new BlockLocation(pos1.getWorldName(), x, y, z);
                        boolean isTrigger = portal != null
                            && portal.isTriggerBlock(world.getBlock(pos));
                        if (isTrigger && alternate_show_trigger)
                            player.spawnColoredDust(pos.toVector().add(OFFSET), 0.2, 0.2, 0.2, 1,
                                                triggerColor);

                    }
                }
            }
        }
    }
}
