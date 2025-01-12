package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.Random;

public class MessageTag implements Tag.Activation {
    @Inject
    ConfigRepository configRepository;

    public static String TAG_NAME = "message";

    private final TagType[] tagTypes =
        new TagType[] {TagType.PORTAL, TagType.DESTINATION};

    private final Random random = new Random();

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
        return Lang.translate("tag.message.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
        String destination = activationData.getMetadata(DestiTag.TAG_NAME);
        String message = activationData.getMetadata(TAG_NAME);
        if (destination == null) {
            destination = "";
        } else {
            destination = destination.replaceAll("_", " ");
        }

        sendMessage(player,
                    message.replaceAll("@desti", destination)
                        .replaceAll("@player", player.getName()));
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activeData, String[] argData) {
        String selectedArg = argData[random.nextInt(argData.length)];
        activeData.setMetadata(TAG_NAME, selectedArg);
        activeData.setWarpStatus(ActivationData.WarpedStatus.ACTIVATED);
        return true;
    }

    public void sendMessage(PlayerContainer player, String message) {
        if (this.configRepository.warpMessageOnActionBar()) {
            player.sendActionBar(Lang.convertColors(message));
        } else if (this.configRepository.warpMessageInChat()) {
            player.sendMessage(Lang.getPositivePrefix() + " "
                               + Lang.convertColors(message));
        }
    }
}
