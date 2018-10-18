package com.sekwah.advancedportals.reflection;

import java.lang.reflect.Field;

public class ReflectionHelper {

    public static Field getFieldByType(Class<?> clazz, Class<?> findingType, boolean isAccessable) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            if(field.getType() == findingType && field.isAccessible() == isAccessable) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }
}
