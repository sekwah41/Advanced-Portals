package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.core.warphandler.TriggerType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public class InteractionTriggerTag implements Tag.Activation, Tag.AutoComplete,
                                             Tag.OrderPriority {
    public static final String TAG_NAME = "interaction_trigger";

    private final String[] aliases = new String[] {};

    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL};

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
        return aliases.length > 0 ? aliases : null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.interaction_trigger.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        return !Objects.equals(argData[0], "true")
            || activeData.getTriggerType() == TriggerType.BLOCK_INTERACTION;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activationData, String[] argData) {
        return true;
    }

    @Nullable
    @Override
    public List<String> autoComplete(String argData) {
        return Arrays.asList("true", "false");
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }
}
