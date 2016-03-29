package com.sekwah.advancedportals.portals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by on 29/03/2016.
 * <p>
 * TODO add all the normal tags then add the extradata tags
 *
 * @author sekwah41
 */
public class PortalTags {

    // TODO create a list or hashmap of tags to check for.

    public Map<String, String> tagDesc = new HashMap<String, String>();

    public ArrayList<String> tags = new ArrayList<String>();


    public void registerTag(String tagName) {
        this.registerTag(tagName, false);
    }

    /**
     * Will only be used if a /portal tags command is created. The descriptions will be used for help text
     * so please keep it short.
     *
     * @param tagName
     * @param description
     */
    public void registerTag(String tagName, boolean multiWord, String description) {
        this.registerTag(tagName, multiWord);
    }

    public void registerTag(String tagName, boolean multiWord) {

    }

}
