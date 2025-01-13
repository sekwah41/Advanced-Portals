package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.network.Packet;
import com.sekwah.advancedportals.core.network.ProxyTransferDestiPacket;
import com.sekwah.advancedportals.core.network.ProxyTransferPacket;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.Random;

public class ProxyTag implements Tag.Activation, Tag.OrderPriority, Tag.Split {
    @Inject
    ConfigRepository configRepository;

    public static String TAG_NAME = "proxy";

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
        return Lang.translate("tag.proxy.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        String selectedArg = argData[random.nextInt(argData.length)];
        activeData.setMetadata(TAG_NAME, selectedArg);
        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activeData, String[] argData) {
        if (!this.configRepository.getEnableProxySupport()) {
            player.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("tag.proxy.notenabled"));
            return false;
        }

        String selectedArg = argData[random.nextInt(argData.length)];

        String desti = activeData.getMetadata(DestiTag.TAG_NAME);

        Packet packet;
        if (desti == null) {
            packet = new ProxyTransferPacket(selectedArg);
        } else {
            packet = new ProxyTransferDestiPacket(selectedArg, desti);
        }
        player.sendPacket(ProxyMessages.CHANNEL_NAME, packet.encode());
        activeData.setWarpStatus(ActivationData.WarpedStatus.WARPED);
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGHEST;
    }
}
