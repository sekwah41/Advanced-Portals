package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;

import java.util.List;

public class TransUpdateSubCommand implements SubCommand {
    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        AdvancedPortalsCore.getInstance().getDataStorage().copyDefaultFile("lang/en_GB.lang", true);
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("translatedata.replaced"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp();
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return "This is basic help text";
    }

    @Override
    public String getDetailedHelpText() {
        return "This help text is a lot more detailed than the basic one";
    }
}
