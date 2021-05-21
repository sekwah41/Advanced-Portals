package com.sekwah.advancedportals.bukkit.api.portaldata;

public class PortalArg {

    public final String argName;
    public final String value;

    public PortalArg(String argName, String value/*, int type*/) {
        this.argName = argName;
        this.value = value;
        // may be used if values need to be 100% not string
        //this.type = type;
    }

}
