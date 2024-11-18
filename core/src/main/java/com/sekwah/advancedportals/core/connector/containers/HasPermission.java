package com.sekwah.advancedportals.core.connector.containers;

public interface HasPermission {
    boolean isOp();
    boolean hasPermission(String permission);
}
