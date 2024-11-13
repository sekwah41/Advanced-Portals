package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;

import java.util.List;

public class DisableBeaconSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            var portalName = args[1];
            var portal = portalServices.getPortal(portalName);
            if(portal == null) {
                sender.sendMessage(Lang.getNegativePrefix()
                        + Lang.translateInsertVariables("command.portal.disablebeacon.notfound", portalName));
                return;
            }
            sender.sendMessage(Lang.getPositivePrefix()
                    + Lang.translateInsertVariables("command.portal.disablebeacon.complete", portalName));
            sender.getPlayerContainer().getWorld().disableBeacon(portal);
        } else {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translate("command.portal.disablebeacon.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return portalServices.getPortalNames();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.disablebeacon.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.disablebeacon.help");
    }
}
