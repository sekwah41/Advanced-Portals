package com.sekwah.advancedportals.core.api.destination;

import com.google.gson.annotations.SerializedName;
import com.sekwah.advancedportals.core.data.PlayerLocation;

import java.util.HashMap;

/**
 * Possibly look at adding the ability to add some tags to destinations such as permissions. Would make it easier
 * to add permissions to block access to certain areas and such. Could be a different permission system or just
 * it takes the tags on the destination and automatically applies them when a portal wants to warp to there.
 * (Of course it would not work cross server unless the data was communicated and checked first however that
 * could effect performance and would definitely effect speed)
 *
 * @author sekwah41
 */
public class Destination {

    @SerializedName("l")
    private PlayerLocation loc;

    @SerializedName("a")
    private HashMap<String, String> args = new HashMap<>();

    public Destination(PlayerLocation loc) {
        this.loc = loc;
    }

    public void setArg(String argName, String argValue) {
        this.args.put(argName, argValue);
    }

    public void removeArg(String arg) {
        this.args.remove(arg);
    }
}
