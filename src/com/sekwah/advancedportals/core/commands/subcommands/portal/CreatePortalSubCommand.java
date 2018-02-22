package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.DataTag;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.commands.subcommands.CreateSubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.List;

public class CreatePortalSubCommand extends CreateSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translate("command.create.console"));
                return;
            }
            ArrayList<DataTag> portalTags = this.getTagsFromArgs(args);
            try {
                AdvancedPortalsCore.getPortalManager().createPortal(args[1], player, portalTags);
                sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor(""));
            } catch (PortalException portalTagExeption) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateColor("command.create.error") + " "
                        + Lang.translate(portalTagExeption.getMessage()));
            }
        }
        else {
            sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.error.noname"));
        }
    }

    protected String getTag(String arg) {
        ArrayList<String> tags = AdvancedPortalsCore.getPortalTagRegistry().getTags();
        for(String tag : tags) {
            if(arg.startsWith(tag + ":")) {
                return tag;
            }
        }
        return null;
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
        return Lang.translate("command.create.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.create.detailedhelp");
    }
}
