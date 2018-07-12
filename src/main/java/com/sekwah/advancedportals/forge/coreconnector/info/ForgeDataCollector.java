package com.sekwah.advancedportals.forge.coreconnector.info;

import com.sekwah.advancedportals.coreconnector.info.DataCollector;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ForgeDataCollector implements DataCollector {

    private final List<String> blockMaterialList = new ArrayList<>();

    public ForgeDataCollector() {
        /**
         * TODO Look at
         * {@link net.minecraft.command.CommandGive#getTabCompletions}
         * for inspiration for both spigot and forge versions
         */
        //Block.REGISTRY.getKeys()
    }

    /**
     * @param materialName this is given in the format minecraft:material
     * @return
     */
    public boolean materialExists(String materialName) {
        return Block.getBlockFromName(materialName) != null;
    }

    @Override
    public List<String> getMaterials() {
        return null;
    }
}
