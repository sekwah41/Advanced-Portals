package com.sekwah.advancedportals.core.permissions;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import java.util.List;

public class PortalPermissions {
    private static final PermissionBuilder PERMISSIONS =
        new PermissionBuilder("advancedportals");

    public static final PermissionBuilder BUILD =
        PERMISSIONS.createChild("build");
    public static final PermissionBuilder DESTI =
        PERMISSIONS.createChild("desti");
    public static final PermissionBuilder CREATE_PORTAL =
        PERMISSIONS.createChild("createportal");
    public static final PermissionBuilder LANG_UPDATE =
        PERMISSIONS.createChild("langupdate");
    public static final PermissionBuilder RELOAD =
        PERMISSIONS.createChild("reload");

    /**
     * this will not currently build the permissions for the files, but maybe at
     * some point. It'll just make it easier though.
     */
    public static class PermissionBuilder {
        private final String permissionTag;

        private final PermissionBuilder parent;

        private List<PermissionBuilder>[] children;

        PermissionBuilder(String permissionTag) {
            this.permissionTag = permissionTag;
            this.parent = null;
        }

        PermissionBuilder(String permissionTag, PermissionBuilder parent) {
            this.permissionTag = permissionTag;
            this.parent = parent;
        }

        PermissionBuilder createChild(String permissionTag) {
            return new PermissionBuilder(permissionTag, this);
        }

        @Override
        public String toString() {
            if (parent != null) {
                return parent + "." + permissionTag;
            } else {
                return permissionTag;
            }
        }

        public boolean hasPermission(CommandSenderContainer player) {
            return player.hasPermission(this.toString());
        }

        public boolean hasPermission(PlayerContainer player) {
            return player.hasPermission(this.toString());
        }
    }
}
