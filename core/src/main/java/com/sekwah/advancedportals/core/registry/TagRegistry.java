package com.sekwah.advancedportals.core.registry;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.*;

/**
 * Allows a portal to register a tag and add a handler. If a plugin wants to add functionality
 * to someone elses tag then they should use the events.
 *
 * @author sekwah41
 */
public class TagRegistry {

    @Inject
    AdvancedPortalsCore portalsCore;

    private final ArrayList<String> literalTags = new ArrayList<>();

    private final ArrayList<Tag> tags = new ArrayList<>();

    private final Map<String, Tag.Activation> activationTags = new HashMap<>();
    private final Map<String, Tag.Creation> creationTags = new HashMap<>();
    private final Map<String, Tag.TagStatus> statusTags = new HashMap<>();

    /**
     * Portals to trigger when a portal is activated
     *
     * @param arg
     * @return
     */
    public Tag.Activation getActivationHandler(String arg) {
        return this.activationTags.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public Tag.Creation getCreationHandler(String arg) {
        return this.creationTags.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public Tag.TagStatus getTagStatusHandler(String arg) {
        return this.statusTags.get(arg);
    }

    /**
     * File must extend
     * @return if the tag has been registered or if it already exists.
     */
    public boolean registerTag(Tag tag) {

        String tagName = tag.getName();

        this.tags.add(tag);

        // Check literal tags for clashes
        if(this.literalTags.contains(tagName)) {
            this.portalsCore.getInfoLogger().logWarning("A tag with the name " + tagName + " already exists.");
            return false;
        }

        for (String alias : tag.getAliases()) {
            if(this.literalTags.contains(alias)) {
                this.portalsCore.getInfoLogger().logWarning("A tag with the alias " + alias + " already exists.");
                return false;
            }
        }

        // Add name and aliases to literalTags to check for clashes
        this.literalTags.add(tagName);
        Collections.addAll(this.literalTags, tag.getAliases());

        if (tagName == null) {
            this.portalsCore.getInfoLogger().logWarning("A tag cannot be null.");
            return false;
        }

        if (tag instanceof Tag.Activation tagActivation) {
            this.activationTags.put(tagName, tagActivation);
        }
        if (tag instanceof Tag.TagStatus tagStatus) {
            this.statusTags.put(tagName, tagStatus);
        }
        if (tag instanceof Tag.Creation tagCreation) {
            this.creationTags.put(tagName, tagCreation);
        }
        return true;
    }


    public List<Tag> getTags() {
        // Make a copy of the list to prevent issues with modification

        return this.tags;
    }
}
