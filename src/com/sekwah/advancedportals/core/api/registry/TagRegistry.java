package com.sekwah.advancedportals.core.api.registry;

import com.sekwah.advancedportals.core.api.warphandler.TagHandler;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;

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
public class TagRegistry {

    /**
     * List of tag names which should be in order alphabetically
     */
    private ArrayList<String> tags = new ArrayList();
    /**
     * Description of tags for help commands
     */
    private Map<String, String> tagDesc = new HashMap();
    private Map<String, TagHandler.Activation> activationHandlers = new HashMap();
    private Map<String, TagHandler.Creation> creationHandlers = new HashMap();
    private Map<String, TagHandler.TagStatus> statusHandlers = new HashMap();

    /**
     *
     * @param arg
     * @return
     */
    public TagHandler.Activation getActivationHandler(String arg) {
        return this.activationHandlers.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public TagHandler.Creation getCreationHandler(String arg) {
        return this.creationHandlers.get(arg);
    }

    /**
     *
     * @param arg
     * @return
     */
    public TagHandler.TagStatus getTagStatusHandler(String arg) {
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
            AdvancedPortalsCore.getInfoLogger().logWarning("The tag '"
                    + tag + "' is invalid as it contains spaces.");
            return false;
        }
        if (this.tags.contains(tag)) {
            AdvancedPortalsCore.getInfoLogger().logWarning("The tag "
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
     * @return if the tag has been registered or if it already exists.
     */
    public  boolean registerTag(String tag, TagHandler tagHandler) {

        if (tag == null) {
            AdvancedPortalsCore.getInfoLogger().logWarning("A tag cannot be null.");
            return false;
        }

        if (!this.registerTag(tag)) {
            return false;
        }

        if (tagHandler != null && !(tagHandler instanceof TagHandler.Activation) && !(tagHandler instanceof TagHandler.TagStatus) &&
                !(tagHandler instanceof TagHandler.Creation)) {
            AdvancedPortalsCore.getInfoLogger().logWarning("Error with tag: " + tag + ". A tag handler must implement one of the handlers. Not just extend.");
            if (tagHandler instanceof TagHandler.Activation) {
                this.activationHandlers.put(tag, (TagHandler.Activation) tagHandler);
            }
            if (tagHandler instanceof TagHandler.TagStatus) {
                this.statusHandlers.put(tag, (TagHandler.TagStatus) tagHandler);
            }
            if (tagHandler instanceof TagHandler.Creation) {
                this.creationHandlers.put(tag, (TagHandler.Creation) tagHandler);
            }
        }
        return true;
    }


}
