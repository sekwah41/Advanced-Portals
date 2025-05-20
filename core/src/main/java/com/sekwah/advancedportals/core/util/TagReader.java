package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TagReader {
    public static boolean isOpenString(String[] args) {
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;

        for (String arg : args) {
            if (arg.contains(":") && !inQuotes) {
                int colonIndex = arg.indexOf(':');
                currentValue = new StringBuilder(arg.substring(colonIndex + 1));
                inQuotes = currentValue.toString().startsWith("\"");
            } else {
                if (currentValue.length() != 0) {
                    currentValue.append(" ");
                }
                currentValue.append(arg);
            }

            if (inQuotes && arg.endsWith("\"")) {
                inQuotes = false;
            }
        }

        return inQuotes;
    }

    public static ArrayList<DataTag> getTagsFromArgs(String[] args) {
        HashMap<String, ArrayList<String>> tagMap = new HashMap<>();
        StringBuilder currentValue = new StringBuilder();
        String currentIdentifier = null;
        boolean inQuotes = false;

        for (String arg : args) {
            if (arg.contains(":") && !inQuotes) {
                if (currentIdentifier != null) {
                    ArrayList<String> tags;
                    if (tagMap.containsKey(currentIdentifier)) {
                        tags = tagMap.get(currentIdentifier);
                    } else {
                        tags = new ArrayList<>();
                        tagMap.put(currentIdentifier, tags);
                    }
                    tags.add(currentValue.toString());
                }
                int colonIndex = arg.indexOf(':');
                currentIdentifier = arg.substring(0, colonIndex);
                currentValue = new StringBuilder(arg.substring(colonIndex + 1));
                inQuotes = currentValue.toString().startsWith("\"");
                if (inQuotes) {
                    currentValue.deleteCharAt(0);
                }
            } else {
                if (currentValue.length() != 0) {
                    currentValue.append(" ");
                }
                currentValue.append(arg);
            }

            if (inQuotes && arg.endsWith("\"")) {
                inQuotes = false;
                if (currentValue.length() > 1) {
                    currentValue.deleteCharAt(currentValue.length() - 1);
                }
            }
        }

        if (currentIdentifier != null) {
            ArrayList<String> tags;
            if (tagMap.containsKey(currentIdentifier)) {
                tags = tagMap.get(currentIdentifier);
            } else {
                tags = new ArrayList<>();
                tagMap.put(currentIdentifier, tags);
            }
            tags.add(currentValue.toString().replace("\"", ""));
        }

        // Loop over values in the map and create the tags
        ArrayList<DataTag> tags = new ArrayList<>();
        for (String key : tagMap.keySet()) {
            tags.add(new DataTag(key, tagMap.get(key).toArray(new String[0])));
        }

        return tags;
    }

    public static String getTag(String arg) {
        int splitLoc = arg.indexOf(":");
        if (splitLoc != -1) {
            return arg.substring(0, splitLoc);
        }
        return null;
    }

    public static void printArgs(CommandSenderContainer sender,
                                 List<DataTag> dataTags) {
        for (DataTag tag : dataTags) {
            if (tag.VALUES.length == 1) {
                sender.sendMessage(" \u00A7a" + tag.NAME + "\u00A77:\u00A7e"
                                   + tag.VALUES[0]);
            } else {
                for (int i = 0; i < tag.VALUES.length; i++) {
                    sender.sendMessage(" \u00A7a" + tag.NAME + "\u00A77[" + i
                                       + "]:\u00A7e" + tag.VALUES[i]);
                }
            }
        }
    }
}
