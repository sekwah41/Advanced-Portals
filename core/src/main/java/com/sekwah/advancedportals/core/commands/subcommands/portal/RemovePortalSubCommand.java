package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.List;

public class RemovePortalSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            if (portalServices.removePortal(args[1],
                                            sender.getPlayerContainer())) {
                sender.sendMessage(
                    Lang.getPositivePrefix()
                    + Lang.translate("command.portal.remove.complete"));
            } else {
                sender.sendMessage(
                    Lang.getNegativePrefix()
                    + Lang.translate("command.portal.remove.error"));
            }
        } else {
            sender.sendMessage(Lang.translate("command.portal.remove.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.REMOVE_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return portalServices.getPortalNames();
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
