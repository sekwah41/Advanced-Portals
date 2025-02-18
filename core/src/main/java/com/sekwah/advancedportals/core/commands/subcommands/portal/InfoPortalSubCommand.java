package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;
import java.util.List;

public class InfoPortalSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            String portalName = args[1];
            AdvancedPortal portal = portalServices.getPortal(portalName);
            if (portal == null) {
                sender.sendMessage(
                    Lang.getNegativePrefix()
                    + Lang.translateInsertVariables(
                        "command.portal.info.notfound", portalName));
                return;
            }
            sender.sendMessage(Lang.getPositivePrefix()
                               + Lang.translateInsertVariables(
                                   "command.portal.info.complete", portalName));
            TagReader.printArgs(sender, portal.getArgs());
        } else {
            sender.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("command.portal.info.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.PORTAL_INFO.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return portalServices.getPortalNames();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.list.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.list.help");
    }
}
