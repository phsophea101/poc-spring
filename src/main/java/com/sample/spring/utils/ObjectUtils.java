package com.sample.spring.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtils {

    static <T> T cast(final Object obj, final Class<T> _class) {
        T result = null;

        if (isNotNull(obj)) {
            if (_class.isInstance(obj)) {
                result = _class.cast(obj);
            } else {
                throw new ClassCastException(
                        String.format("class %s cannot be cast to class %s",
                                obj.getClass().getName(),
                                _class.getName()));
            }
        }

        return result;
    }
    static boolean isNull(final Object obj) {
        return obj == null;
    }

    static boolean isNotNull(final Object obj) {
        return !isNull(obj);
    }

}

