package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePortalSubCommand implements SubCommand {

    @Inject
    PortalServices portalServices;

    @Inject
    InfoLogger infoLogger;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.create.console"));
                return;
            }
            ArrayList<DataTag> portalTags = TagReader.getTagsFromArgs(args);

            // Find the tag with the "name" NAME
            DataTag nameTag = portalTags.stream().findFirst().filter(tag -> tag.NAME.equals("name")).orElse(null);

            AdvancedPortal portal = portalServices.createPortal(nameTag == null ? null : nameTag.VALUES[0], player, portalTags);
            if(portal != null) {
                sender.sendMessage(Lang.translate("messageprefix.positive") + Lang.translate("command.create.complete"));
                sender.sendMessage(Lang.translate("command.create.tags"));
                sender.sendMessage("\u00A7a" + "triggerBlock\u00A77:\u00A7e" + Arrays.toString(portal.getTriggerBlocks()));
                for (DataTag tag: portal.getArgs()) {
                    if(tag.VALUES.length == 1) {
                        sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUES[0]);
                    } else {
                        // Output in the format tag.NAME(index): value
                        for (int i = 0; i < tag.VALUES.length; i++) {
                            sender.sendMessage("\u00A7a" + tag.NAME + "(" + i + ")\u00A77:\u00A7e" + tag.VALUES[i]);
                        }
                    }
                }
            }
            sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.create.error"));
        }
        else {
            sender.sendMessage(Lang.translate("messageprefix.negative") + Lang.translate("command.error.notags"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || PortalPermissions.CREATE_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        // TODO add tab complete for tags
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
