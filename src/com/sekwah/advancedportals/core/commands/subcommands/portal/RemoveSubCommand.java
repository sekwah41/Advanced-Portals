package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.portal.PortalTag;
import com.sekwah.advancedportals.core.api.portal.PortalTagExeption;
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


        }
        else {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translate("command.remove.noname"));
            }
            else {
                try {
                    AdvancedPortalsCore.getPortalManager().removePlayerSelection(player);
                } catch (PortalTagExeption portalTagExeption) {
                    sender.sendMessage(Lang.translateColor("messageprefix.negative")
                            + Lang.translateColor("command.remove.error") + " " + portalTagExeption.getMessage());
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
        for(Map.Entry<String, AdvancedPortal> portal : AdvancedPortalsCore.getPortalManager().getPortals()) {
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
