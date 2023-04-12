package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoveSubCommand implements SubCommand {


    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            // TODO sort portal services
            if(portalServices.removePortal(args[1], sender.getPlayerContainer())) {
                sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.remove.complete"));
            }
            else {
                sender.sendMessage(Lang.translate("messageprefix.negative")
                        + Lang.translate("command.remove.error"));
            }
        }
        else {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translate("command.remove.noname"));
            }
            else {
                if(portalServices.removePlayerSelection(player)) {

                }
                else {
                    sender.sendMessage(Lang.translate("messageprefix.negative")
                            + Lang.translate("command.remove.error"));
                }
            }
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.createportal");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        List<String> portalNames = new ArrayList<>();
        for(Map.Entry<String, AdvancedPortal> portal : portalServices.getPortals()) {
            portalNames.add(portal.getKey());
        }
        Collections.sort(portalNames);
        return portalNames;
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
