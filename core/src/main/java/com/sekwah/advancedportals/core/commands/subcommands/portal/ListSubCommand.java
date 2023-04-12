package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.List;

public class ListSubCommand implements SubCommand {

    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.list"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.list.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.list.help");
    }
}
