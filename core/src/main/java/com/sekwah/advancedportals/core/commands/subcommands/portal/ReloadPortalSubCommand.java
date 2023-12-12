package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.List;

public class ReloadPortalSubCommand implements SubCommand {

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Inject
    PortalServices portalServices;

    @Inject
    DestinationServices destinationServices;

    @Inject
    ConfigRepository configRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        portalsCore.loadPortalConfig();
        portalServices.loadPortals();
        destinationServices.loadDestinations();
        Lang.loadLanguage(configRepository.getTranslation());
        sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.reload.reloaded"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || PortalPermissions.RELOAD.hasPermission(sender);
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
