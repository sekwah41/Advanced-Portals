package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.List;

public class SelectorSubCommand implements SubCommand {

    @Inject
    private ConfigRepository configRepo;

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerContainer player = sender.getPlayerContainer();
        if(player == null) {
            sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.playeronly"));
        }
        else {
            player.giveItem(configRepo.getSelectorMaterial(), "\u00A7ePortal Region Selector"
                    , "\u00A7rThis wand with has the power to help", "\u00A7r create portals bistowed upon it!");
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.selector"));
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
