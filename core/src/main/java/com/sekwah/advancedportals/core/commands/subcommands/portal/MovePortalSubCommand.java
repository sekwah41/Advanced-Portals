package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.Collections;
import java.util.List;

public class MovePortalSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;
    @Inject
    PlayerDataServices playerDataServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Lang.getNegativePrefix() + "Usage: /portal move <name>");
            return;
        }
        PlayerContainer player = sender.getPlayerContainer();
        if (player == null) {
            sender.sendMessage(Lang.getNegativePrefix() + Lang.translate("command.playeronly"));
            return;
        }
        String portalName = args[1];
        AdvancedPortal portal = portalServices.getPortal(portalName);
        if (portal == null) {
            sender.sendMessage(Lang.getNegativePrefix() + "Portal not found: " + portalName);
            return;
        }
        PlayerData tempData = playerDataServices.getPlayerData(player);
        BlockLocation pos1 = tempData.getPos1();
        BlockLocation pos2 = tempData.getPos2();
        if (pos1 == null || pos2 == null) {
            sender.sendMessage(Lang.translate("portal.error.selection.missing"));
            return;
        }

        if (!tempData.getPos1().getWorldName().equals(
                tempData.getPos2().getWorldName())) {
            player.sendMessage(
                    Lang.getNegativePrefix()
                            + Lang.translate("portal.error.selection.differentworlds"));
            return;
        }

        portal.updateBounds(pos1, pos2);
        sender.sendMessage(Lang.getPositivePrefix() + Lang.translateInsertVariables("command.portal.move.complete", portalName));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.CREATE_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if (args.length == 2) {
            return portalServices.getPortalNames();
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.move.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.move.detailedhelp");
    }
}
