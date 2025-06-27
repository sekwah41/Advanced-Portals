package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.Matrix;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * This will be different from the old show command and I believe it is 1.16+
 * till the latest version as of writing this.
 */
public class ShowDestiSubCommand
    implements SubCommand, SubCommand.SubCommandOnInit {
    @Inject
    PlayerDataServices tempDataServices;

    @Inject
    GameScheduler gameScheduler;

    @Inject
    AdvancedPortalsCore core;

    @Inject
    DestinationServices destinationServices;

    @Inject
    ServerContainer serverContainer;

    @Inject
    ConfigRepository config;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerData tempData =
            tempDataServices.getPlayerData(sender.getPlayerContainer());
        if (tempData.isDestiVisible()) {
            sender.sendMessage(
                Lang.getNegativePrefix()
                + Lang.translate("command.destination.show.disabled"));
        } else {
            sender.sendMessage(
                Lang.getPositivePrefix()
                + Lang.translate("command.destination.show.enabled"));
        }
        tempData.setDestiVisible(!tempData.isDestiVisible());
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.SHOW_DESTI.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.destination.show.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.destination.show.detailedhelp");
    }

    @Override
    public void registered() {
        gameScheduler.intervalTickEvent("show_portal", () -> {
            for (PlayerContainer player : serverContainer.getPlayers()) {
                PlayerData tempData = tempDataServices.getPlayerData(player);
                if (!tempData.isDestiVisible()) {
                    continue;
                }

                for (Destination destination :
                     destinationServices.getDestinations()) {
                    PlayerLocation pos = destination.getLoc();
                    if (Objects.equals(pos.getWorldName(),
                                       player.getWorldName())
                        && pos.distanceTo(player.getLoc())
                            < config.getShowVisibleRange()) {
                        drawArrow(player, pos, new Color(255, 221, 0));
                    }
                }
            }
        }, 1, 5);
    }

    // The arrow will be rotated around the player at 0 0 0 facing forwards in
    // the positive Z direction
    final Vector BASE = new Vector(0, 0, -1);
    final Vector TIP = new Vector(0, 0, 1);
    final Vector LEFT = new Vector(0.7, 0, 0.2);
    final Vector RIGHT = new Vector(-0.7, 0, 0.2);

    public void drawArrow(PlayerContainer player, PlayerLocation playerLocation,
                          Color color) {
        // Draw three lines to create an arrow, applying the yaw and pitch
        // to the direction of the arrow
        Matrix rotation = Matrix.identity()
                              .rotY(-playerLocation.getYaw())
                              .rotX(playerLocation.getPitch());

        Vector location = playerLocation.add(new Vector(0, 1.5, 0));

        // Rotate the base values for the arrow
        Vector base = rotation.transform(BASE).add(location);
        Vector tip = rotation.transform(TIP).add(location);
        Vector left = rotation.transform(LEFT).add(location);
        Vector right = rotation.transform(RIGHT).add(location);

        player.drawLine(base, tip, color, 0.25f);
        player.drawLine(tip, left, color, 0.25f);
        player.drawLine(tip, right, color, 0.25f);
    }
}
