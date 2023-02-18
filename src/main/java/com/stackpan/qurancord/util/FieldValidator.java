package com.stackpan.qurancord.util;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class FieldValidator {

    public static void validateNotNull(Object o) throws IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Nullable.class)) continue;
            field.setAccessible(true);
            if (field.get(o) == null) {
                throw new NullPointerException("Field " + field.getName() + " cannot be null");
            }
        }
    }

}
