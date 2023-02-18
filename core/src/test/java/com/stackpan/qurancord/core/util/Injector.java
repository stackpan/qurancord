package com.stackpan.qurancord.core.util;

import com.stackpan.qurancord.core.annotation.Injectable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Injector<T, V> {

    public void injectField(T u, V value) throws IllegalAccessException {
        Field[] fields = u.getClass().getDeclaredFields();

        for (Field field : fields) {
            Annotation injectable = field.getAnnotation(Injectable.class);
            if (injectable != null) {
                field.setAccessible(true);
                field.set(u, value);
                field.setAccessible(false);
            }
        }
    }

}
