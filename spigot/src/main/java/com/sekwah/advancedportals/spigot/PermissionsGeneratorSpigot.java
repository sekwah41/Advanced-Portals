package com.sekwah.advancedportals.spigot;


import com.sekwah.advancedportals.core.permissions.PermissionBuilder;
import com.sekwah.advancedportals.core.permissions.Permissions;

public class PermissionsGeneratorSpigot {

    private PermissionsGeneratorSpigot() {
    }

    public static String getPermissions() {
        return toPermBlock(Permissions.ROOT);
    }

    public static String toPermBlock(PermissionBuilder permission) {
        StringBuilder builder = new StringBuilder();
        String indent = "  ";
        if(!permission.isDoNotExport()) {
            builder.append(indent).append(permission.toString()).append(":\n");
            builder.append(indent).append(indent).append("default2ß: ");
            builder.append(permission.getPermissionDefault().toString().toLowerCase());
            builder.append("\n");
            if(permission.getDescription() != null) {
                builder.append(indent).append(indent).append("description: ");
                builder.append(permission.getDescription()).append("\n");
            }
            var children = permission.getGrantChildren();
            if(!children.isEmpty()) {
                builder.append(indent).append(indent).append("children:\n");
                for(PermissionBuilder child : children) {
                    builder.append(indent).append(indent).append(indent);
                    builder.append(child.toString()).append("\n");
                }
            }
        }
        for(PermissionBuilder child : permission.getChildren()) {
            builder.append(toPermBlock(child));
        }
        return builder.toString();
    }
}
