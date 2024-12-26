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
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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

        if(tag instanceof Tag.Status tagStatus) {
            if(!tagStatus.canAlterTag()) {
                sender.sendMessage(Lang.getNegativePrefix()
                        + Lang.translateInsertVariables("command.portal.edit.error.tagcannotalter", subArgs[1]));
                return;
            }
        }

        boolean partialRemoval = false;
        int valueIndex = -1;

        if(portal.hasArg(tag.getName())) {
            if(args.length == 5) {
                try {
                    var value = Integer.parseInt(args[4]);
                    valueIndex = value;
                    var values = portal.getArgValues(tag.getName());

                    if(value < 0 || value >= values.length) {
                        sender.sendMessage(Lang.getNegativePrefix()
                                + Lang.translate("command.portal.edit.error.invalidindex"));
                        return;
                    }

                    // remove value at index from the String[]
                    var newValues = IntStream.range(0, values.length)
                            .filter(i -> i != value)
                            .mapToObj(i -> values[i])
                            .toArray(String[]::new);
                    portal.setArgValues(sender, tag.getName(), newValues);
                    partialRemoval = true;
                }
                catch(NumberFormatException e) {
                    sender.sendMessage(Lang.getNegativePrefix()
                            + Lang.translate("command.portal.edit.error.invalidnumber"));
                    return;
                }
            } else {
                portal.removeArg(sender, tag.getName());
            }
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

        if(partialRemoval) {
            sender.sendMessage(Lang.getPositivePrefix()
                    + Lang.translateInsertVariables("command.portal.edit.removetag.success.partial", tag.getName(), valueIndex, portal.getName()));
        } else {
            sender.sendMessage(Lang.convertColors("\n&a") + Lang.translateInsertVariables("command.portal.edit.removetag.success", tag.getName(), portal.getName()));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.EDIT_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {

        if(args.length > 5) {
            return Collections.emptyList();
        }

        // trim first 3 values from args
        var subArgs = Arrays.copyOfRange(args, 1, args.length);

        var portalName = subArgs[0];

        var portal = portalServices.getPortal(portalName);

        if(portal == null) {
            return Collections.emptyList();
        }

        var tagNames = portal.getArgs().stream().filter(arg -> {
            var tag = tagRegistry.getTag(arg.NAME);
            if(tag instanceof Tag.Status tagStatus) {
                return tagStatus.canAlterTag();
            }
            return true;
        }).map(arg -> arg.NAME).toList();

        if(args.length == 4) {
            return tagNames;
        }

        if(args.length == 5) {
            // check if tagNames contains args[3] ignoring case
            var tag = portal.getArgs().stream().filter(arg -> arg.NAME.equalsIgnoreCase(args[3])).findFirst().orElse(null);
            if(tag == null) {
                return Collections.emptyList();
            }

            return IntStream.range(0, tag.VALUES.length)
                    .mapToObj(Integer::toString)
                    .toList();
        }

        return Collections.emptyList();
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
