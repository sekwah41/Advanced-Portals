package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.util.Lang;

import java.util.List;

public class LangUpdateSubCommand implements SubCommand {

    @Inject
    private AdvancedPortalsCore portalsCore;

    public LangUpdateSubCommand() {
    }

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1 && args[1].equalsIgnoreCase("overwrite")) {
            this.portalsCore.getDataStorage().copyDefaultFile("lang/en_GB.lang", true);
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("translatedata.replaced"));
        } else {
            this.portalsCore.getDataStorage().copyDefaultFile("lang/en_GB.lang", "lang/en_GB-new.lang", true);
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("translatedata.replaced") + Lang.translate("translatedata.overwrite"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.langupdate");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.lang.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.lang.help");
    }
}
