package com.sekwah.advancedportals.core.registry;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows a portal to register a tag and add a handler. If a plugin wants to add functionality
 * to someone elses tag then they should use the events.
 *
 * @author sekwah41
 */
public class TagRegistry<T> {

    @Inject
    private AdvancedPortalsCore portalsCore;

    /**
     * List of tag names which should be in order alphabetically
     */
    private ArrayList<String> tags = new ArrayList();
    /**
     * Description of tags for help commands (will try to use translation strings before rendering
     * Possibly add a way to allow addons to supply extra info to the Lang class in the future.
     */
    private Map<String, String> tagDesc = new HashMap();
    private Map<String, Tag.Activation> activationHandlers = new HashMap();
    private Map<String, Tag.Creation> creationHandlers = new HashMap();
    private Map<String, Tag.TagStatus> statusHandlers = new HashMap();

    /**
     * Portals to trigger when a portal is activated
     *
     * @param arg
     * @return
     */
    public Tag.Activation getActivationHandler(String arg) {
        return this.activationHandlers.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public Tag.Creation getCreationHandler(String arg) {
        return this.creationHandlers.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public Tag.TagStatus getTagStatusHandler(String arg) {
        return this.statusHandlers.get(arg);
    }


    /**
     * It is reccomended that you use the taghandlers to add tag functionality. However
     * if needed such as extra data for a tag then this is here.
     *
     * @param tag
     * @return if the tag was registered
     */
    private boolean registerTag(String tag) {
        if (tag.contains(" ")) {
            this.portalsCore.getInfoLogger().logWarning("The tag '"
                    + tag + "' is invalid as it contains spaces.");
            return false;
        }
        if (this.tags.contains(tag)) {
            this.portalsCore.getInfoLogger().logWarning("The tag "
                    + tag + " has already been registered.");
            return false;
        }
        this.tags.add(tag);
        Collections.sort(this.tags);
        return true;
    }

    private boolean registerTag(String tag, String desc) {
        if (registerTag(tag)) {
            this.tagDesc.put(tag, desc);
            return true;
        }
        return false;
    }

    /**
     * Returns a non referenced copy of the array list.
     * @return
     */
    public ArrayList<String> getTags() {
        ArrayList<String> newArrayList = new ArrayList<>();
        newArrayList.addAll(this.tags);
        return newArrayList;
    }

    public boolean isTagRegistered(String tag){
        return this.tagDesc.containsKey(tag);
    }

    /**
     * File must extend
     * @return if the tag has been registered or if it already exists.
     */
    public boolean registerTag(String tag, Tag tagHandler) {

        if (tag == null) {
            this.portalsCore.getInfoLogger().logWarning("A tag cannot be null.");
            return false;
        }

        if (!this.registerTag(tag)) {
            return false;
        }

        if (tagHandler instanceof Tag.Activation tagActivation) {
            this.activationHandlers.put(tag, tagActivation);
        }
        if (tagHandler instanceof Tag.TagStatus tagStatus) {
            this.statusHandlers.put(tag, tagStatus);
        }
        if (tagHandler instanceof Tag.Creation tagCreation) {
            this.creationHandlers.put(tag, tagCreation);
        }
        return true;
    }


}
