package com.sekwah.advancedportals.core.registry;

public @interface SubCmd {

    TYPE parent();

    String name();

    int minArgs();

    String[] permissions();

    public enum TYPE {
        PORTAL,
        DESTI
    }

}
