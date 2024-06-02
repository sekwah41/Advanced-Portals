package com.sekwah.advancedportals.core.serializeddata;

import com.sekwah.advancedportals.core.util.InfoLogger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import sun.misc.Unsafe;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReflectiveConstructor<T> extends Constructor {
    private static final Unsafe unsafe = getUnsafe();
    private final Class<T> clazz;

    @Inject
    private InfoLogger infoLogger;

    public ReflectiveConstructor(Class<T> clazz, LoaderOptions loadingConfig) {
        super(clazz, loadingConfig);
        this.clazz = clazz;
    }

    @Override
    protected Object constructObject(Node node) {
        if (node instanceof MappingNode) {
            return constructFromMappingNode((MappingNode) node);
        } else if (node instanceof ScalarNode) {
            return constructFromScalarNode((ScalarNode) node);
        } else {
            infoLogger.warning("Unexpected node type encountered: " + node.getClass().getSimpleName());
            return null;
        }
    }

    private Object constructFromMappingNode(MappingNode mappingNode) {

        try {
            Object instance = unsafe.allocateInstance(clazz);
            Map<String, Object> mappedValues = mapMappingNode(mappingNode);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isSynthetic() || Modifier.isTransient(field.getModifiers())) continue;
                makeFieldAccessible(field);
                if (mappedValues.containsKey(field.getName())) {
                    Object value = mappedValues.get(field.getName());
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            infoLogger.warning("Failed to instantiate " + clazz.getName() + ": " + e.getMessage());
            throw new RuntimeException("Failed to instantiate " + clazz.getName(), e);
        }
    }

    private Object constructFromScalarNode(ScalarNode scalarNode) {
        return super.constructObject(scalarNode);
    }

    private Map<String, Object> mapMappingNode(MappingNode mappingNode) {
        Map<String, Object> values = new HashMap<>();
        for (NodeTuple tuple : mappingNode.getValue()) {
            String key = (String) constructObject(tuple.getKeyNode());
            Object value = constructObject(tuple.getValueNode());
            values.put(key, value);
        }
        return values;
    }

    private void makeFieldAccessible(Field field) throws NoSuchFieldException, IllegalAccessException {
        if (!field.isAccessible() || Modifier.isFinal(field.getModifiers())) {
            field.setAccessible(true);
            if (Modifier.isFinal(field.getModifiers())) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
        }
    }

    private static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Unsafe instance", e);
        }
    }
}
