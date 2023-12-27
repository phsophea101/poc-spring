package com.sample.spring;

import com.sample.spring.conts.ActionStatus;
import com.sample.spring.dto.AuditTrailDto;
import com.sample.spring.service.HibernateAuditTrailService;
import com.sample.spring.utils.InheritableContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Iterator;

@Slf4j
public class HibernateAuditInterceptor extends EmptyInterceptor {
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        try {
            this.onSaveAction(entity, ActionStatus.CREATED);
        } catch (Exception e) {
            log.error("Audit onSave error occurred {}", e.getMessage(), e);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        try {
            boolean audited = HibernateAuditTrailService.isAudited(entity.getClass());
            if (audited) {
                AuditTrailDto audit = HibernateAuditTrailService.makeAuditData(entity, propertyNames, currentState, previousState);
                if (ObjectUtils.isNotEmpty(audit.getFields())) {
                    if (ObjectUtils.isEmpty(audit.getAction()))
                        audit.setAction(String.valueOf(ActionStatus.MODIFIED));
                    HibernateAuditTrailService.generateLog(audit);
                }
            }
        } catch (Exception e) {
            log.error("Audit onFlushDirty error occurred {}", e.getMessage(), e);
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
    private void onSaveAction(Object entity, ActionStatus action) {
        boolean audited = HibernateAuditTrailService.isAudited(entity.getClass());
        if (audited) {
            AuditTrailDto audit = new AuditTrailDto();
            String tableName = HibernateAuditTrailService.getTableName(entity.getClass());
            audit.setTableName(tableName);
            audit.setAction(String.valueOf(action));
            String key = HibernateAuditTrailService.getIdentityKey(entity);
            InheritableContextHolder.setObject(key, audit);
        }
    }
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        try {
            this.onSaveAction(entity, ActionStatus.DELETED);
        } catch (Exception e) {
            log.error("Audit onDelete error occurred {}", e.getMessage(), e);
        } finally {
            super.onDelete(entity, id, state, propertyNames, types);
        }
    }
    @Override
    public void postFlush(Iterator entities) {
        try {
            while (entities.hasNext()) {
                this.postData(entities.next());
            }
        } catch (Exception e) {
            log.error("Audit postFlush error occurred {}", e.getMessage(), e);
        }
        super.postFlush(entities);
    }
    private void postData(Object object) {
        if (HibernateAuditTrailService.isAudited(object.getClass())) {
            String key = HibernateAuditTrailService.getIdentityKey(object);
            AuditTrailDto table = InheritableContextHolder.getObject(key, AuditTrailDto.class);
            if (ObjectUtils.isNotEmpty(table)) {
                String action = table.getAction();
                table = HibernateAuditTrailService.getCreatedData(object);
                if (ObjectUtils.isNotEmpty(action))
                    table.setAction(action);
                if (ObjectUtils.isNotEmpty(table.getFields())) {
                    HibernateAuditTrailService.generateLog(table);
                    InheritableContextHolder.remove(key);
                }
            }
        }
    }
}
