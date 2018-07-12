package com.sekwah.advancedportals.coreconnector.info;

import java.util.List;

/**
 * Gets info from the specific implementation
 */
public interface DataCollector {

    boolean materialExists(String materialName);

    List<String> getMaterials();

}
