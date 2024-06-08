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
        return this.constructObject(clazz, node);
    }

    private Object constructObject(Class<?> currentClass, Node node) {
        infoLogger.log("Constructing object from node: " + node);
        if (node instanceof MappingNode) {
            return constructFromMappingNode(currentClass, (MappingNode) node);
        } else if (node instanceof ScalarNode) {
            return constructFromScalarNode((ScalarNode) node);
        } else {
            infoLogger.warning("Unexpected node type encountered: " + node.getClass().getSimpleName());
            return null;
        }
    }

    private <U> Object constructFromMappingNode(Class<U> currentClass, MappingNode mappingNode) {
        System.out.println("Constructing object from mapping node " + mappingNode.getValue());

        try {
            Object instance = unsafe.allocateInstance(currentClass);
            Map<String, Object> mappedValues = mapMappingNode(currentClass, mappingNode);

            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                try {

                    if (field.isSynthetic() || Modifier.isTransient(field.getModifiers())) continue;
                    makeFieldAccessible(field);
                    if (mappedValues.containsKey(field.getName())) {
                        Object value = mappedValues.get(field.getName());

                        infoLogger.log("Setting field " + field.getName() + " to " + value + " in " + currentClass.getName());
                        field.set(instance, value);
                    }
                }
                catch (Exception e) {
                    infoLogger.warning("Failed to set field " + field.getName() + " in " + currentClass.getName() + ": " + e.getMessage());
                    throw new RuntimeException("Failed to set field " + field.getName() + " in " + currentClass.getName(), e);
                }
            }
            return instance;
        } catch (Exception e) {
            infoLogger.warning("Failed to instantiate " + currentClass.getName() + ": " + e.getMessage());
            throw new RuntimeException("Failed to instantiate " + currentClass.getName(), e);
        }
    }

    private Object constructFromScalarNode(ScalarNode scalarNode) {
        System.out.println("Constructing object from scalar node " + scalarNode.getValue());
        return super.constructObject(scalarNode);
    }

    private Map<String, Object> mapMappingNode(Class<?> currentClass, MappingNode mappingNode) {
        Map<String, Object> values = new HashMap<>();
        for (NodeTuple tuple : mappingNode.getValue()) {
            var key = (String) constructObject(tuple.getKeyNode());

            var node = tuple.getValueNode();

            if (node instanceof ScalarNode scalarNode) {
                values.put(key, constructFromScalarNode(scalarNode));
            } if (node instanceof MappingNode mappingNodeChild) {
                try {
                    var field = currentClass.getDeclaredField(key);

                    infoLogger.log("Looking for field " + key + " on " + currentClass.getName());
                    // Output the type of the field
                    infoLogger.log("Field type: " + field.getType().getName());

                    Object value = constructFromMappingNode(field.getType(), mappingNodeChild);

                    infoLogger.log("Mapping key " + key + " to value " + value);
                    values.put(key, value);
                } catch (NoSuchFieldException e) {
                    infoLogger.warning("Field " + key + " not found on " + currentClass.getName());
                }
            } else {
                infoLogger.warning("Unexpected node type encountered: " + node.getClass().getSimpleName());
            }
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
