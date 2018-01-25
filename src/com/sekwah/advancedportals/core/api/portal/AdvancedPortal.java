package com.sekwah.advancedportals.core.api.portal;

import com.sekwah.advancedportals.core.data.PortalLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sekwah41
 */
public class AdvancedPortal {
    private PortalLocation maxLoc;

    private PortalLocation minLoc;

    private HashMap<String, String> args = new HashMap<>();

    public AdvancedPortal(PortalLocation maxLoc, PortalLocation minLoc) {
        this.maxLoc = maxLoc;
        this.minLoc = minLoc;
    }

    public PortalLocation getMaxLoc() {
        return this.maxLoc;
    }

    public PortalLocation getMinLoc() {
        return this.minLoc;
    }

    public String getArg(String argName) {
        return this.args.get(argName);
    }

    public void setArg(String argName, String argValue) {
        this.args.put(argName, argValue);
    }
}
