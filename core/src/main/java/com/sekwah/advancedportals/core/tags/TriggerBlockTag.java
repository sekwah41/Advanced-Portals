package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.List;
import java.util.Locale;

public class TriggerBlockTag implements Tag.AutoComplete, Tag.Split, Tag.Creation{

    @Inject
    private ServerContainer serverContainer;

    @Inject
    private InfoLogger infoLogger;

    public static final String TAG_NAME = "triggerblock";

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
        return null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.triggerblock.description");
    }

    @Override
    public List<String> autoComplete(String argData) {
        var triggerBlocks = serverContainer.getCommonTriggerBlocks()
                                .stream()
                                .filter(block -> block.contains(argData))
                                .toList();

        if (triggerBlocks.isEmpty()) {
            return serverContainer.getAllTriggerBlocks();
        }

        return triggerBlocks;
    }

    @Override
    public boolean created(TagTarget target, PlayerContainer player, String[] argData) {
        for (int i = 0; i < argData.length; i++) {
            String material = argData[i].toUpperCase(Locale.ROOT);
            if (!isValidMaterial(material)) {
                infoLogger.warning(Lang.translate("tag.triggerblock.error.invalid_material"));
                return false;
            }
            argData[i] = material; // Save the uppercase material name
        }
        return true;
    }

    @Override
    public void destroyed(TagTarget target, PlayerContainer player, String[] argData) {

    }

    private boolean isValidMaterial(String material) {
        // Assuming serverContainer has a method to get all valid materials
        return serverContainer.getAllTriggerBlocks()
                .stream()
                .anyMatch(validMaterial -> validMaterial.equalsIgnoreCase(material));
    }
}
