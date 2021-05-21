package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.List;

public class LangUpdateSubCommand implements SubCommand {

    @Inject
    private AdvancedPortalsCore portalsCore;

    public LangUpdateSubCommand() {
    }

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        this.portalsCore.getDataStorage().copyDefaultFile("lang/en_GB.lang", true);
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("translatedata.replaced"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.transupdate");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.trans.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.trans.help");
    }
}
