package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DestiTag implements Tag.Activation, Tag.AutoComplete, Tag.Split {
    public static String TAG_NAME = "destination";

    @Inject
    ConfigRepository configRepository;

    @Inject
    DestinationServices destinationServices;

    @Inject
    WarpEffectRegistry warpEffectRegistry;

    @Inject
    private transient TagRegistry tagRegistry;

    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL};

    @Override
    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String getName() {
        return TAG_NAME;
    }

    private final Random random = new Random();

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
        if (argData.length == 0) {
            return false;
        }
        String selectedArg = argData[random.nextInt(argData.length)];

        activeData.setMetadata(TAG_NAME, selectedArg);

        if(destinationServices.getDestination(selectedArg) == null) {
            player.sendMessage(Lang.getNegativePrefix() + Lang.translateInsertVariables("desti.error.notfound", selectedArg));
            return false;
        }

        // Check and trigger all tags on the destination
        Destination destination = destinationServices.getDestination(selectedArg);
        if (destination != null) {

            for (var destiTag : destination.getArgs()) {
                Tag.Activation activationHandler =
                        tagRegistry.getActivationHandler(destiTag.NAME, Tag.TagType.DESTINATION);
                if (activationHandler != null
                        && !activationHandler.preActivated(target, player, activeData, argData)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
        var selectedArg = activationData.getMetadata(TAG_NAME);
        Destination destination = destinationServices.getDestination(selectedArg);
        if (destination != null) {
            for (var destiTag : destination.getArgs()) {
                Tag.Activation activationHandler =
                        tagRegistry.getActivationHandler(destiTag.NAME, Tag.TagType.DESTINATION);
                if (activationHandler != null) {
                    activationHandler.postActivated(target, player, activationData, argData);
                }
            }
            var message = activationData.getMetadata(MessageTag.TAG_NAME);
            if(message == null) {
                sendMessage(player, Lang.translateInsertVariables("desti.warpdesti.warp", destination.getName().replaceAll("_", " ")));
            }
        }
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activationData, String[] argData) {
        var selectedArg = activationData.getMetadata(TAG_NAME);
        Destination destination =
            destinationServices.getDestination(selectedArg);
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

    public void sendMessage(PlayerContainer player, String message) {
        if(this.configRepository.warpMessageOnActionBar()) {
            player.sendActionBar(Lang.convertColors(message));
        }
        else if(this.configRepository.warpMessageInChat()) {
            player.sendMessage(Lang.getPositivePrefix() + " " + Lang.convertColors(message));
        }
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
