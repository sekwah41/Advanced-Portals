package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;

import java.util.ArrayList;
import java.util.List;

public class CreateDestiSubCommand implements SubCommand {

    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.createdesti.console"));
                return;
            }
            ArrayList<DataTag> destinationTags = TagReader.getTagsFromArgs(args);

            Destination destination = destinationServices.createDesti(args[1], player, player.getLoc(), destinationTags);
            if(destination != null) {
                sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.createdesti.complete"));
                sender.sendMessage(Lang.translate("command.create.tags"));

                ArrayList<DataTag> destiArgs = destination.getArgs();

                if(destiArgs.isEmpty()) {
                    sender.sendMessage(Lang.translate("desti.info.noargs"));
                }
                else {
                    for (DataTag tag : destiArgs) {
                        if(tag.VALUES.length == 1) {
                            sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUES[0]);
                        } else {
                            sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUES[0]);
                        }
                    }
                }
            }
            else {
                sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.createdesti.error"));
            }
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
