package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.serializeddata.DataStorage;

public interface ConfigRepository {

    boolean getUseOnlySpecialAxe();

    String getTranslation();

    String getSelectorMaterial();

    void loadConfig(DataStorage dataStorage);

    int getVisibleRange();

    int getMaxTriggerVisualisationSize();

    String getDefaultTriggerBlock();


}
