package com.sekwah.advancedportals.core.permissions;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.core.connector.containers.HasPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * this will not currently build the permissions for the files, but maybe at
 * some point. It'll just make it easier though.
 */
public class PermissionBuilder {
    private final String permissionTag;
    private final PermissionDefault permissionDefault;
    private final PermissionBuilder parent;
    private final List<PermissionBuilder> children = new ArrayList<>();
    private final List<PermissionBuilder> grantChildren = new ArrayList<>();
    private String description;
    private boolean doNotExport = false;

    PermissionBuilder(String permissionTag) {
        this.permissionTag = permissionTag;
        this.parent = null;
        this.permissionDefault = PermissionDefault.FALSE;
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

    public PermissionDefault getPermissionDefault() {
        return permissionDefault;
    }

    public PermissionBuilder createChild(String permissionTag) {
        var child = new PermissionBuilder(permissionTag, this);
        children.add(child);

        return child;
    }

    public PermissionBuilder createChild(String permissionTag, PermissionDefault permissionDefault) {
        var child = new PermissionBuilder(permissionTag, this, permissionDefault);
        children.add(child);

        return child;
    }

    public PermissionBuilder addGrantChild(PermissionBuilder child) {
        grantChildren.add(child);
        return this;
    }

    public PermissionBuilder doNotExport() {
        this.doNotExport = true;
        return this;
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
        if (Permissions.hasPermissionManager) {
            return sender.hasPermission(this.toString());
        }
        return switch (permissionDefault) {
            case TRUE -> true;
            case FALSE -> false;
            case OP -> sender.isOp();
            case NOT_OP -> !sender.isOp();
        };
    }

    public List<PermissionBuilder> getChildren() {
        return ImmutableList.copyOf(children);
    }

    public List<PermissionBuilder> getGrantChildren() {
        return ImmutableList.copyOf(grantChildren);
    }

    public String getDescription() {
        return description;
    }

    public boolean isDoNotExport() {
        return doNotExport;
    }

    public PermissionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public enum PermissionDefault {
        TRUE,
        FALSE,
        OP,
        NOT_OP
    }
}
