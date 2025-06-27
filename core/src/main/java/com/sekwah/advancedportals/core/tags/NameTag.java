package com.sekwah.advancedportals.core.tags;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.List;

/**
 * The name of the destination or portal.
 *
 * <p>Most of the implementation of this tag is external, this is just to allow
 * for the tag to be used.
 *
 * <p>Most tags shouldn't be like this unless they are to be paired with
 * another tag.
 */
public class NameTag implements Tag.AutoComplete, Tag.Creation {
    public static final String TAG_NAME = "name";

    private final TagType[] tagTypes =
        new TagType[] {TagType.PORTAL, TagType.DESTINATION};

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
        return Lang.translate("tag.name.description");
    }

    @Override
    public List<String> autoComplete(String argData) {
        return null;
    }

    @Override
    public boolean created(TagTarget target, PlayerContainer player,
                           String[] argData) {
        if (argData.length > 0) {
            String name = argData[0];
            if (name.contains(" ")) {
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("tag.name.error.nospaces"));
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroyed(TagTarget target, PlayerContainer player,
                          String[] argData) {
    }
}
