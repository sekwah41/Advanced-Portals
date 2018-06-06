package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoveSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            try {
                AdvancedPortalsCore.getPortalServices().removePortal(args[1], sender.getPlayerContainer());
                    sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("command.remove.complete"));
            } catch (PortalException portalTagExeption) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative")
                        + Lang.translateColor("command.remove.error") + " " + Lang.translate(portalTagExeption.getMessage()));
            }
        }
        else {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translate("command.remove.noname"));
            }
            else {
                try {
                    AdvancedPortalsCore.getPortalServices().removePlayerSelection(player);
                } catch (PortalException portalTagExeption) {
                    sender.sendMessage(Lang.translateColor("messageprefix.negative")
                            + Lang.translateColor("command.remove.error") + " " + Lang.translate(portalTagExeption.getMessage()));
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
        for(Map.Entry<String, AdvancedPortal> portal : AdvancedPortalsCore.getPortalServices().getPortals()) {
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
