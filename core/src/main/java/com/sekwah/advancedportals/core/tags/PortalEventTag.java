package com.sekwah.advancedportals.core.tags;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import javax.annotation.Nullable;
import javax.inject.Inject;

public class PortalEventTag implements Tag.Activation {
    @Inject
    PlayerDataServices playerDataServices;

    @Inject
    ConfigRepository configRepository;

    @Inject
    private InfoLogger infoLogger;

    public static String TAG_NAME = "portalEvent";

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
        return new String[0];
    }

    @Override
    public String description() {
        return Lang.translate("tag.permission.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        if (!player.hasPermission(argData[1])) {
            player.sendMessage(Lang.translate("portal.error.nopermission"));
            return false;
        }
        return true;
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
}
