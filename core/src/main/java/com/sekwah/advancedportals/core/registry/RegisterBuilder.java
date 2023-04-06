package com.sekwah.advancedportals.core.registry;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegisterBuilder<T extends CommandHandler> {
    public static RegisterBuilder newBuilder() {
        return new RegisterBuilder();
    }

    private RegisterBuilder() {
    }

    private boolean allowPermissionInheritance;
    private String scanDirectory;
    private final Class<T> genericType = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];


    public RegisterBuilder<T> inheritPermissions(boolean allowInheritance) {
        allowPermissionInheritance = allowInheritance;
        return this;
    }

    public RegisterBuilder<T> scanDirectory(String directoryName) {
        this.scanDirectory = directoryName;
        return this;
    }

}
