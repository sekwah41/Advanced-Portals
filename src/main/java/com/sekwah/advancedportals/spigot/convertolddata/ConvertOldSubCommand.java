package com.sekwah.advancedportals.spigot.convertolddata;

import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;

import java.util.List;

/**
 * TODO this is for spigot only for a few releases
 */
public class ConvertOldSubCommand implements SubCommand {
    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor(" Old portal data found."));
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor(" Old portal data successfully converted."));
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor(" Old desti data found."));
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor(" Old desti data successfully converted."));
        sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor(" Those were just sample outputs, it doesnt work yet."));
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
        return null;
    }

    @Override
    public String getDetailedHelpText() {
        return null;
    }
}
