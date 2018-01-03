package com.sekwah.advancedportals.core.api.portal;

public class PortalTag {

    public final String NAME;
    public final String VALUE;
    //public final int type;

    public PortalTag(String argName, String value/*, int type*/) {
        this.NAME = argName;
        this.VALUE = value;
        // may be used if values need to be 100% not string
        //this.type = type;
    }

}
