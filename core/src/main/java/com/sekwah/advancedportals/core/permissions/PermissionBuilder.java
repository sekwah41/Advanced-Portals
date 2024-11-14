package com.sekwah.advancedportals.core.permissions;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;

import java.util.List;

/**
 * this will not currently build the permissions for the files, but maybe at
 * some point. It'll just make it easier though.
 */
public class PermissionBuilder {
    private final String permissionTag;

    private final boolean op;

    private final PermissionBuilder parent;

    private List<PermissionBuilder>[] children;

    PermissionBuilder(String permissionTag) {
        this.permissionTag = permissionTag;
        this.parent = null;
        this.op = false;
    }

    PermissionBuilder(String permissionTag, PermissionBuilder parent) {
        this.permissionTag = permissionTag;
        this.parent = parent;
        this.op = parent.op;
    }

    PermissionBuilder(String permissionTag, PermissionBuilder parent, boolean op) {
        this.permissionTag = permissionTag;
        this.parent = parent;
        this.op = op;
    }

    PermissionBuilder(String permissionTag, boolean op) {
        this.permissionTag = permissionTag;
        this.parent = null;
        this.op = op;
    }

    PermissionBuilder createChild(String permissionTag) {
        return new PermissionBuilder(permissionTag, this);
    }

    PermissionBuilder createChild(String permissionTag, boolean op) {
        return new PermissionBuilder(permissionTag, this, op);
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
        return player.isOp() && player.hasPermission(this.toString());
    }

    public boolean hasPermission(PlayerContainer player) {
        return player.hasPermission(this.toString());
    }
}
