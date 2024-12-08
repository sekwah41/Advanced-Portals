package com.sekwah.advancedportals.core.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.effect.WarpEffect;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.registry.WarpEffectRegistry;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.repository.IDestinationRepository;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DestinationServices {
    @Inject
    private IDestinationRepository destinationRepository;

    @Inject
    private WarpEffectRegistry warpEffectRegistry;

    @Inject
    private ConfigRepository configRepository;

    @Inject
    TagRegistry tagRegistry;

    private final Map<String, Destination> destinationCache = new HashMap<>();

    public List<String> getDestinationNames() {
        return destinationRepository.getAllNames();
    }

    public List<Destination> getDestinations() {
        return new ArrayList<>(destinationCache.values());
    }

    public void loadDestinations() {
        List<String> destinationNames = destinationRepository.getAllNames();
        destinationCache.clear();
        for (String name : destinationNames) {
            Destination destination = destinationRepository.get(name);
            destinationCache.put(name, destination);
        }
    }

    public Destination createDesti(PlayerLocation playerLocation,
                                   List<DataTag> tags) {
        return createDesti(null, playerLocation, tags);
    }

    public Destination createDesti(PlayerContainer player,
                                   PlayerLocation playerLocation,
                                   List<DataTag> tags) {
        // Find the tag with the "name" NAME
        DataTag nameTag = tags.stream()
                              .filter(tag -> tag.NAME.equals("name"))
                              .findFirst()
                              .orElse(null);

        String name = nameTag == null ? null : nameTag.VALUES[0];

        // If the name is null, send an error saying that the name is required.
        if (nameTag == null) {
            if (player != null)
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("desti.error.noname"));
            return null;
        }

        if (name == null || name.equals("")) {
            if (player != null)
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("command.error.noname"));
            return null;
        } else if (this.destinationRepository.containsKey(name)) {
            if (player != null)
                player.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translateInsertVariables(
                                       "command.error.nametaken", name));
            return null;
        }

        Destination desti = new Destination(playerLocation);
        for (DataTag portalTag : tags) {
            desti.setArgValues(portalTag);
        }
        for (DataTag destiTag : tags) {
            Tag.Creation creation =
                tagRegistry.getCreationHandler(destiTag.NAME);
            if (creation != null) {
                if (!creation.created(desti, player, destiTag.VALUES)) {
                    return null;
                }
            }
        }
        try {
            if (this.destinationRepository.save(name, desti)) {
                this.destinationCache.put(name, desti);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("desti.error.save"));
        }
        return desti;
    }

    public boolean removeDestination(String name,
                                     PlayerContainer playerContainer) {
        this.destinationCache.remove(name);
        if (this.destinationRepository.containsKey(name)) {
            this.destinationRepository.delete(name);
            return true;
        }
        return false;
    }

    public Destination getDestination(String name) {
        return destinationCache.get(name);
    }

    public boolean teleportToDestination(String name,
                                         PlayerContainer playerContainer) {
        return teleportToDestination(name, playerContainer, false);
    }

    public boolean teleportToDestination(String name, PlayerContainer player,
                                         boolean doEffect) {
        if (this.destinationRepository.containsKey(name)) {
            player.teleport(this.destinationRepository.get(name).getLoc());
            if (doEffect && configRepository.getWarpEffectEnabled()) {
                var warpEffectVisual = warpEffectRegistry.getVisualEffect(
                    configRepository.getWarpVisual());
                if (warpEffectVisual != null) {
                    warpEffectVisual.onWarpVisual(player,
                                                  WarpEffect.Action.ENTER);
                }
                var warpEffectSound = warpEffectRegistry.getSoundEffect(
                    configRepository.getWarpSound());
                if (warpEffectSound != null) {
                    warpEffectSound.onWarpSound(player,
                                                WarpEffect.Action.ENTER);
                }
            }
            return true;
        }
        return false;
    }
}
