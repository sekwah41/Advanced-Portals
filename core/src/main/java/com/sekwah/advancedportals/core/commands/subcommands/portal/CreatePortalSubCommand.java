package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePortalSubCommand implements SubCommand {

    @Inject
    PortalServices portalServices;

    @Inject
    InfoLogger infoLogger;

    @Inject
    TagRegistry tagRegistry;

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

        if(TagReader.isClosedString(args)) {
            return List.of();
        }



        List<Tag> allTags = tagRegistry.getTags();
        List<String> suggestions = new ArrayList<>();
        if(args.length > 0) {
            var lastArg = args[args.length - 1];
            // Check if the split results in exactly 2 or if its  1 and ends with :
            var split = lastArg.split(":");
            if(split.length == 2 || (split.length == 1 && lastArg.endsWith(":"))) {
                // Loop over tags in allTags and check if the first half of split is equal to the tag name or alias
                for(Tag tag : allTags) {
                    // Check if the last tag starts with the tag name or alias
                    var startsWith = false;
                    if(lastArg.startsWith(tag.getName())) {
                        startsWith = true;
                    } else {
                        var aliases = tag.getAliases();
                        if(aliases != null) {
                            for (String alias : aliases) {
                                if(lastArg.startsWith(alias)) {
                                    startsWith = true;
                                    break;
                                }
                            }
                        }
                    }

                    if(tag instanceof Tag.AutoComplete autoComplete && startsWith) {
                        var tagSuggestions = autoComplete.autoComplete(split.length == 2 ? split[1] : "");
                        if(tagSuggestions != null) {
                            // Loop over suggestions and add split[0] + ":" to the start
                            for (String tagSuggestion : tagSuggestions) {
                                suggestions.add(split[0] + ":" + tagSuggestion);
                            }
                        }
                    }
                }

                return suggestions;
            }
        }

        ArrayList<DataTag> portalTags = TagReader.getTagsFromArgs(args);

        allTags.stream().filter(tag -> {
            for (DataTag portalTag : portalTags) {
                if(portalTag.NAME.equals(tag.getName())) {
                    return false;
                }
                // check the tag aliases
                for (String alias : tag.getAliases()) {
                    if(portalTag.NAME.equals(alias)) {
                        return false;
                    }
                }
            }
            return true;
        }).forEach(tag -> {
            suggestions.add(tag.getName());
            var aliases = tag.getAliases();
            if(aliases != null) {
                suggestions.addAll(Arrays.stream(aliases).toList());
            }
        });

        // Loop over all suggestions and add : to the end
        for (int i = 0; i < suggestions.size(); i++) {
            suggestions.set(i, suggestions.get(i) + ":");
        }

        return suggestions;
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
