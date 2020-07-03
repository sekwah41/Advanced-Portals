package com.sekwah.advancedportals.core.registry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Cmd {
    String name();
    //TODO Convert to enum
    String parentCommand() default "";
    boolean isEnabled() default true;
    int minArgs() default 0;
    String description() default "";
    String[] permissions() default {};
}
