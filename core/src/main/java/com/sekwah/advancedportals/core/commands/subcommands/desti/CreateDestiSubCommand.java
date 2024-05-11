package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;

import java.util.ArrayList;
import java.util.List;

public class CreateDestiSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.createdesti.console"));
                return;
            }
            ArrayList<DataTag> destiTags = TagReader.getTagsFromArgs(args);
            // TODO sort desti service
            /*Destination desti = AdvancedPortalsCore.getDestinationServices().createDesti(args[1], player, player.getLoc(), destiTags);
            if(desti != null) {
                sender.sendMessage(Lang.translateColor("messageprefix.positive") + Lang.translateColor("command.createdesti.complete"));
                sender.sendMessage(Lang.translateColor("command.create.tags"));
                ArrayList<DataTag> destiArgs = desti.getArgs();
                if(destiArgs.size() == 0) {
                    sender.sendMessage(Lang.translateColor("desti.info.noargs"));
                }
                else {
                    for (DataTag tag : destiArgs) {
                        sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUE);
                    }
                }
            }
            else {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateColor("command.createdesti.error"));
            }*/
        }
        else {
            sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.error.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || PortalPermissions.CREATE_PORTAL.hasPermission(sender);
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