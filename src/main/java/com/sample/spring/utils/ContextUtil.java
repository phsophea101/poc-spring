package com.sample.spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@Slf4j
public final class ContextUtil {

    private static ApplicationContext context;

    public static boolean isApplicationContextActive() {
        return (context instanceof ConfigurableApplicationContext
                && ((ConfigurableApplicationContext) context).isActive());
    }

    public static <T> Optional<T> optBean(Class<T> clazz) {
        try {
            return Optional.ofNullable(getBean(clazz));
        } catch (IllegalStateException | BeansException e) {
            return Optional.empty();
        }
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        if (!isApplicationContextActive())
            throw new IllegalStateException("Application context is not active");
        return context == null ? null : context.getBean(clazz);
    }
}
