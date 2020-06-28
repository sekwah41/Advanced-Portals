package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.commands.subcommands.CreateSubCommand;
import com.sekwah.advancedportals.core.data.DataTag;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.Arrays;
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

            AdvancedPortal portal = AdvancedPortalsCore.getPortalServices().createPortal(args[1], player, portalTags);
            if(portal != null) {
                sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("command.create.complete"));
                sender.sendMessage(Lang.translateColor("command.create.tags"));
                sender.sendMessage("\u00A7a" + "triggerBlock\u00A77:\u00A7e" + Arrays.toString(portal.getTriggerBlocks()));
                for (DataTag tag: portal.getArgs()) {
                    sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUE);
                }
            }
            sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateColor("command.create.error"));
        }
        else {
            sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.error.noname"));
        }
    }

    protected String getTag(String arg) {
        int splitLoc = arg.indexOf(":");
        if(splitLoc != -1) {
            return arg.substring(0,splitLoc);
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
