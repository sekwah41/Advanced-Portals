package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.List;
import javax.annotation.Nullable;

public class TriggerBlockTag implements Tag.AutoComplete, Tag.Split {
    @Inject
    private ServerContainer serverContainer;

    public static final String TAG_NAME = "triggerblock";

    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL};

    @Override
    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String getName() {
        return TAG_NAME;
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.triggerblock.description");
    }

    @Override
    public List<String> autoComplete(String argData) {
        var triggerBlocks = serverContainer.getCommonTriggerBlocks()
                                .stream()
                                .filter(block -> block.contains(argData))
                                .toList();

        if (triggerBlocks.isEmpty()) {
            return serverContainer.getAllTriggerBlocks();
        }

        return triggerBlocks;
    }
}
