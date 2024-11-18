package com.sekwah.advancedportals.core.permissions;

import com.sekwah.advancedportals.core.commands.subcommands.portal.DisableBeaconSubCommand;

public class Permissions {

    /**
     * If true then a permission manager is being used and don't check for op
     *  for platforms like spigot this will always be true.
     * <p>
     *  This is to allow for negative permissions where a value may be defaulted to true.
     */
    public static boolean hasPermissionManager = false;

    public static final PermissionBuilder ROOT =
        new PermissionBuilder("advancedportals").doNotExport();

    public static final PermissionBuilder BUILD =
        ROOT.createChild("build", PermissionBuilder.PermissionDefault.OP).description("Allows you to build in the portal regions");
    public static final PermissionBuilder DESTI =
        ROOT.createChild("desti", PermissionBuilder.PermissionDefault.TRUE).description("Allows you to use the destination command");
    public static final PermissionBuilder PORTAL =
            ROOT.createChild("portal", PermissionBuilder.PermissionDefault.TRUE).description("Allows you to use the portal command");

    public static final PermissionBuilder CREATE_PORTAL =
            PORTAL.createChild("create", PermissionBuilder.PermissionDefault.OP).description("Allows you to create portals");

    public static final PermissionBuilder CREATE_DESTI =
            DESTI.createChild("create", PermissionBuilder.PermissionDefault.OP).description("Allows you to create destinations");

    public static final PermissionBuilder LANG_UPDATE =
        ROOT.createChild("lang_update", PermissionBuilder.PermissionDefault.OP);
    public static final PermissionBuilder RELOAD =
        ROOT.createChild("reload", PermissionBuilder.PermissionDefault.OP);

    public static final PermissionBuilder DISABLE_BEACON =
            ROOT.createChild("disable_beacon", PermissionBuilder.PermissionDefault.OP);
    public static final PermissionBuilder IMPORT =
            ROOT.createChild("import", PermissionBuilder.PermissionDefault.OP);

    private static final PermissionBuilder CREATE_COMMAND_LEVEL =
            CREATE_PORTAL.createChild("command_level").doNotExport();

    public static final PermissionBuilder CREATE_COMMAND_OP =
            CREATE_COMMAND_LEVEL.createChild("op").description("Allows you to increase the users level temporarily to op");

    public static final PermissionBuilder CREATE_COMMAND_CONSOLE =
            CREATE_COMMAND_LEVEL.createChild("console").description("Allows you to create portals which execute console commands");

    public static final PermissionBuilder CREATE_COMMAND_PERMS =
            CREATE_COMMAND_LEVEL.createChild("perms_wildcard").description("Allows you to increase the users level temporarily to have all perms");

    static {
        // These are to add children which will not be used directly e.g. advancedportals.*
        ROOT.createChild("*", PermissionBuilder.PermissionDefault.OP).description("Gives access to all portal commands")
                .addGrantChild(CREATE_PORTAL)
                .addGrantChild(CREATE_DESTI)
                .addGrantChild(DESTI)
                .addGrantChild(PORTAL);

        CREATE_COMMAND_LEVEL.createChild("*", PermissionBuilder.PermissionDefault.OP).description("Gives access to all command level raisers")
                .addGrantChild(CREATE_COMMAND_OP)
                .addGrantChild(CREATE_COMMAND_CONSOLE)
                .addGrantChild(CREATE_COMMAND_PERMS);
    }
}
