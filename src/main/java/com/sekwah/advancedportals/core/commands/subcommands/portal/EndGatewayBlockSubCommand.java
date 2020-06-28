package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

import java.util.List;

public class EndGatewayBlockSubCommand implements SubCommand {

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerContainer player = sender.getPlayerContainer();
        if(player == null) {
            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translate("command.playeronly"));
        }
        else {
            player.giveWool("BLACK", "\u00A78Gateway Block Placer"
                    , "\u00A7rThis wool is made of a magical substance",
                            "\u00A7rRight Click: Place portal block");
            sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.gatewayblock"));
        }

    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.createportal");
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
