package com.sekwah.advancedportals.core.commands.subcommands;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.portal.DataTag;

import java.util.ArrayList;

public abstract class CreateSubCommand {

    protected ArrayList<DataTag> getTagsFromArgs(String[] args) {
        ArrayList<DataTag> tags = new ArrayList<>();
        boolean partingValueWithSpaces = false;
        String argBeingParsed = "";
        String currentParsedValue = "";
        for (int i = 1; i < args.length; i++) {
            if(partingValueWithSpaces) {
                if(args[i].charAt(args[i].length() - 1) == '"') {
                    args[i] = args[i].substring(0, args[i].length() - 1);
                    partingValueWithSpaces = false;
                    tags.add(new DataTag(argBeingParsed.toLowerCase(), currentParsedValue));
                }
                else {
                    currentParsedValue += " " + args[i];
                }
            }
            else {
                String detectedTag = this.getTag(args[i].toLowerCase());
                if(detectedTag != null) {
                    String arg = args[i].substring(detectedTag.length() + 1);
                    if(arg.length() > 0 && arg.charAt(0) == '"') {
                        argBeingParsed = detectedTag;
                        currentParsedValue = arg;
                    }
                    else {
                        tags.add(new DataTag(detectedTag.toLowerCase(), arg));
                    }
                }
            }
        }
        return tags;
    }


    protected abstract String getTag(String arg);
}
