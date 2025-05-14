package com.sekwah.advancedportals.core.tags;

import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;
import javax.annotation.Nullable;

public class GroupTag implements Tag {
    public static final String TAG_NAME = "group";
    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL, TagType.DESTINATION};

    @Override
    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String getName() {
        return TAG_NAME;
    }

    @Nullable
    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.group.description");
    }
}
