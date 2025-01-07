package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.List;

public class TriggerBlockTag implements Tag.AutoComplete, Tag.Split, Tag.Creation {

    @Inject
    private ServerContainer serverContainer;

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
            String material = serverContainer.matchMaterialName(argData[i]);
            if (material == null || !isValidMaterial(material)) {
                player.sendMessage(Lang.getNegativePrefix() + Lang.translate("tag.triggerblock.error.invalidmaterial"));
                return false;
            }
            argData[i] = material;
        }
        return true;
    }

    @Override
    public void destroyed(TagTarget target, PlayerContainer player, String[] argData) {

    }

    private boolean isValidMaterial(String material) {
        return serverContainer.getAllTriggerBlocks()
                .stream()
                .anyMatch(validMaterial -> validMaterial.equalsIgnoreCase(material));
    }
}