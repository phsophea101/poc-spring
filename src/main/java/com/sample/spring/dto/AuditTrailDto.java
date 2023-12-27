package com.sample.spring.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuditTrailDto {
    private String tableName;
    private String action;
    private List<AuditTrailFieldDto> fields;
    private List<AuditTrailPrimaryKeyDto> primaryKeys;
}
