package com.sekwah.advancedportals.core.tags.activation;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TriggerBlockTag implements Tag.AutoComplete {

    @Inject
    private ServerContainer serverContainer;

    public static String TAG_NAME = "triggerblock";

    private final TagType[] tagTypes = new TagType[]{ TagType.PORTAL };

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
        return Lang.translate("tag.triggerblock.description");
    }

    @Override
    public List<String> autoComplete(String argData) {
        boolean endsWithComma = argData.endsWith(",");
        String[] items = argData.split(",");
        Set<String> existingItems = Arrays.stream(items, 0, endsWithComma ? items.length : items.length - 1)
                .map(String::trim)
                .collect(Collectors.toSet());

        String partialInput = endsWithComma ? "" : items[items.length - 1].trim();
        String baseString = endsWithComma ? argData : argData.substring(0, argData.lastIndexOf(",") + 1);

        return serverContainer.getTriggerBlocks().stream()
                // Remove already listed items
                .filter(s -> !existingItems.contains(s))
                // Remove items that don't match the currently typed input
                .filter(s -> s.startsWith(partialInput))
                // Remap so the auto completes actually show
                .map(s -> baseString + s)
                .collect(Collectors.toList());
    }
}
