package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoveDestiSubCommand implements SubCommand {


    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            if(destinationServices.removeDesti(args[1], sender.getPlayerContainer())) {
                sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.portal.remove.complete"));
            }
            else {
                sender.sendMessage(Lang.translate("messageprefix.negative")
                        + Lang.translate("command.destination.remove.error"));
            }
        }
        else {
            sender.sendMessage(Lang.translate("command.portal.remove.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || PortalPermissions.CREATE_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if(args.length > 2) {
            return Collections.emptyList();
        }
        List<String> destiNames = destinationServices.getDestinations();
        Collections.sort(destiNames);
        return destiNames;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.create.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.create.detailedhelp");
    }
}
