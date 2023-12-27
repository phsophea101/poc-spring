package com.sample.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.spring.conts.ActionStatus;
import com.sample.spring.dto.AuditTrailDto;
import com.sample.spring.dto.AuditTrailFieldDto;
import com.sample.spring.dto.AuditTrailPrimaryKeyDto;
import com.sample.spring.utils.ClassUtil;
import com.sample.spring.utils.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HibernateAuditTrailService {

    public static final List<String> FIELD_IGNORES = List.of("created_by", "created_date", "updated_by", "updated_date", "createdBy", "createdDate", "updatedBy", "updatedDate", "updatedDateTime", "class", "hibernateLazyInitializer");
    public static final List<String> TABLE_IGNORES = List.of("audit_trails", "oauth_access_token", "oauth_refresh_token");
    private static final String ALL = "*";

    public static String getTableName(Class<?> clazz) {
        Optional<Table> optionalTable = Optional.ofNullable(clazz.getAnnotation(javax.persistence.Table.class));
        return optionalTable.map(table -> table.name().replace("`", "")).orElse(StringUtils.EMPTY);
    }

    @SneakyThrows
    public static void generateLog(AuditTrailDto dto) {
        log.info("here is data log\n {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dto));
    }

    public static boolean isAudited(Class<?> clazz) {
        String tableName = getTableName(clazz);
        if (TABLE_IGNORES.contains(tableName))
            return Boolean.FALSE;
        String[] tables = {"*"};
        for (String table : tables) {
            if (tableName.equalsIgnoreCase(table) || ALL.equals(table))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static String getIdentityKey(Object object) {
        return String.format("%s_(%s)", getTableName(object.getClass()).toUpperCase(), org.springframework.util.ObjectUtils.getIdentityHexString(object));
    }

    static boolean isModified(Object currentValue, Object previousValue) {
        return Objects.isNull(currentValue) ? !Objects.isNull(previousValue) : ClassUtil.isJavaLangType(previousValue) && !currentValue.equals(previousValue);
    }

    public static List<AuditTrailPrimaryKeyDto> getPrimaryKeyValues(Object object) {
        List<Field> primaryKeyFields = getPrimaryKeyFields(object);
        List<AuditTrailPrimaryKeyDto> primaryKeyValues = new ArrayList<>();
        for (Object propNameObject : new BeanMap(object).keySet()) {
            String fieldName = (String) propNameObject;
            Object value = new BeanMap(object).get(fieldName);
            primaryKeyFields.forEach(item -> {
                if (item.getName().equalsIgnoreCase(fieldName))
                    primaryKeyValues.add(new AuditTrailPrimaryKeyDto(caseFieldName(fieldName), String.valueOf(value)));
            });
        }
        return primaryKeyValues;
    }

    public static AuditTrailDto getCreatedData(Object object) {
        AuditTrailDto table = new AuditTrailDto();
        List<AuditTrailFieldDto> fields = new ArrayList<>();
        List<AuditTrailPrimaryKeyDto> primaryKeyValues = getPrimaryKeyValues(object);
        List<String> primaryKeyFields = primaryKeyValues.stream().map(AuditTrailPrimaryKeyDto::getFieldName).collect(Collectors.toList());
        AtomicReference<BeanMap> map = new AtomicReference<>(new BeanMap(object));
        map.get().keySet().stream().map(String.class::cast).forEach(fieldName -> {
            Object currentValue = map.get().get(fieldName);
            if (!FIELD_IGNORES.contains(fieldName) && ObjectUtils.isNotEmpty(currentValue) && ClassUtil.isJavaLangType(currentValue.getClass()) && !primaryKeyFields.contains(caseFieldName(fieldName)))
                fields.add(new AuditTrailFieldDto(caseFieldName(fieldName), String.valueOf(currentValue), StringUtils.EMPTY));
        });
        table.setTableName(getTableName(object.getClass()));
        table.setFields(fields);
        table.setPrimaryKeys(primaryKeyValues);
        if (isDeleted(object))
            table.setAction(String.valueOf(ActionStatus.DELETED));
        else
            table.setAction(String.valueOf(ActionStatus.CREATED));
        return table;
    }

    public static boolean isDeleted(Object object) {
        List<Field> fields = getAllFields(object.getClass());
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("status") && List.of(String.valueOf(ActionStatus.DELETED), "DELETE").contains(String.valueOf(new BeanMap(object).get(fieldName)))) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static List<Field> getPrimaryKeyFields(Object entity) {
        List<Field> primaryFields = new ArrayList<>();
        List<Field> fields = getAllFields(entity.getClass());
        for (Field field : fields) {
            Optional<javax.persistence.Id> optionalPersistence = Optional.ofNullable(field.getAnnotation(javax.persistence.Id.class));
            if (optionalPersistence.isPresent()) {
                primaryFields.add(field);
            }
        }
        return primaryFields;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        Set<Field> fields = new HashSet<>();
        Class<?> current = clazz;
        while (ObjectUtils.isNotEmpty(current) && !("java.lang.Object".equalsIgnoreCase(current.getName()))) {
            List<Field> auditFields = Arrays.stream(current.getDeclaredFields()).collect(Collectors.toList());
            if (!auditFields.isEmpty()) {
                fields.addAll(auditFields);
            } else {
                fields.addAll(Arrays.asList(current.getDeclaredFields()));
            }
            current = current.getSuperclass();
        }
        return new ArrayList<>(fields);
    }

    private static String caseFieldName(String fieldName) {
        return StringUtil.camelCaseToSnakeCase(fieldName);
    }

    public static AuditTrailDto makeAuditData(Object object, String[] propertyNames, Object[] currentValues, Object[] previousValues) {
        AuditTrailDto table = new AuditTrailDto();
        String tableName = getTableName(object.getClass());
        List<AuditTrailFieldDto> fieldChanges = getFieldChanges(propertyNames, currentValues, previousValues);
        List<AuditTrailPrimaryKeyDto> primaryKeyValues = getPrimaryKeyValues(object);
        table.setFields(fieldChanges);
        table.setPrimaryKeys(primaryKeyValues);
        table.setTableName(tableName);
        if (isDeleted(object))
            table.setAction(String.valueOf(ActionStatus.DELETED));
        return table;
    }

    private static List<AuditTrailFieldDto> getFieldChanges(String[] propertyNames, Object[] currentValues, Object[] previousValues) {
        List<AuditTrailFieldDto> fields = new ArrayList<>();
        for (int i = 0; i < propertyNames.length; i++) {
            String fieldName = propertyNames[i];
            if (!FIELD_IGNORES.contains(fieldName)) {
                Object currentValue = currentValues[i];
                Object previousValue = !(Objects.isNull(previousValues) || Objects.isNull(previousValues[i])) ? previousValues[i] : null;
                if (isModified(currentValue, previousValue))
                    fields.add(new AuditTrailFieldDto(caseFieldName(fieldName), String.valueOf(currentValue), String.valueOf(previousValue)));
            }
        }
        return fields;
    }
}
