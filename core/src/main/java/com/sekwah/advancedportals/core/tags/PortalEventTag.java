package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.GameMode;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.core.warphandler.TriggerType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public class PortalEventTag implements Tag.Activation, Tag.AutoComplete,
                                       Tag.DenyBehavior, Tag.OrderPriority {
    @Inject
    PlayerDataServices playerDataServices;

    @Inject
    ConfigRepository configRepository;

    @Inject
    private InfoLogger infoLogger;

    public static final String TAG_NAME = "portalEvent";

    private final String[] aliases = new String[] {"delayed"};

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
        return aliases;
    }

    @Override
    public String description() {
        return Lang.translate("tag.permission.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return true;
        }
        return !Objects.equals(argData[0], "true")
            || activeData.getTriggerType() == TriggerType.PORTAL;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
        // Do nothing
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
    public Behaviour getDenyBehavior() {
        return Behaviour.SILENT;
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGHEST;
    }
}
