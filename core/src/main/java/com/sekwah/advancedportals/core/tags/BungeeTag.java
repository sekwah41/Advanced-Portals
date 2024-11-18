package com.sekwah.advancedportals.core.tags;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.Random;

public class BungeeTag implements Tag.Activation {
    public static final String PACKET_CHANNEL = "BungeeCord";

    public static String TAG_NAME = "bungee";

    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL};

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
        return Lang.translate("tag.bungee.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activeData, String[] argData) {
        String selectedArg = argData[random.nextInt(argData.length)];

        ByteArrayDataOutput outForSend = ByteStreams.newDataOutput();
        outForSend.writeUTF("Connect");
        outForSend.writeUTF(selectedArg);
        player.sendPacket(BungeeTag.PACKET_CHANNEL, outForSend.toByteArray());
        activeData.setWarpStatus(ActivationData.WarpedStatus.WARPED);
        return true;
    }
}
