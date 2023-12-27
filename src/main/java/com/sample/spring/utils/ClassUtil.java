package com.sample.spring.utils;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.util.Date;

public class ClassUtil extends ClassUtils {

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Assert.notNull(clazz, "Class required");
        return clazz.getConstructor(parameterTypes);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object... initargs) {
        try {
            return (T) getConstructor(clazz, parameterTypes).newInstance(initargs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> forName(final String className) {
        try {
            return getClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isJavaLangType(Object object) {
        if (object == null) return false;
        return isJavaLangType(object.getClass());
    }

    public static boolean isJavaLangType(Class<?> clazz) {
        if (clazz == null) return false;
        return isAssignable(clazz, String.class)
                || isAssignable(clazz, Number.class)
                || isAssignable(clazz, Boolean.class)
                || isAssignable(clazz, Date.class)
                || isAssignable(clazz, Enum.class)
                || isAssignable(clazz, Byte.class);
    }
}
