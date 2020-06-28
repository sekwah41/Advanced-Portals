package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.List;

public class ReloadSubCommand implements SubCommand {

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        portalsCore.loadPortalConfig();
        portalsCore.getPortalServices().loadPortals();
        portalsCore.getDestinationServices().loadDestinations();
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("command.reload.reloaded"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.reload");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.reload.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.reload.detailedhelp");
    }
}
