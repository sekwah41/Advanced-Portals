package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.serializeddata.DataTag;

import java.util.ArrayList;

public class TagReader {

    public static ArrayList<DataTag> getTagsFromArgs(String[] args) {
        ArrayList<DataTag> tags = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        String currentIdentifier = null;
        boolean inQuotes = false;

        for (String arg : args) {
            if (arg.contains(":") && !inQuotes) {
                if (currentIdentifier != null) {
                    tags.add(new DataTag(currentIdentifier, currentValue.toString()));
                }
                int colonIndex = arg.indexOf(':');
                currentIdentifier = arg.substring(0, colonIndex);
                currentValue = new StringBuilder(arg.substring(colonIndex + 1));
                inQuotes = currentValue.toString().startsWith("\"");
            } else {
                if (!currentValue.isEmpty()) {
                    currentValue.append(" ");
                }
                currentValue.append(arg);
            }

            if (inQuotes && arg.endsWith("\"")) {
                inQuotes = false;
            }
        }

        if (currentIdentifier != null) {
            tags.add(new DataTag(currentIdentifier, currentValue.toString().replace("\"", "")));
        }

        return tags;
    }


    public static String getTag(String arg) {
        int splitLoc = arg.indexOf(":");
        if(splitLoc != -1) {
            return arg.substring(0,splitLoc);
        }
        return null;
    }

}
