package com.sekwah.advancedportals.proxy;

import java.util.UUID;

public class TargetInfoArray {

    private final String TARGET_SERVER;
    private final String TARGET_DESTINATION;

    public TargetInfoArray(String targetServer, String targetDestination) {
        TARGET_SERVER = targetServer;
        TARGET_DESTINATION = targetDestination;
    }

    public String getTargetDestination() {
        return TARGET_DESTINATION;
    }

    public String getTargetServer() {
        return getTargetServer();
    }
}
