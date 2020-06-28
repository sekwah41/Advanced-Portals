package com.sekwah.advancedportals.spigot.coreconnector.info;

import com.sekwah.advancedportals.core.connector.info.DataCollector;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SpigotDataCollector implements DataCollector {

    private final List<String> blockMaterialList = new ArrayList<>();

    public SpigotDataCollector() {
        for(Material material : Material.values()) {
            this.blockMaterialList.add(material.name());
        }
    }

    public boolean materialExists(String materialName) {
        String sameCase = materialName.toUpperCase();
        return Material.getMaterial(sameCase) != null;
    }

    @Override
    public List<String> getMaterials() {
        return blockMaterialList;
    }
}
