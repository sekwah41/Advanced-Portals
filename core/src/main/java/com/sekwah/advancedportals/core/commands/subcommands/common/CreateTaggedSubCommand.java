package com.sekwah.advancedportals.core.commands.subcommands.common;

import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.util.TagReader;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CreateTaggedSubCommand implements SubCommand {
    protected abstract List<Tag> getRelatedTags();

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        if (TagReader.isClosedString(args)) {
            return Collections.emptyList();
        }

        List<Tag> allTags = this.getRelatedTags();
        List<String> suggestions = new ArrayList<>();
        if (args.length > 0) {
            String lastArg = args[args.length - 1];
            // Check if the split results in exactly 2 or if its  1 and ends
            // with :
            String[] split = lastArg.split(":");
            if (split.length == 2
                || (split.length == 1 && lastArg.endsWith(":"))) {
                // Loop over tags in allTags and check if the first half of
                // split is equal to the tag name or alias
                for (Tag tag : allTags) {
                    // Check if the last tag starts with the tag name or alias
                    boolean startsWith = false;
                    if (lastArg.startsWith(tag.getName())) {
                        startsWith = true;
                    } else {
                        String[] aliases = tag.getAliases();
                        if (aliases != null) {
                            for (String alias : aliases) {
                                if (lastArg.startsWith(alias)) {
                                    startsWith = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (tag instanceof Tag.AutoComplete
                        && startsWith) {
                        Tag.AutoComplete autoComplete = (Tag.AutoComplete) tag;
                        String argData = split.length == 2 ? split[1] : "";
                        List<String> tagSuggestions = autoComplete.autoComplete(argData);

                        if (tagSuggestions != null) {
                            if (tag instanceof Tag.Split ) {
                                Tag.Split splitTag = (Tag.Split) tag;
                                String multiTagSplit = splitTag.splitString();
                                boolean endsWithSplit =
                                    argData.endsWith(multiTagSplit);
                                String[] items = argData.split(multiTagSplit);
                                Set<String> existingItems =
                                    Arrays
                                        .stream(items, 0,
                                                endsWithSplit
                                                    ? items.length
                                                    : items.length - 1)
                                        .map(String::trim)
                                        .collect(Collectors.toSet());

                                String partialInput = endsWithSplit
                                    ? ""
                                    : items[items.length - 1].trim();
                                String baseString = endsWithSplit
                                    ? argData
                                    : argData.substring(
                                          0,
                                          argData.lastIndexOf(multiTagSplit)
                                              + 1);

                                tagSuggestions =
                                    tagSuggestions
                                        .stream()
                                        // Remove already listed items
                                        .filter(s -> !existingItems.contains(s))
                                        // Remove items that don't match the
                                        // currently typed input
                                        .filter(s -> s.startsWith(partialInput))
                                        // Remap so the auto completes actually
                                        // show
                                        .map(s -> baseString + s)
                                        .collect(Collectors.toList());
                            }

                            // Loop over suggestions and add split[0] + ":" to
                            // the start
                            for (String tagSuggestion : tagSuggestions) {
                                suggestions.add(split[0] + ":" + tagSuggestion);
                            }
                        }
                    }
                }

                return suggestions;
            }
        }

        ArrayList<DataTag> tagsFromArgs = TagReader.getTagsFromArgs(args);

        allTags.stream()
            .filter(tag -> {
                for (DataTag argTag : tagsFromArgs) {
                    if (argTag.NAME.equals(tag.getName())) {
                        return false;
                    }
                    String[] aliases = tag.getAliases();
                    if (aliases != null) {
                        for (String alias : aliases) {
                            if (argTag.NAME.equals(alias)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            })
            .forEach(tag -> {
                suggestions.add(tag.getName());
                String[] aliases = tag.getAliases();
                if (aliases != null) {
                    suggestions.addAll(Arrays.stream(aliases).collect(Collectors.toList()));
                }
            });

        // Loop over all suggestions and add : to the end
        suggestions.replaceAll(s -> s + ":");

        return suggestions;
    }

    protected void filterAndProcessTags(List<DataTag> dataTags) {
        List<Tag> relatedTags = this.getRelatedTags();
        List<DataTag> processedTags = new ArrayList<>();

        for (DataTag dataTag : dataTags) {
            for (Tag tag : relatedTags) {
                if (tag instanceof Tag.Split ) {
                    Tag.Split splitTag = (Tag.Split) tag;
                    String splitString = splitTag.splitString();
                    if (splitString != null) {
                        List<String> newValues = new ArrayList<>();
                        for (String split : dataTag.VALUES) {
                            newValues.addAll(
                                Arrays.stream(split.split(splitString))
                                    .map(String::trim)
                                    .collect(Collectors.toList()));
                        }
                        dataTag = new DataTag(dataTag.NAME,
                                              newValues.toArray(new String[0]));
                    }
                }
                if (dataTag.NAME.equals(tag.getName())) {
                    // DataTag name matches the tag's main name, add as is
                    processedTags.add(dataTag);
                    break;
                } else if (tag.getAliases() != null
                           && Arrays.asList(tag.getAliases())
                                  .contains(dataTag.NAME)) {
                    // There is currently an edge case where if someone uses
                    // multiple aliases or names for tags at the same time,
                    // it'll only use the last one applied to the mapping.
                    // DataTag name matches an alias, create a new DataTag with
                    // the main name
                    processedTags.add(
                        new DataTag(tag.getName(), dataTag.VALUES));
                    break;
                }
            }
        }

        // Replace the original dataTags list with the processed tags
        dataTags.clear();
        dataTags.addAll(processedTags);
        // Sort the tags by name however make sure name is first
        dataTags.sort((o1, o2) -> {
            if (o1.NAME.equals("name")) {
                return -1;
            }
            if (o2.NAME.equals("name")) {
                return 1;
            }
            return o1.NAME.compareTo(o2.NAME);
        });
    }
}
