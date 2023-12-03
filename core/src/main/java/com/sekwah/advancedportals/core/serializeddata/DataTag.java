package com.sekwah.advancedportals.core.serializeddata;

public class DataTag {

    public final String NAME;
    public final String[] VALUES;

    public DataTag(String argName, String... values) {
        this.NAME = argName;
        this.VALUES = values;
    }

}
