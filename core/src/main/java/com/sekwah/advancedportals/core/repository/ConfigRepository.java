package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.serializeddata.DataStorage;

public interface ConfigRepository {

    boolean getUseOnlySpecialAxe();

    void setUseOnlySpecialAxe(boolean useOnlyServerMadeAxe);

    String getTranslation();

    String getSelectorMaterial();

    void loadConfig(DataStorage dataStorage);
}
