package com.sekwah.advancedportals;

import com.sekwah.advancedportals.data.DataStorage;

public interface ConfigRepository {

    boolean getUseOnlySpecialAxe();

    void setUseOnlySpecialAxe(boolean useOnlyServerMadeAxe);

    String getTranslation();

    String getSelectorMaterial();

    void loadConfig(DataStorage dataStorage);
}
