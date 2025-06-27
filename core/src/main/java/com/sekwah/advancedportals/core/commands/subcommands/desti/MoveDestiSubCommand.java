package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.Collections;
import java.util.List;

public class MoveDestiSubCommand implements SubCommand {
    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (!hasPermission(sender)) {
            sender.sendMessage(Lang.translate("command.nopermission"));
            return;
        }
        if (!(sender instanceof PlayerContainer)) {
            sender.sendMessage(Lang.translate("command.playeronly"));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(getBasicHelpText());
            return;
        }
        String destName = args[1];
        Destination destination = destinationServices.getDestination(destName);
        if (destination == null) {
            sender.sendMessage(Lang.translate("command.destination.notfound"));
            return;
        }
        PlayerContainer player = (PlayerContainer) sender;
        destination.setLoc(player.getLoc());
        destinationServices.changeDestinationLocation(destName, player.getLoc(), player);
        sender.sendMessage(Lang.translate("command.destination.moved"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.MOVE_DESTI.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if (args.length == 2) {
            return destinationServices.getDestinationNames();
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.desti.move.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.desti.move.detailedhelp");
    }
}
