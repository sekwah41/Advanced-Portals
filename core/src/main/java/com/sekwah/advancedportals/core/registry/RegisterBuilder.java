package com.sekwah.advancedportals.core.registry;


import java.lang.reflect.ParameterizedType;

public class RegisterBuilder<T extends CommandHandler> {
    public static RegisterBuilder newBuilder() {
        return new RegisterBuilder();
    }

    private RegisterBuilder() {}

    private boolean allowPermissionInheritance;
    private String scanDirectory;
    private final Class<T> genericType =
            (Class<T>)
                    ((ParameterizedType) getClass().getGenericSuperclass())
                            .getActualTypeArguments()[0];

    public RegisterBuilder<T> inheritPermissions(boolean allowInheritance) {
        allowPermissionInheritance = allowInheritance;
        return this;
    }

    public RegisterBuilder<T> scanDirectory(String directoryName) {
        this.scanDirectory = directoryName;
        return this;
    }
}
