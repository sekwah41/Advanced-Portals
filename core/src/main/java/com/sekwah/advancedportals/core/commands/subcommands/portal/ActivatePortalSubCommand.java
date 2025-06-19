package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActivatePortalSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;



    @Inject
    IPortalRepository portalRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(Lang.getNegativePrefix() + Lang.translateInsertVariables("command.activate.usage", getBasicHelpText()));
            return;
        }
        String playerName = args[1];
        String portalName = args[2];
        ServerContainer server = sender.getPlayerContainer().getServer();
        PlayerContainer player = server.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(Lang.getNegativePrefix() + Lang.translateInsertVariables("command.activate.playernotfound", playerName));
            return;
        }
        Optional<AdvancedPortal> portalOpt = Optional.ofNullable(portalRepository.get(portalName));
        if (!portalOpt.isPresent()) {
            sender.sendMessage(Lang.getNegativePrefix() + Lang.translateInsertVariables("command.activate.portalnotfound", portalName));
            return;
        }
        AdvancedPortal portal = portalOpt.get();
        portal.forceActivateForPlayer(player);
        sender.sendMessage(Lang.getPositivePrefix() + Lang.translateInsertVariables("command.activate.success", portalName, playerName));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.PORTAL_ACTIVATE.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if (args.length == 1) {
            // Suggest online player names for the first argument
            return Arrays.stream(sender.getServer().getPlayers())
                    .map(PlayerContainer::getName)
                    .collect(Collectors.toList());
        }
        if (args.length == 2) {
            // Suggest portal names for the second argument
            return portalServices.getPortalNames();
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.activate.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.activate.detailedhelp");
    }
}
