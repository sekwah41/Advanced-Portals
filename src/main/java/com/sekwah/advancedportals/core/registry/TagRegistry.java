package com.sekwah.advancedportals.core.registry;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.warphandler.TagHandler;

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
     * Description of tags for help commands
     */
    private Map<String, String> tagDesc = new HashMap();
    private Map<String, TagHandler.Activation<T>> activationHandlers = new HashMap();
    private Map<String, TagHandler.Creation<T>> creationHandlers = new HashMap();
    private Map<String, TagHandler.TagStatus<T>> statusHandlers = new HashMap();

    /**
     *
     * @param arg
     * @return
     */
    public TagHandler.Activation<T> getActivationHandler(String arg) {
        return this.activationHandlers.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public TagHandler.Creation<T> getCreationHandler(String arg) {
        return this.creationHandlers.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public TagHandler.TagStatus<T> getTagStatusHandler(String arg) {
        return this.statusHandlers.get(arg);
    }

    /**
     *
     * @param tag
     * @param desc
     * @param tagHandler
     * @return if the tag was registered
     */
    public boolean registerTag(String tag, String desc, TagHandler tagHandler) {
        if (registerTag(tag, tagHandler)) {
            this.tagDesc.put(tag, desc);
        }
        return false;
    }


    /**
     * It is reccomended that you use the taghandlers to add tag functionality. However
     * if needed such as extra data for a tag then this is here.
     *
     * @param tag
     * @return if the tag was registered
     */
    public boolean registerTag(String tag) {
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

    /**
     * Same as registerTag(String tag) but allows a description to be added.
     *
     * @param tag  Tag to be used on command line
     * @param desc
     * @return if the tag was registered
     */
    public boolean registerTag(String tag, String desc) {
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
    public boolean registerTag(String tag, Object tagHandler) {

        if (tag == null) {
            this.portalsCore.getInfoLogger().logWarning("A tag cannot be null.");
            return false;
        }

        if (!this.registerTag(tag)) {
            return false;
        }

        if (tagHandler instanceof TagHandler.Activation) {
            this.activationHandlers.put(tag, (TagHandler.Activation<T>) tagHandler);
        }
        if (tagHandler instanceof TagHandler.TagStatus) {
            this.statusHandlers.put(tag, (TagHandler.TagStatus<T>) tagHandler);
        }
        if (tagHandler instanceof TagHandler.Creation) {
            this.creationHandlers.put(tag, (TagHandler.Creation<T>) tagHandler);
        }
        return true;
    }


}
