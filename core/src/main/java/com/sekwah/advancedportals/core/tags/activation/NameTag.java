package com.sekwah.advancedportals.core.tags.activation;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.List;

public class NameTag implements Tag.Activation, Tag.AutoComplete {

    private final TagType[] tagTypes = new TagType[]{ TagType.PORTAL };

    @Override
    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.name.description");
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
        return null;
    }
}
