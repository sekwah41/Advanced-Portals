package com.sekwah.advancedportals.core.commands.subcommands.reusable;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.permissions.PortalPermissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CreateTaggedSubCommand implements SubCommand {

    protected abstract List<Tag> getRelatedTags();

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {

        if(TagReader.isClosedString(args)) {
            return List.of();
        }

        List<Tag> allTags = this.getRelatedTags();
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
                var aliases = tag.getAliases();
                if(aliases != null) {
                    for (String alias : aliases) {
                        if(portalTag.NAME.equals(alias)) {
                            return false;
                        }
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

    protected void printTags(CommandSenderContainer sender, List<DataTag> dataTags, Tag.TagType tagType) {
        for (DataTag tag : dataTags) {
            if(tag.VALUES.length == 1) {
                sender.sendMessage(" \u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUES[0]);
            } else {
                for (int i = 0; i < tag.VALUES.length; i++) {
                    sender.sendMessage(" \u00A7a" + tag.NAME + "\u00A77[" + i + "]:\u00A7e" + tag.VALUES[i]);
                }
            }
        }
        // TODO add a note saying if tags were ignored due to not being valid for the type
    }
}
