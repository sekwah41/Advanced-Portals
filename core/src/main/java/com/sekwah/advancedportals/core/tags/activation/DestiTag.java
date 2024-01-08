package com.sekwah.advancedportals.core.tags.activation;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.List;

public class DestiTag implements Tag.Activation, Tag.AutoComplete {

    public static String TAG_NAME = "destination";
    @Inject
    DestinationServices destinationServices;

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
        return destinationServices.getDestinationNames();
    }
}