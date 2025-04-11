package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.EntityContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TeleportDestiSubCommand implements SubCommand {
    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            if (args.length > 2
                && Permissions.TELEPORT_PLAYER_DESTI.hasPermission(sender)) {
                PlayerContainer targetPlayer =
                    sender.getServer().getPlayer(args[2]);
                if (destinationServices.teleportToDestination(args[1],
                                                              targetPlayer)) {
                    sender.sendMessage(
                        Lang.getPositivePrefix()
                        + Lang.translateInsertVariables(
                            "command.destination.teleportplayer.success",
                            args[2], args[1]));

                    targetPlayer.sendMessage(
                        Lang.getPositivePrefix()
                        + Lang.translateInsertVariables(
                                  "command.destination.teleport.success",
                                  args[1])
                              // leave this in case they have the old
                              // translation strings
                              .replaceAll("@destiname", args[1]));
                } else {
                    sender.sendMessage(
                        Lang.getNegativePrefix()
                        + Lang.translateInsertVariables(
                            "command.destination.teleportplayer.error", args[2],
                            args[1]));
                }
            } else {
                if (destinationServices.teleportToDestination(
                        args[1], sender.getPlayerContainer())) {
                    sender.sendMessage(
                        Lang.getPositivePrefix()
                        + Lang.translateInsertVariables(
                                  "command.destination.teleport.success",
                                  args[1])
                              // leave this in case they have the old
                              // translation strings
                              .replaceAll("@destiname", args[1]));
                } else {
                    sender.sendMessage(
                        Lang.getNegativePrefix()
                        + Lang.translateInsertVariables(
                                  "command.destination.teleport.error", args[1])
                              // leave this in case they have the old
                              // translation strings
                              .replaceAll("@destiname", args[1]));
                }
            }
        } else {
            sender.sendMessage(Lang.getNegativePrefix()
                               // should probably be translateInsertVariables
                               // but this predates that. Leave it for now
                               + Lang.translate("command.destination.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.TELEPORT_DESTI.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        if (args.length == 2) {
            List<String> destiNames = destinationServices.getDestinationNames();
            Collections.sort(destiNames);
            return destiNames;
        } else if (args.length == 3
                   && Permissions.TELEPORT_PLAYER_DESTI.hasPermission(sender)) {
            return Arrays.stream(sender.getServer().getPlayers())
                .map(EntityContainer::getName)
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.destination.teleport.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.destination.teleport.detailedhelp");
    }
}
