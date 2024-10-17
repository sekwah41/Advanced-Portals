package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.List;

public class VersionSubCommand implements SubCommand {
    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage(Lang.getPositivePrefix()
                           + " Advanced Portals v"
                           + AdvancedPortalsCore.version);
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.version.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.version.help");
    }
}
