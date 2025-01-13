package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.PlayerData;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.Random;
import javax.annotation.Nullable;

public class CooldownTag implements Tag.Activation, Tag.Creation {
    @Inject
    PlayerDataServices playerDataServices;

    @Inject
    ConfigRepository configRepository;

    @Inject
    private InfoLogger infoLogger;

    public static String TAG_NAME = "cooldown";

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
        return null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.cooldown.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activationData,
                                String[] argData) {
        PlayerData playerData = playerDataServices.getPlayerData(player);
        if (target instanceof AdvancedPortal) {
            AdvancedPortal portal = (AdvancedPortal) target;
            String portalName = portal.getName();
            if (playerData.hasPortalCooldown(portalName)) {
                int cooldown = (int) Math.ceil(
                    playerData.getPortalCooldownLeft(portalName) / 1000D);
                player.sendMessage(Lang.translateInsertVariables(
                    "portal.cooldown.individual", cooldown,
                    Lang.translate(cooldown == 1 ? "time.second"
                                                 : "time.seconds")));
                if (configRepository.playFailSound()) {
                    player.playSound("block.portal.travel", 0.05f,
                                     new Random().nextFloat() * 0.4F + 0.8F);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
        if (activationData.hasActivated()) {
            if (target instanceof AdvancedPortal) {
                AdvancedPortal portal = (AdvancedPortal) target;
                PlayerData playerData =
                    playerDataServices.getPlayerData(player);
                try {
                    playerData.setPortalCooldown(
                        portal.getName(), Integer.parseInt(argData[0]) * 1000);
                } catch (NumberFormatException e) {
                    infoLogger.warning(
                        "Cooldown tag failed to set cooldown for portal: "
                        + portal.getName() + " with value: " + argData[0]);
                }
            }
        }
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activationData, String[] argData) {
        return true;
    }

    @Override
    public boolean created(TagTarget target, PlayerContainer player,
                           String[] argData) {
        try {
            Integer.parseInt(argData[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("tag.cooldown.fail"));
            return false;
        }
        return true;
    }

    @Override
    public void destroyed(TagTarget target, PlayerContainer player,
                          String[] argData) {
    }
}
