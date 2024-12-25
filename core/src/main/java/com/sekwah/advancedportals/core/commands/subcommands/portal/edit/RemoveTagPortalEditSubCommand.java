package com.sekwah.advancedportals.core.commands.subcommands.portal.edit;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.Arrays;
import java.util.List;

public class RemoveTagPortalEditSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;

    @Inject
    TagRegistry tagRegistry;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        var subArgs = Arrays.copyOfRange(args, 1, args.length);

        var portalName = subArgs[0];

        var portal = portalServices.getPortal(portalName);

        if(portal == null) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translateInsertVariables("command.portal.edit.error.notfound", portalName));
            return;
        }

        if(subArgs.length < 3) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translate("command.portal.edit.error.notagname"));
            return;
        }

        var tag = tagRegistry.getTag(subArgs[2]);

        if(tag == null) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translateInsertVariables("command.portal.edit.error.tagnotfound", subArgs[2]));
            return;
        }

        if(tag instanceof Tag.TagStatus tagStatus) {
            if(!tagStatus.canAlterTag()) {
                sender.sendMessage(Lang.getNegativePrefix()
                        + Lang.translateInsertVariables("command.portal.edit.error.tagcannotalter", subArgs[1]));
                return;
            }
        }

        if(portal.hasArg(tag.getName())) {
            portal.removeArg(tag.getName());
        } else {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translateInsertVariables("command.portal.edit.error.tagnotfoundinportal", tag.getName(), portal.getName()));
            return;
        }

        sender.sendMessage(Lang.getPositivePrefix()
                + Lang.translateInsertVariables(
                "command.portal.edit.newsummary", tag.getName(), portalName));

        TagReader.printArgs(sender, portal.getArgs());

        portalServices.savePortal(portal);

        sender.sendMessage(Lang.convertColors("\n&a") + Lang.translateInsertVariables("command.portal.edit.removetag.success", tag.getName(), portal.getName()));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.EDIT_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        // trim first 3 values from args
        var subArgs = Arrays.copyOfRange(args, 1, args.length);

        var portalName = subArgs[0];

        var portal = portalServices.getPortal(portalName);

        if(portal == null) {
            return null;
        }

        return portal.getArgs().stream().filter(arg -> {
            var tag = tagRegistry.getTag(arg.NAME);
            if(tag instanceof Tag.TagStatus tagStatus) {
                return tagStatus.canAlterTag();
            }
            return true;
        }).map(arg -> arg.NAME).toList();
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
