package com.sample.spring;

import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Component
@ConditionalOnClass(value = {EntityManagerFactory.class, SessionFactory.class})
public class BeanHibernateAuditInterceptor implements HibernatePropertiesCustomizer {
    private static final String HIBERNATE_SESSION_FACTORY_INTERCEPTOR = "hibernate.session_factory.interceptor";

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(HIBERNATE_SESSION_FACTORY_INTERCEPTOR, new HibernateAuditInterceptor());
    }
}
