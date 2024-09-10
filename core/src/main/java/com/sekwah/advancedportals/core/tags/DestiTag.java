package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import javax.annotation.Nullable;
import java.util.List;

public class DestiTag implements Tag.Activation, Tag.AutoComplete, Tag.Split {
    public static String TAG_NAME = "destination";
    @Inject
    DestinationServices destinationServices;

    @Inject
    WarpEffectRegistry warpEffectRegistry;

    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL};

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
        return new String[] {"desti"};
    }

    @Override
    public String description() {
        return Lang.translate("tag.desti.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        // Check that the destination exists.
        for (String destiName : destinationServices.getDestinationNames()) {
            if (destiName.equalsIgnoreCase(argData[0])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activationData, String[] argData) {
        Destination destination =
            destinationServices.getDestination(argData[0]);
        if (destination != null) {
            var warpEffectVisual = warpEffectRegistry.getVisualEffect("ender");
            if (warpEffectVisual != null) {
                warpEffectVisual.onWarpVisual(player, WarpEffect.Action.ENTER);
            }
            var warpEffectSound = warpEffectRegistry.getSoundEffect("ender");
            if (warpEffectSound != null) {
                warpEffectSound.onWarpSound(player, WarpEffect.Action.ENTER);
            }
            player.teleport(destination.getLoc());
            if (warpEffectVisual != null) {
                warpEffectVisual.onWarpVisual(player, WarpEffect.Action.EXIT);
            }
            if (warpEffectSound != null) {
                warpEffectSound.onWarpSound(player, WarpEffect.Action.EXIT);
            }
            activationData.setWarpStatus(ActivationData.WarpedStatus.WARPED);
        }
        return true;
    }

    @Override
    public List<String> autoComplete(String argData) {
        return destinationServices.getDestinationNames();
    }

    @Nullable
    @Override
    public String splitString() {
        return ",";
    }
}
