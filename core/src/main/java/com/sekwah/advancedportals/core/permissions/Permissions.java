package com.sekwah.advancedportals.core.permissions;

public class Permissions {

    private Permissions() {
    }

    /**
     * If true then a permission manager is being used and don't check for op
     *  for platforms like spigot this will always be true.
     * <p>
     *  This is to allow for negative permissions where a value may be defaulted to true.
     */
    public static boolean hasPermissionManager = false;

    private static final PermissionBuilder ROOT =
        new PermissionBuilder("advancedportals");

    public static final PermissionBuilder BUILD =
        ROOT.createChild("build");
    public static final PermissionBuilder DESTI =
        ROOT.createChild("desti");
    public static final PermissionBuilder PORTAL =
            ROOT.createChild("portal");

    private static final PermissionBuilder CREATE =
            ROOT.createChild("create");

    public static final PermissionBuilder CREATE_PORTAL =
            CREATE.createChild("portal");

    public static final PermissionBuilder LANG_UPDATE =
        ROOT.createChild("lang_update");
    public static final PermissionBuilder RELOAD =
        ROOT.createChild("reload");

    public class CreateCommandLevel {
        private CreateCommandLevel() {
        }

        private static final PermissionBuilder CREATE_COMMAND_LEVEL =
                CREATE_PORTAL.createChild("commandlevel");

        public static final PermissionBuilder OP =
                CREATE_COMMAND_LEVEL.createChild("op");

        public static final PermissionBuilder CONSOLE =
                CREATE_COMMAND_LEVEL.createChild("console");

        public static final PermissionBuilder PERMSWILD =
                CREATE_COMMAND_LEVEL.createChild("permswild");

    }

}
