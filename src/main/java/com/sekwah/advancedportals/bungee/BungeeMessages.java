package com.sekwah.advancedportals.bungee;

public class BungeeMessages {

    /**
     * I may be severely wrong but there is a serious problem with offline UUID's
     */
    public static final String WARNING_MESSAGE = "\u00A7cOffline UUID detected\n" +
            "You should be using IP forwarding as it also forwards UUIDs\n" +
            "The issue of accounts with duplicate names does exist so ignoring this message is a bad idea\n" +
            "as changing a username alters the UUID and people can also log in as anyone if they find the sub server ip\n" +
            "\n" +
            "Instructions to setup IP forwarding https://www.spigotmc.org/wiki/bungeecord-ip-forwarding/\n" +
            "\n" +
            "Publicly complaining about this message will just show everyone you have set up your server insecurely\n" +
            "\n" +
            "If you have a genuine reason to be running without IP forwarding or feel I am wrong about the above statements, " +
            "feel free to DM me on Spigot and I will remove this message if it is a valid reason.\n\n" +
            "This message also will only shows if the bungee is in online mode.";

    /**
     * String in
     * string bungee server
     * string desti name
     * string uuid (of what the server thinks it is)
     *
     * String out (to recieving server)
     * string destination
     * string uuid
     * string offline uuid
     *
     * String out
     */
    public static String ENTER_PORTAL = "PortalEnter";


    public static String SERVER_DESTI = "BungeePortal";

    /**
     * Same in and out. This is read by the bungee
     * String containing command
     */
    public static String BUNGEE_COMMAND = "BungeeCommand";
}
