package com.sekwah.advancedportals.core.permissions;

import com.sekwah.advancedportals.core.connector.containers.HasPermission;

import java.util.List;

/**
 * this will not currently build the permissions for the files, but maybe at
 * some point. It'll just make it easier though.
 */
public class PermissionBuilder {
    private final String permissionTag;

    private final PermissionDefault permissionDefault;

    private final PermissionBuilder parent;

    private List<PermissionBuilder>[] children;

    PermissionBuilder(String permissionTag) {
        this.permissionTag = permissionTag;
        this.parent = null;
        this.permissionDefault = PermissionDefault.TRUE;
    }

    PermissionBuilder(String permissionTag, PermissionBuilder parent) {
        this.permissionTag = permissionTag;
        this.parent = parent;
        this.permissionDefault = parent.permissionDefault;
    }

    PermissionBuilder(String permissionTag, PermissionBuilder parent, PermissionDefault permissionDefault) {
        this.permissionTag = permissionTag;
        this.parent = parent;
        this.permissionDefault = permissionDefault;
    }

    PermissionBuilder(String permissionTag, PermissionDefault permissionDefault) {
        this.permissionTag = permissionTag;
        this.parent = null;
        this.permissionDefault = permissionDefault;
    }

    PermissionBuilder createChild(String permissionTag) {
        return new PermissionBuilder(permissionTag, this);
    }

    PermissionBuilder createChild(String permissionTag, PermissionDefault permissionDefault) {
        return new PermissionBuilder(permissionTag, this, permissionDefault);
    }

    @Override
    public String toString() {
        if (parent != null) {
            return parent + "." + permissionTag;
        } else {
            return permissionTag;
        }
    }

    public boolean hasPermission(HasPermission sender) {
        if(Permissions.hasPermissionManager) {
            return sender.hasPermission(this.toString());
        }
        return switch (permissionDefault) {
            case TRUE -> true;
            case FALSE -> false;
            case OP -> sender.isOp();
            case NOT_OP -> !sender.isOp();
        };
    }

    public enum PermissionDefault {
        TRUE,
        FALSE,
        OP,
        NOT_OP
    }
}
