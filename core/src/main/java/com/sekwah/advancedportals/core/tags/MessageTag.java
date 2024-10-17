package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * The name of the destination or portal.
 *
 * <p>Most of the implementation of this tag is external, this is just to allow
 * for the tag to be used.
 *
 * <p>Most tags shouldn't be like this unless they are to be paired with
 * another tag.
 */
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
    public boolean preActivated(TagTarget target, PlayerContainer player, ActivationData activeData, String[] argData) {

        String selectedArg = argData[random.nextInt(argData.length)];
        activeData.setMetadata(TAG_NAME, selectedArg);
        activeData.setWarpStatus(ActivationData.WarpedStatus.ACTIVATED);
        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player, ActivationData activationData, String[] argData) {

        var destination = activationData.getMetadata(DestiTag.TAG_NAME);
        var message = activationData.getMetadata(TAG_NAME);
        if(destination == null) {
            destination = "";
        } else {
            destination = destination.replaceAll("_", " ");
        }

        sendMessage(player, message.replaceAll("@desti", destination).replaceAll("@player", player.getName()));
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player, ActivationData activationData, String[] argData) {
        return true;
    }

    public void sendMessage(PlayerContainer player, String message) {
        if(this.configRepository.warpMessageOnActionBar()) {
            player.sendActionBar(Lang.convertColors(message));
        }
        else if(this.configRepository.warpMessageInChat()) {
            player.sendMessage(Lang.getPositivePrefix() + " " + Lang.convertColors(message));
        }
    }
}
