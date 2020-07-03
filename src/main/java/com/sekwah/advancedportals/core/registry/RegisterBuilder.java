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

    //TODO I don't know if we want to use this as it is marked as Unstable.
    public Registrar<T> build() {
        // Table<String, String, T> commandMap = HashBasedTable.create();
        Map<Cmd, T> commandMap = new HashMap<>();
        ImmutableSet<ClassPath.ClassInfo> classInfo;
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPath classPath = null;
        try {
            classPath = ClassPath.from(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == scanDirectory || scanDirectory.isEmpty()) {
            classInfo = classPath.getTopLevelClasses(scanDirectory);
        } else {
            classInfo = classPath.getTopLevelClasses();
        }

        //TODO implement blackout of already registered commands.
        //TODO If there are duplicates ignore them and throw a warning in console.
        Map<Cmd, Class<?>> commandClasses = classInfo.stream().map(ClassPath.ClassInfo::load)
                .filter(t -> t.isAnnotationPresent(Cmd.class))
                .filter(t -> t.isAssignableFrom(genericType))
                .collect(Collectors.toMap(k -> k.getAnnotation(Cmd.class), k -> k));

        Stream<Map.Entry<Cmd, Class<?>>> result = commandClasses.entrySet().stream();

        result.filter(c -> c.getKey().parentCommand().equals(""))
                .forEach(c -> {
                    try {
                        commandMap.put(c.getKey(), (T) c.getValue().newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        result.filter(c -> c.getKey().parentCommand() != "")
                .forEach(c -> {
                    try {
                        commandMap.put(c.getKey(), (T) c.getValue().newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return new Registrar<>(allowPermissionInheritance, commandMap);
    }


}
