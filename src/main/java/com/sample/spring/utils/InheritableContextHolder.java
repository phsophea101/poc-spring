package com.sample.spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class InheritableContextHolder {
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL = new InheritableThreadLocal() {
        @Override
        protected Map<String, Object> initialValue() {
            return new ConcurrentHashMap<>();
        }
    };

    private InheritableContextHolder() {
    }

    public static void setObject(final String key, final Object value) {
        if (StringUtils.isEmpty(key) || Objects.isNull(value)) {
            log.debug("Key {} or value {} is empty cannot put into inheritable thread local", key, value);
            return;
        }
        THREAD_LOCAL.get().put(key, value);
    }
    public static <T> T getObject(final String key, final Class<T> _class) {
        return ObjectUtils.cast(getObject(key), _class);
    }

    public static Object getObject(final String key) {
        return THREAD_LOCAL.get().get(key);
    }

    public static Object remove(final String key) {
        return THREAD_LOCAL.get().remove(key);
    }
}