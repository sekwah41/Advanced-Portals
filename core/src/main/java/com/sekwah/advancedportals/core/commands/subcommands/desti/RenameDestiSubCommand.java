package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.Collections;
import java.util.List;

public class RenameDestiSubCommand implements SubCommand {

    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 2) {
            String oldName = args[1];
            String newName = args[2];
            if (destinationServices.renameDestination(oldName, newName, sender.getPlayerContainer())) {
                sender.sendMessage(Lang.getPositivePrefix() + Lang.translateInsertVariables("command.destination.rename.complete", oldName, newName));
            }
        } else {
            sender.sendMessage(Lang.getNegativePrefix() + Lang.translate("command.destination.rename.usage"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.RENAME_DESTI.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if (args.length == 1) {
            List<String> destiNames = destinationServices.getDestinationNames();
            Collections.sort(destiNames);
            return destiNames;
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.destination.rename.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.destination.rename.detailedhelp");
    }
}
