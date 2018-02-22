package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.portal.DataTag;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.commands.subcommands.CreateSubCommand;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.List;

public class CreateDestiSubCommand extends CreateSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translate("command.createdesti.console"));
                return;
            }
            ArrayList<DataTag> destiTags = this.getTagsFromArgs(args);
            try {
                Destination desti = AdvancedPortalsCore.getDestinationManager().createDesti(args[1], player, player.getLoc(), destiTags);
                if(desti != null) {
                    sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("command.createdesti.complete"));
                    sender.sendMessage(Lang.translateColor("command.create.tags"));
                    ArrayList<DataTag> destiArgs = desti.getArgs();
                    if(destiArgs.size() == 0) {
                        sender.sendMessage(Lang.translateColor("desti.info.noargs"));
                    }
                    else {
                        for (DataTag tag : destiArgs) {
                            sender.sendMessage("\u00A7a" + tag.NAME + ":" + tag.VALUE);
                        }
                    }
                }
            } catch (PortalException portalTagExeption) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateColor("command.createdesti.error") + " "
                        + Lang.translate(portalTagExeption.getMessage()));
            }
        }
        else {
            sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translate("command.error.noname"));
        }
    }

    protected String getTag(String arg) {
        int splitLoc = arg.indexOf(":");
        if(splitLoc != -1) {
            return arg.substring(0,splitLoc + 1);
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
        return Lang.translate("command.createdesti.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.createdesti.detailedhelp");
    }
}
