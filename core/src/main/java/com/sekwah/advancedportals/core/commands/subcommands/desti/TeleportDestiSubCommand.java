package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.Collections;
import java.util.List;

public class TeleportDestiSubCommand implements SubCommand {
    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            if (destinationServices.teleportToDestination(
                    args[1], sender.getPlayerContainer())) {
                sender.sendMessage(
                    Lang.getPositivePrefix()
                    + Lang.translate("command.destination.teleport.success")
                          .replaceAll("@destiname", args[1]));
            } else {
                sender.sendMessage(
                    Lang.getNegativePrefix()
                    + Lang.translate("command.destination.teleport.error")
                          .replaceAll("@destiname", args[1]));
            }
        } else {
            sender.sendMessage(Lang.translate("command.destination.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.TELEPORT_DESTI.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        if (args.length > 2) {
            return Collections.emptyList();
        }
        List<String> destiNames = destinationServices.getDestinationNames();
        Collections.sort(destiNames);
        return destiNames;
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
