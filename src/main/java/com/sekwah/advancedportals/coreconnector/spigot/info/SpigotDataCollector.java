package com.sekwah.advancedportals.coreconnector.spigot.info;

import com.sekwah.advancedportals.coreconnector.info.DataCollector;
import org.bukkit.Material;

public class SpigotDataCollector implements DataCollector {

    public boolean materialExists(String materialName) {
        String sameCase = materialName.toUpperCase();
        return Material.getMaterial(sameCase) != null;
    }
}
