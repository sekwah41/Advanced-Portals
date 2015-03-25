package com.sekwah.advancedportals.portals;

public class PortalArgs {

    public final String argName;
    public final String value;
    //public final int type;

    public PortalArgs(String argName, String value/*, int type*/){
        this.argName = argName;
        this.value = value;
        // may be used if values need to be 100% not string
        //this.type = type;
    }

}
