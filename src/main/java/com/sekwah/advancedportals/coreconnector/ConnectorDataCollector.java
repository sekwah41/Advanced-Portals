package com.sekwah.advancedportals.coreconnector;

import org.bukkit.Material;

/**
 * Gets info from the specific implementation
 */
public class ConnectorDataCollector {
    public boolean materialExists(String materialName) {
        String sameCase = materialName.toUpperCase();
        return Material.getMaterial(sameCase) != null;
    }

}
