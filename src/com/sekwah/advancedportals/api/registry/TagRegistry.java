package com.sekwah.advancedportals.api.registry;

import com.sekwah.advancedportals.api.warphandler.TagHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by on 25/07/2016.
 *
 * @author sekwah41
 */
public class TagRegistry {

    private ArrayList<String> tags = new ArrayList<String>();

    private Map<String, TagInfo> tagDesc = new HashMap<String, TagInfo>();

    // TODO the event can be used for general data detection and management, but use a TagHandler to make it so they can register
    // the individual class to handle.

    public TagRegistry(){

    }


    /**
     *
     * @return if the tag has been registered or if it already exists.
     */
    public boolean registerTag(String tag, TagHandler tagHandler){
        tagDesc.
        if(tagHandler == null){

        }
        else{
            if(!(tagHandler instanceof TagHandler.Activation) && !(tagHandler instanceof TagHandler.TagStatus) &&
                    !(tagHandler instanceof TagHandler.Creation)){

            }
        }
        return true;
    }




}
