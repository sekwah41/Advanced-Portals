package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.List;

public class EndGatewayBlockSubCommand implements SubCommand {

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerContainer player = sender.getPlayerContainer();
        if(player == null) {
            sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.playeronly"));
        }
        else {
            player.giveItem("BLACK_WOOL", "\u00A78Gateway Block Placer"
                    , "\u00A7r\u00A77This wool is made of a magical substance",
                            "\u00A7r\u00A7eRight Click\u00A77: Place portal block");
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.gatewayblock"));
        }

    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || PortalPermissions.CREATE_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.selector.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.selector.detailedhelp");
    }
}
