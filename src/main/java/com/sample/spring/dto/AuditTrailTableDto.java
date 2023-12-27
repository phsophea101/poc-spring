package com.sample.spring.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuditTrailTableDto {
    private String traceId;
    private String tableName;
    private List<AuditTrailPrimaryKeyDto> primaryKeys;
}
