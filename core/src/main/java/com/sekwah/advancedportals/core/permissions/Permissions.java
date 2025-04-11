package com.sekwah.advancedportals.core.permissions;

public class Permissions {
    /**
     * If true then a permission manager is being used and don't check for op
     *  for platforms like spigot this will always be true.
     * <p>
     *  This is to allow for negative permissions where a value may be defaulted
     * to true.
     */
    public static boolean hasPermissionManager = false;

    public static final PermissionBuilder ROOT =
        new PermissionBuilder("advancedportals").doNotExport();

    public static final PermissionBuilder BUILD =
        ROOT.createChild("build", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to build in the portal regions");

    public static final PermissionBuilder DESTI =
        ROOT.createChild("desti", PermissionBuilder.PermissionDefault.TRUE)
            .description("Allows you to use the destination command");

    public static final PermissionBuilder CREATE_DESTI =
        DESTI.createChild("create", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to create destinations");

    public static final PermissionBuilder TELEPORT_DESTI =
        DESTI.createChild("teleport", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to teleport to destinations");

    public static final PermissionBuilder REMOVE_DESTI =
        DESTI.createChild("remove", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to remove destinations");

    public static final PermissionBuilder LIST_DESTI =
        DESTI.createChild("list", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to list all destinations");

    public static final PermissionBuilder SHOW_DESTI =
        DESTI.createChild("show", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to visualise the destination locations");

    public static final PermissionBuilder TELEPORT_PLAYER_DESTI =
            DESTI.createChild("teleportplayer", PermissionBuilder.PermissionDefault.OP)
                    .description("Allows you to teleport a player to a destination");

    public static final PermissionBuilder PORTAL =
        ROOT.createChild("portal", PermissionBuilder.PermissionDefault.TRUE)
            .description("Allows you to use the portal command");

    public static final PermissionBuilder CREATE_PORTAL =
        PORTAL.createChild("create", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to create portals");

    public static final PermissionBuilder SELECTOR =
        PORTAL.createChild("selector", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to give yourself a portal selector");

    public static final PermissionBuilder REMOVE_PORTAL =
        PORTAL.createChild("remove", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to remove portals");

    public static final PermissionBuilder LIST_PORTAL =
        PORTAL.createChild("list", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to list all portals");

    public static final PermissionBuilder LANG_UPDATE = PORTAL.createChild(
        "lang_update", PermissionBuilder.PermissionDefault.OP);
    public static final PermissionBuilder RELOAD =
        PORTAL.createChild("reload", PermissionBuilder.PermissionDefault.OP);

    public static final PermissionBuilder DISABLE_BEACON = PORTAL.createChild(
        "disable_beacon", PermissionBuilder.PermissionDefault.OP);
    public static final PermissionBuilder IMPORT =
        PORTAL.createChild("import", PermissionBuilder.PermissionDefault.OP);

    public static final PermissionBuilder SHOW_PORTAL =
        PORTAL.createChild("show", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to view the portal regions");

    private static final PermissionBuilder CREATE_COMMAND_LEVEL =
        CREATE_PORTAL.createChild("command_level").doNotExport();

    public static final PermissionBuilder CREATE_COMMAND_OP =
        CREATE_COMMAND_LEVEL.createChild("op").description(
            "Allows you to increase the users level temporarily to op");

    public static final PermissionBuilder CREATE_COMMAND_CONSOLE =
        CREATE_COMMAND_LEVEL.createChild("console").description(
            "Allows you to create portals which execute console commands");

    public static final PermissionBuilder CREATE_COMMAND_PERMS =
        CREATE_COMMAND_LEVEL.createChild("perms_wildcard")
            .description("Allows you to increase the users level temporarily "
                         + "to have all perms");

    public static final PermissionBuilder PORTAL_INFO =
        PORTAL.createChild("info", PermissionBuilder.PermissionDefault.OP)
            .description("Allows you to view portal information");

    static {
        // These are to add children which will not be used directly e.g.
        // advancedportals.*
        ROOT.createChild("*", PermissionBuilder.PermissionDefault.OP)
            .description("Gives access to all portal commands")
            .addGrantChild(CREATE_PORTAL)
            .addGrantChild(CREATE_DESTI)
            .addGrantChild(DESTI)
            .addGrantChild(PORTAL)
            .addGrantChild(TELEPORT_DESTI)
            .addGrantChild(REMOVE_DESTI)
            .addGrantChild(LIST_DESTI)
            .addGrantChild(SHOW_DESTI)
            .addGrantChild(TELEPORT_PLAYER_DESTI)
            .addGrantChild(SELECTOR)
            .addGrantChild(REMOVE_PORTAL)
            .addGrantChild(LIST_PORTAL)
            .addGrantChild(LANG_UPDATE)
            .addGrantChild(RELOAD)
            .addGrantChild(DISABLE_BEACON)
            .addGrantChild(IMPORT)
            .addGrantChild(SHOW_PORTAL)
            .addGrantChild(PORTAL_INFO)
            .addGrantChild(BUILD);

        CREATE_COMMAND_LEVEL
            .createChild("*", PermissionBuilder.PermissionDefault.OP)
            .description("Gives access to all command level raisers")
            .addGrantChild(CREATE_COMMAND_OP)
            .addGrantChild(CREATE_COMMAND_CONSOLE)
            .addGrantChild(CREATE_COMMAND_PERMS);
    }
}
