package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.registry.SubCmd;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.List;

@SubCmd(name="version", parent=SubCmd.TYPE.PORTAL, minArgs=5, permissions= {"Test"})
public class VersionSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + " Advanced Portals v" + AdvancedPortalsCore.version);
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
        return Lang.translate("command.version.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.version.help");
    }
}
