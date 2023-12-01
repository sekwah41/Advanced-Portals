package com.sekwah.advancedportals.core.tags.activation;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.List;

public class DestiTag implements Tag.Activation, Tag.AutoComplete {

    private final TagType[] tagTypes = new TagType[]{ TagType.PORTAL };

    @Override
    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String getName() {
        return "destination";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"desti"};
    }

    @Override
    public String description() {
        return Lang.translate("tag.desti.description");
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player, ActivationData activeData, String[] argData) {
        return false;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player, ActivationData activeData, String[] argData) {

    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player, ActivationData activeData, String[] argData) {
        return false;
    }

    @Override
    public List<String> autoComplete(String argData) {
        List<String> autoCompletes = new ArrayList<>();
        // Get all and filter by the argData

        autoCompletes.add("AIR");
        autoCompletes.add("WATER");

        return autoCompletes;
    }
}
