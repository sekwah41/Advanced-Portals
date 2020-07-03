package com.sekwah.advancedportals.core.registry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//There is no reason to run a double class file when we can just make the main command default out.
@Deprecated()
@Retention(RetentionPolicy.RUNTIME)
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
