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
import com.sekwah.advancedportals.core.serializeddata.Vector;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;
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
        if (core.getMcVersion()[1] < 16) {
            sender.sendMessage(
                Lang.getNegativePrefix()
                + Lang.translate("command.portal.show.unsupported"));
            return;
        }

        var tempData =
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
                var tempData = tempDataServices.getPlayerData(player);
                if (!tempData.isDestiVisible()) {
                    continue;
                }

                var size = 0.2;

                for (Destination destination :
                     destinationServices.getDestinations()) {
                    var pos = destination.getLoc();
                    if (Objects.equals(pos.getWorldName(),
                                       player.getWorldName())
                        && pos.distanceTo(player.getLoc())
                            < config.getShowVisibleRange()) {
                        player.spawnColoredDust(
                                pos.add(new Vector(0, 0.5, 0)), size, size, size, 20,
                            new Color(255, 221, 0));

                    } }
                }
        }, 1, 20);
    }
}
