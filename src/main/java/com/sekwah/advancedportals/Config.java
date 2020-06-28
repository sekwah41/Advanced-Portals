package com.sekwah.advancedportals;

import java.lang.reflect.Type;

public class Config {

    public Config(String key, String classType, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private Type type;
    private String value;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
